package com.waseel.dssadminservice.service.customization;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.waseel.dssadminservice.enums.DssAdminConstants;
import com.waseel.dssadminservice.enums.DssAdminMessages;
import com.waseel.dssadminservice.enums.PbmPayerType;
import com.waseel.dssadminservice.enums.ServiceStatus;
import com.waseel.dssadminservice.exceptions.AdminException;
import com.waseel.dssadminservice.model.customization.pcdrugtogender.PcDrugToGenderRequestModel;
import com.waseel.dssadminservice.persist.mdss.PCAgeGenderId;
import com.waseel.dssadminservice.persist.mdss.PCGender;
import com.waseel.dssadminservice.repository.mdss.PCDrugToGenderRepository;
import com.waseel.dssadminservice.repository.mdss.PayerConfigRepository;
import com.waseel.dssadminservice.util.UserInfoUtil;

@Service
public class PCDrugToGenderTechnicalvalidation {

	@Autowired
	private PCDrugToGenderRepository pcDrugToGenderRepository;

	@Autowired
	private PayerConfigRepository payerConfigRepository;

	public PCGender validatePCDrugToGenderCustomizationRequest(Long id,
			PcDrugToGenderRequestModel drugToGenderRequestModel) throws AdminException {
		String payerId = drugToGenderRequestModel.getPayerId();
		if (isValidPayerId(payerId)) {
			Optional<PCGender> pcGenderOpt = getPcGenderDetailBasedOnCategory(id);
			if (pcGenderOpt.isPresent()) {
				String serviceCode = drugToGenderRequestModel.getServiceCode();
				differentServiceCodeCheck(serviceCode, id);
				String module = drugToGenderRequestModel.getModuleName();
				duplicateCustomizationRequest(serviceCode, payerId, module, id);
				String gender = drugToGenderRequestModel.getGender();
				String serviceStatus = drugToGenderRequestModel.getServiceStatus();
				duplicateGenderCustomizationRule(serviceCode, gender, payerId, module, serviceStatus, id);
				return pcGenderOpt.get();
			} else {
				throw new AdminException(DssAdminMessages.INVALID_CUSTOMIZATION_REQUEST.message());
			}
		}
		throw new AdminException(
				DssAdminMessages.INVALID_PAYER_ID.message().replace(DssAdminConstants.PAYER_ID.value(), payerId));
	}

	private Optional<PCGender> getPcGenderDetailBasedOnCategory(Long id) {
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		if (category != null && StringUtils.isNotBlank(category) && category.equalsIgnoreCase("payer")) {
			return pcDrugToGenderRepository.findBySeqIdAndId_PayerId(id,
					UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
		} else {
			return pcDrugToGenderRepository.findBySeqId(id);
		}
	}

	private boolean isValidPayerId(String payerId) {
		return payerConfigRepository.findByPayerIdAndPbmPayerTypeAndIsEnabled(payerId, PbmPayerType.PAYER.name(), true)
				.isPresent();
	}

	private void differentServiceCodeCheck(String serviceCode, Long id) throws AdminException {
		Optional<PCGender> pcGenderOpt = pcDrugToGenderRepository.findBySeqIdAndIdServiceCode(id, serviceCode);
		if (pcGenderOpt.isEmpty()) {
			throw new AdminException(DssAdminMessages.CANNOT_EDIT_DRUG_CODE_MESSAGE.message()
					.replace(DssAdminConstants.SERVICE_CODE.value(), serviceCode)
					.replace(DssAdminConstants.CUSTOMIZATION_NAME.value(), "Gender"));
		}
	}

	private void duplicateCustomizationRequest(String serviceCode, String payerId, String moduleName, Long id)
			throws AdminException {
		Optional<PCGender> pcGenderOpt = pcDrugToGenderRepository
				.findByIdAndSeqIdNot(new PCAgeGenderId(serviceCode, payerId, moduleName), id);
		if (pcGenderOpt.isPresent()) {
			throw new AdminException(DssAdminMessages.DUPLICATE_CUSTOMIZATION_REQUEST.message()
					.replace(DssAdminConstants.PAYER_ID.value(), payerId)
					.replace(DssAdminConstants.SERVICE_CODE.value(), serviceCode)
					.replace(DssAdminConstants.MODULE_NAME.value(), moduleName)
					.replace(DssAdminConstants.CUSTOMIZATION_NAME.value(), "Gender"));
		}
	}

	private void duplicateGenderCustomizationRule(String serviceCode, String gender, String payerId, String module,
			String serviceStatus, Long id) throws AdminException {
		duplicateRule(serviceCode, gender, payerId, module, serviceStatus, id);
		differentServiceStatus(serviceCode, gender, payerId, module, serviceStatus, id);
	}

	private void duplicateRule(String serviceCode, String gender, String payerId, String module, String serviceStatus,
			Long id) throws AdminException {
		Optional<PCGender> pcGenderOpt = pcDrugToGenderRepository
				.findByIdServiceCodeAndGenderIgnoreCaseAndIdPayerIdAndIdModuleNameIgnoreCaseAndServiceStatusIgnoreCaseAndSeqIdNot(
						serviceCode, gender, payerId, module, serviceStatus, id);
		if (pcGenderOpt.isPresent()) {
			throw new AdminException(DssAdminMessages.GENDER_CUSTOMIZATION_REQUEST_ALREADY_EXISTS.message()
					.replace(DssAdminConstants.SERVICE_CODE.value(), serviceCode)
					.replace(DssAdminConstants.GENDER.value(), serviceCode)
					.replace(DssAdminConstants.PAYER_ID.value(), payerId)
					.replace(DssAdminConstants.MODULE_NAME.value(), module)
					.replace(DssAdminConstants.SERVICE_STATUS.value(), serviceStatus));
		}
	}

	private void differentServiceStatus(String serviceCode, String gender, String payerId, String module,
			String serviceStatus, Long id) throws AdminException {
		serviceStatus = serviceStatus.equals(ServiceStatus.APPROVED.value()) ? ServiceStatus.REJECTED.value()
				: ServiceStatus.APPROVED.value();
		Optional<PCGender> pcGenderOpt = pcDrugToGenderRepository
				.findByIdServiceCodeAndGenderIgnoreCaseAndIdPayerIdAndIdModuleNameIgnoreCaseAndServiceStatusIgnoreCaseAndSeqIdNot(
						serviceCode, gender, payerId, module, serviceStatus, id);
		if (pcGenderOpt.isPresent()) {
			throw new AdminException(DssAdminMessages.GENDER_CUSTOMIZATION_REQUEST_ALREADY_EXISTS.message()
					.replace(DssAdminConstants.SERVICE_CODE.value(), serviceCode)
					.replace(DssAdminConstants.GENDER.value(), serviceCode)
					.replace(DssAdminConstants.PAYER_ID.value(), payerId)
					.replace(DssAdminConstants.MODULE_NAME.value(), module)
					.replace(DssAdminConstants.SERVICE_STATUS.value(), serviceStatus));
		}
	}
}
