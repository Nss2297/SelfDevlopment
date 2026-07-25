package com.waseel.pbm.payercustomizationservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.waseel.pbm.payercustomizationservice.enums.CustomizationModuleName;
import com.waseel.pbm.payercustomizationservice.enums.CustomizationRejectionCategory;
import com.waseel.pbm.payercustomizationservice.enums.CustomizationRequestDetailKeys;
import com.waseel.pbm.payercustomizationservice.enums.CustomizationRequestDetailLabels;
import com.waseel.pbm.payercustomizationservice.enums.CustomizationRequestDetailSplitParameters;
import com.waseel.pbm.payercustomizationservice.enums.CustomizationRequestErrorMessage;
import com.waseel.pbm.payercustomizationservice.enums.CustomizationRequestMetaDataStatus;
import com.waseel.pbm.payercustomizationservice.enums.EntityNames;
import com.waseel.pbm.payercustomizationservice.exceptions.PayerCustomizationException;
import com.waseel.pbm.payercustomizationservice.model.CustomizationRequestModel;
import com.waseel.pbm.payercustomizationservice.model.CustomizationResponseModel;
import com.waseel.pbm.payercustomizationservice.model.ErrorMessage;
import com.waseel.pbm.payercustomizationservice.persist.CustomizationRequestDetail;
import com.waseel.pbm.payercustomizationservice.persist.CustomizationRequestMetadata;
import com.waseel.pbm.payercustomizationservice.repository.CustomizationRequestDetailsRepository;
import com.waseel.pbm.payercustomizationservice.repository.CustomizationRequestMetadataRepository;
import com.waseel.pbm.payercustomizationservice.service.management.CustomizationRequestsAuditLogService;
import com.waseel.pbm.payercustomizationservice.util.UserInfoUtil;

@Service
public class CustomizationRequestService {

	@Autowired
	private CustomizationRequestMetadataRepository customizationRequestMetadataRepository;

	@Autowired
	private CustomizationRequestDetailsRepository customizationRequestDetailsRepository;

	@Autowired
	private CustomizationResponseService customizationResponseService;

	@Autowired
	private CustomizationRequestsAuditLogService customizationRequestsAuditLogService;

	@Autowired
	CustomizationListService customizationListService;

	private final Logger log = LoggerFactory.getLogger(CustomizationRequestService.class);

	public CustomizationResponseModel managePayerCustomizationRequest(
			CustomizationRequestModel customizationRequestModel, HttpServletRequest httpServletRequest)
			throws PayerCustomizationException {
		validateModuleFields(customizationRequestModel);
		CustomizationResponseModel customizationResponseModel = null;
		String payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		Optional<CustomizationRequestMetadata> customizationRequestMetadataOpt = customizationRequestMetadataRepository
				.findByPayerIdAndDrugCodeAndIsDeletedAndStatus(payerId, customizationRequestModel.getDrugCode(), false,
						CustomizationRequestMetaDataStatus.PC_PENDING_REQUEST.value());
		if (customizationRequestMetadataOpt.isPresent()) {
			throw new PayerCustomizationException(customizationResponseService.invalidCustomizationResponse(
					CustomizationRequestErrorMessage.PC_IN_PROGRESS.value().replace("payerID", payerId)));
		} else {
			customizationResponseModel = new CustomizationResponseModel();
			addCustomizationRequest(customizationRequestModel, payerId, customizationResponseModel, httpServletRequest);
		}
		return customizationResponseModel;
	}

	@Transactional(rollbackOn = { PayerCustomizationException.class })
	private void addCustomizationRequest(CustomizationRequestModel customizationRequestModel, String payerId,
			CustomizationResponseModel customizationResponseModel, HttpServletRequest httpServletRequest)
			throws PayerCustomizationException {
		String action = httpServletRequest.getMethod();
		String change = customizationRequestModel.toString();
		CustomizationRequestMetadata customizationRequestMetadata = populateCustomizationRequestMetadata(
				customizationRequestModel, payerId, action, change);
		manageCustomizationRequestDetail(customizationRequestMetadata.getCustomizationRequestsId(),
				customizationRequestModel, customizationResponseModel, action, change);
	}

	private CustomizationRequestMetadata populateCustomizationRequestMetadata(
			CustomizationRequestModel customizationRequestModel, String payerId, String action, String change) {
		CustomizationRequestMetadata customizationRequestMetadata = new CustomizationRequestMetadata();
		customizationRequestMetadata.setDrugCode(customizationRequestModel.getDrugCode());
		customizationRequestMetadata.setDrugName(customizationRequestModel.getDrugName());
		customizationRequestMetadata.setLastUpdatedDate(new Date());
		customizationRequestMetadata.setModuleName(customizationRequestModel.getModuleName());
		customizationRequestMetadata.setPayerId(payerId);
		customizationRequestMetadata.setRejectionReason(customizationRequestModel.getRejectionReason());
		customizationRequestMetadata
				.setePrescriptionReferenceNumber(customizationRequestModel.getePrescriptionReferenceNo());
		customizationRequestMetadata = customizationRequestMetadataRepository.save(customizationRequestMetadata);
		customizationRequestsAuditLogService.populateCustomizationRequestsAudit(
				customizationRequestMetadata.getCustomizationRequestsId(),
				EntityNames.CUSTOMIZATION_REQUEST_METADATA.name(), action, change);
		return customizationRequestMetadata;
	}

	private void manageCustomizationRequestDetail(long customizationRequestId,
			CustomizationRequestModel customizationRequestModel, CustomizationResponseModel customizationResponseModel,
			String action, String change) throws PayerCustomizationException {
		Iterable<CustomizationRequestDetail> customizationRequestDetailsIterable = null;
		String moduleName = customizationRequestModel.getModuleName();
		if (moduleName.equals(CustomizationModuleName.DRUG_TO_DISEASE_INTERACTION_RULE.value())) {
			customizationRequestDetailsIterable = customizationRequestDetailsRepository
					.saveAll(customizationRequestForDrugToDiagnosis(customizationRequestId, customizationRequestModel));
		} else if (moduleName.equals(CustomizationModuleName.DRUG_TO_DRUG_INTERACTION_RULE.value())) {
			customizationRequestDetailsIterable = customizationRequestDetailsRepository
					.saveAll(customizationRequestForDrugToDrug(customizationRequestId, customizationRequestModel));
		} else if (moduleName.equals(CustomizationModuleName.DUPLICATE_THERAPY_RULE.value())) {
			customizationRequestDetailsIterable = customizationRequestDetailsRepository.saveAll(
					customizationRequestForDuplicateTherapy(customizationRequestId, customizationRequestModel));
		} else if (moduleName.equals(CustomizationModuleName.DRUG_TO_GENDER_INTERACTION_RULE.value())) {
			customizationRequestDetailsIterable = customizationRequestDetailsRepository
					.saveAll(customizationRequestForGender(customizationRequestId, customizationRequestModel));
		}
		if (null != customizationRequestDetailsIterable) {
			saveCustomizationRequestAudits(customizationRequestDetailsIterable, action, change);
		}
		customizationResponseModel.setCustomizationRequestId(customizationRequestId);
	}

	private List<CustomizationRequestDetail> customizationRequestForDrugToDiagnosis(long customizationRequestId,
			CustomizationRequestModel customizationRequestModel) throws PayerCustomizationException {
		List<CustomizationRequestDetail> customizationRequestDetails = new ArrayList<>();
		String rejectionCategory = customizationRequestModel.getRejectionCategory();
		if (!customizationRequestModel.getRejectionReason()
				.contains(CustomizationRequestDetailSplitParameters.CODE_PARAMETER.value())) {
			customizationListService.deleteCustomizationRequest(customizationRequestId);
			throw new PayerCustomizationException(customizationResponseService
					.invalidCustomizationResponse(CustomizationRequestErrorMessage.INVALID_MESSAGE_FORMAT.value()));
		}
		String icdCode = customizationRequestModel.getRejectionReason()
				.split(CustomizationRequestDetailSplitParameters.CODE_PARAMETER.value())[1].strip();
		if (rejectionCategory.equals(CustomizationRejectionCategory.DIAGNOSIS_CONTRAINDICATION.value())) {
			icdCode = icdCode.split(CustomizationRequestDetailSplitParameters.COMMA_PARAMETER.value())[0].strip();
		}
		customizationRequestDetails.add(addCustomizationRequestDetail(customizationRequestId));
		customizationRequestDetails.add(addIcdCode(customizationRequestId, icdCode));
		customizationRequestDetails.add(addRejectionCategory(customizationRequestId, rejectionCategory));
		customizationRequestDetailsRepository.saveAll(customizationRequestDetails);
		return customizationRequestDetails;
	}

	private CustomizationRequestDetail addCustomizationRequestDetail(long customizationRequestId) {
		return new CustomizationRequestDetail(customizationRequestId,
				CustomizationRequestDetailKeys.CUSTOMIZABLE.toString(), "0",
				CustomizationRequestDetailKeys.CUSTOMIZABLE.toString());
	}

	private List<CustomizationRequestDetail> customizationRequestForDrugToDrug(long customizationRequestId,
			CustomizationRequestModel customizationRequestModel) throws PayerCustomizationException {
		List<CustomizationRequestDetail> customizationRequestDetails = new ArrayList<>();
		if (!customizationRequestModel.getRejectionReason()
				.contains(CustomizationRequestDetailSplitParameters.WITH_PARAMETER.value())
				|| !customizationRequestModel.getRejectionReason()
						.contains(CustomizationRequestDetailSplitParameters.HAS_PARAMETER.value())) {
			throw new PayerCustomizationException(customizationResponseService
					.invalidCustomizationResponse(CustomizationRequestErrorMessage.INVALID_MESSAGE_FORMAT.value()));
		}
		String interactedDrugCode = StringUtils.substringBetween(customizationRequestModel.getRejectionReason(),
				CustomizationRequestDetailSplitParameters.WITH_PARAMETER.value(),
				CustomizationRequestDetailSplitParameters.HAS_PARAMETER.value()).strip();
		customizationRequestDetails.add(addCustomizationRequestDetail(customizationRequestId));
		customizationRequestDetails.add(addInteractedDrugCode(customizationRequestId, interactedDrugCode));
		return customizationRequestDetails;
	}

	private List<CustomizationRequestDetail> customizationRequestForDuplicateTherapy(long customizationRequestId,
			CustomizationRequestModel customizationRequestModel) throws PayerCustomizationException {
		List<CustomizationRequestDetail> customizationRequestDetails = new ArrayList<>();
		if (!customizationRequestModel.getRejectionReason()
				.contains(CustomizationRequestDetailSplitParameters.AND_DRUG_PARAMETER.value())) {
			throw new PayerCustomizationException(customizationResponseService
					.invalidCustomizationResponse(CustomizationRequestErrorMessage.INVALID_MESSAGE_FORMAT.value()));
		}
		String interactedDrugCode = StringUtils.substringAfter(customizationRequestModel.getRejectionReason(),
				CustomizationRequestDetailSplitParameters.AND_DRUG_PARAMETER.value());
		customizationRequestDetails.add(addCustomizationRequestDetail(customizationRequestId));
		customizationRequestDetails.add(addInteractedDrugCode(customizationRequestId, interactedDrugCode));
		return customizationRequestDetails;
	}

	private List<CustomizationRequestDetail> customizationRequestForGender(long customizationRequestId,
			CustomizationRequestModel customizationRequestModel) {
		List<CustomizationRequestDetail> customizationRequestDetails = new ArrayList<>();
		customizationRequestDetails.add(addCustomizationRequestDetail(customizationRequestId));
		customizationRequestDetails.add(addGender(customizationRequestId, customizationRequestModel.getGender()));
		return customizationRequestDetails;
	}

	private CustomizationRequestDetail addIcdCode(long customizationRequestId, String icdCode) {
		CustomizationRequestDetail icdCodeDetails = new CustomizationRequestDetail();
		icdCodeDetails.setCustomizationRequestsId(customizationRequestId);
		icdCodeDetails.setCustomizationKey(CustomizationRequestDetailKeys.ICD_CODE.name());
		icdCodeDetails.setCustomizationLabel(CustomizationRequestDetailLabels.ICD_CODE.value());
		icdCodeDetails.setCustomizationValue(icdCode);
		return icdCodeDetails;
	}

	private CustomizationRequestDetail addRejectionCategory(long customizationRequestId, String rejectionCategory) {
		CustomizationRequestDetail rejectionCategoryDetails = new CustomizationRequestDetail();
		rejectionCategoryDetails.setCustomizationRequestsId(customizationRequestId);
		rejectionCategoryDetails.setCustomizationKey(CustomizationRequestDetailKeys.REJECTION_CATEGORY.name());
		rejectionCategoryDetails.setCustomizationLabel(CustomizationRequestDetailLabels.REJECTION_CATEGORY.value());
		rejectionCategoryDetails.setCustomizationValue(rejectionCategory);
		return rejectionCategoryDetails;
	}

	private CustomizationRequestDetail addInteractedDrugCode(long customizationRequestId, String interactedDrugCode) {
		CustomizationRequestDetail interactedDrugCodeDetails = new CustomizationRequestDetail();
		interactedDrugCodeDetails.setCustomizationRequestsId(customizationRequestId);
		interactedDrugCodeDetails.setCustomizationKey(CustomizationRequestDetailKeys.INTERACTED_DRUG_CODE.name());
		interactedDrugCodeDetails.setCustomizationLabel(CustomizationRequestDetailLabels.INTERACTED_DRUG_CODE.value());
		interactedDrugCodeDetails.setCustomizationValue(interactedDrugCode);
		return interactedDrugCodeDetails;
	}

	private CustomizationRequestDetail addGender(long customizationRequestId, String gender) {
		CustomizationRequestDetail genderDetails = new CustomizationRequestDetail();
		genderDetails.setCustomizationRequestsId(customizationRequestId);
		genderDetails.setCustomizationKey(CustomizationRequestDetailKeys.GENDER.name());
		genderDetails.setCustomizationLabel(CustomizationRequestDetailLabels.GENDER.value());
		genderDetails.setCustomizationValue(gender);
		return genderDetails;
	}

	private void saveCustomizationRequestAudits(
			Iterable<CustomizationRequestDetail> customizationRequestDetailsIterable, String action, String change) {
		customizationRequestDetailsIterable.forEach(customizationRequestDetail -> customizationRequestsAuditLogService
				.populateCustomizationRequestsAudit(customizationRequestDetail.getCustomizationDetailsId(),
						EntityNames.CUSTOMIZATION_REQUEST_DETAILS.name(), action, change));
	}

	private void validateModuleFields(CustomizationRequestModel customizationRequestModel)
			throws PayerCustomizationException {
		List<ErrorMessage> errorMessages = new ArrayList<>();
		if (StringUtils.isBlank(customizationRequestModel.getePrescriptionReferenceNo())) {
			errorMessages.add(
					new ErrorMessage(CustomizationRequestErrorMessage.EPRESCRIPTION_REFERENCE_NO_IS_EMPTY.value()));
		}
		if (StringUtils.isBlank(customizationRequestModel.getDrugCode())) {
			errorMessages.add(new ErrorMessage(CustomizationRequestErrorMessage.DRUG_CODE_IS_EMPTY.value()));
		}
		if (StringUtils.isBlank(customizationRequestModel.getDrugName())) {
			errorMessages.add(new ErrorMessage(CustomizationRequestErrorMessage.DRUG_NAME_IS_EMPTY.value()));
		}
		if (StringUtils.isBlank(customizationRequestModel.getRejectionReason())) {
			errorMessages.add(new ErrorMessage(CustomizationRequestErrorMessage.REJECTION_REASON_IS_EMPTY.value()));
		}
		validateModuleDetails(customizationRequestModel, errorMessages);
		if (!errorMessages.isEmpty()) {
			CustomizationResponseModel responseModel = new CustomizationResponseModel(errorMessages);
			throw new PayerCustomizationException(responseModel);
		}
	}

	private void validateModuleDetails(CustomizationRequestModel customizationRequestModel,
			List<ErrorMessage> errorMessages) {
		String moduleName = customizationRequestModel.getModuleName();
		if (StringUtils.isBlank(moduleName)) {
			errorMessages.add(new ErrorMessage(CustomizationRequestErrorMessage.MODULE_NAME_IS_EMPTY.value()));
		} else {
			if (!moduleName.equals(CustomizationModuleName.DRUG_TO_AGE_INTERACTION_RULE.value())
					&& !moduleName.equals(CustomizationModuleName.DRUG_TO_DISEASE_INTERACTION_RULE.value())
					&& !moduleName.equals(CustomizationModuleName.DRUG_TO_GENDER_INTERACTION_RULE.value())
					&& !moduleName.equals(CustomizationModuleName.QUANTITY_LIMIT_CHECK_RULE.value())
					&& !moduleName.equals(CustomizationModuleName.DRUG_TO_DRUG_INTERACTION_RULE.value())
					&& !moduleName.equals(CustomizationModuleName.DUPLICATE_THERAPY_RULE.value())) {
				errorMessages.add(new ErrorMessage(CustomizationRequestErrorMessage.INVALID_MODULE_NAME.value()));
			}
			validateRejectionCategory(customizationRequestModel, errorMessages, moduleName);
			if (moduleName.equals(CustomizationModuleName.DRUG_TO_GENDER_INTERACTION_RULE.value())
					&& StringUtils.isBlank(customizationRequestModel.getGender())) {
				errorMessages.add(new ErrorMessage(CustomizationRequestErrorMessage.GENDER_IS_EMPTY.value()));
			}
		}
	}

	private void validateRejectionCategory(CustomizationRequestModel customizationRequestModel,
			List<ErrorMessage> errorMessages, String moduleName) {
		String rejectionCategory = customizationRequestModel.getRejectionCategory();
		if (moduleName.equals(CustomizationModuleName.DRUG_TO_DISEASE_INTERACTION_RULE.value())) {
			if (StringUtils.isBlank(rejectionCategory)) {
				errorMessages
						.add(new ErrorMessage(CustomizationRequestErrorMessage.REJECTION_CATEGORY_IS_EMPTY.value()));
			} else {
				if (!rejectionCategory.equals(CustomizationRejectionCategory.DIAGNOSIS_CONTRAINDICATION.value())
						&& !rejectionCategory.equals(CustomizationRejectionCategory.DIAGNOSIS_INDICATION.value())) {
					errorMessages
							.add(new ErrorMessage(CustomizationRequestErrorMessage.INVALID_REJECTION_CATEGORY.value()));
				}
			}
		}
	}

	public List<String> getAllModuleName() {
		log.info("get All ModuleName");
		return Arrays.stream(CustomizationModuleName.values()).map(CustomizationModuleName::value)
				.collect(Collectors.toList());
	}
}
