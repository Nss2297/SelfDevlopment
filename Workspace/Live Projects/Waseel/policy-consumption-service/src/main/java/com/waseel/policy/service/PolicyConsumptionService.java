package com.waseel.policy.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.policy.enums.AuditType;
import com.waseel.policy.enums.BusinessRulesPrivilage;
import com.waseel.policy.enums.EntitiesName;
import com.waseel.policy.enums.ExceptionLogs;
import com.waseel.policy.enums.PolicyConsumptionDenialCodes;
import com.waseel.policy.enums.PolicyResponseStatus;
import com.waseel.policy.enums.PolicyResponseStatusDescription;
import com.waseel.policy.enums.RequestType;
import com.waseel.policy.enums.StatusDescription;
import com.waseel.policy.enums.TransactionStatusType;
import com.waseel.policy.enums.TransactionType;
import com.waseel.policy.exception.PolicyException;
import com.waseel.policy.model.BrandAndGenericModel;
import com.waseel.policy.model.CancellAndDispensePolicyRequestModel;
import com.waseel.policy.model.DeactivatePrescriptionRequestModel;
import com.waseel.policy.model.DispensibleDrugsRequestModel;
import com.waseel.policy.model.DrugListModel;
import com.waseel.policy.model.MemberPolicyDetailsModel;
import com.waseel.policy.model.PolicyRequestModel;
import com.waseel.policy.model.PolicyResponseModel;
import com.waseel.policy.persist.businessrules.AuditLog;
import com.waseel.policy.persist.businessrules.CommonDenial;
import com.waseel.policy.persist.businessrules.PrescriptionMetadata;
import com.waseel.policy.persist.businessrules.TransactionLog;
import com.waseel.policy.persist.hira.AccountToAccountAssociation;
import com.waseel.policy.repository.businessrules.AuditLogRepository;
import com.waseel.policy.repository.businessrules.BenefitCaseRepository;
import com.waseel.policy.repository.businessrules.ClassBenefitRepository;
import com.waseel.policy.repository.businessrules.CommonDenialsRepository;
import com.waseel.policy.repository.businessrules.GenericIrreplicableBrandRepository;
import com.waseel.policy.repository.businessrules.MemberBenefitAssoicationRepository;
import com.waseel.policy.repository.businessrules.MemberPolicyAssociationRepository;
import com.waseel.policy.repository.businessrules.MemberProfileRepository;
import com.waseel.policy.repository.businessrules.PrescriptionMetadataRepository;
import com.waseel.policy.repository.businessrules.ReplicableBrandRepository;
import com.waseel.policy.repository.businessrules.TransactionLogRepository;
import com.waseel.policy.repository.hira.AccountToAccountAssociationRepository;
import com.waseel.policy.service.management.SessionService;
import com.waseel.policy.service.mapper.MapperService;

@Service
public class PolicyConsumptionService {

	@Autowired
	MemberPolicyAssociationRepository memberPolicyAssociationRepository;
	@Autowired
	MemberProfileRepository memberProfileRepository;
	@Autowired
	ClassBenefitRepository classBenefitRepository;
	@Autowired
	MemberBenefitAssoicationRepository memberBenefitAssoicationRepository;
	@Autowired
	BenefitCaseRepository benefitCaseRepository;
	@Autowired
	PrescriptionMetadataRepository prescriptionMetadataRepository;
	@Autowired
	private CommonDenialsRepository commonDenialsRepository;
	@Autowired
	TransactionLogRepository transactionLogRepository;
	@Autowired
	GenericIrreplicableBrandRepository genericIrreplicableBrandRepository;
	@Autowired
	ReplicableBrandRepository replicableBrandRepository;
	@Autowired
	AuditLogRepository auditLogRepository;
	@Autowired
	private MapperService mapperService;
	@Autowired
	private MemberDetailsService memberDetailsService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	AccountToAccountAssociationRepository accountToAccountAssociationRepository;

	private final Logger log = LoggerFactory.getLogger(PolicyConsumptionService.class);
	private static final String SERVICE_NAME = "policy-consumption-service";

	public PolicyResponseModel getPolicyDetails(String idNumber, PolicyRequestModel policyRequestModel,
			ContentCachingRequestWrapper contentCachingRequestWrapper) throws PolicyException {
		AccountToAccountAssociation providerPayerCode = accountToAccountAssociationRepository
				.findByIdSourceAndIdDestinationsAndIsEnabled(new BigDecimal(policyRequestModel.getProviderId()),
						new BigDecimal(policyRequestModel.getPayerId()), true);
		BigDecimal transactionId = policyRequestModel.getRequestType().equals(RequestType.NEW.value())
				? new BigDecimal(BusinessRulesPrivilage.NEW_PRESCRIPTION_PRIVILAGE.value())
				: new BigDecimal(BusinessRulesPrivilage.PRESCRIPTION_FOLLOWUP_PRIVILAGE.value());
		handleTransactionLog(contentCachingRequestWrapper, policyRequestModel.getPayerId(),
				policyRequestModel.getProviderId(), policyRequestModel.getRequestId(), transactionId);
		PolicyResponseModel policyResponseModel = new PolicyResponseModel();
		policyResponseModel.setRequestId(policyRequestModel.getRequestId());
		MemberPolicyDetailsModel memberPolicyDetailsModel = memberDetailsService.fetchMemberPolicyDetails(idNumber,
				policyRequestModel.getBenefitCode(), policyRequestModel.getBenefitCase(),
				policyRequestModel.getPayerId(), policyRequestModel.getProviderId(), providerPayerCode.getCode());
		boolean hasTotalRemainingLimitAmount = null != memberPolicyDetailsModel.getTotalRemainingLimitAmount()
				&& memberPolicyDetailsModel.getTotalRemainingLimitAmount().compareTo(BigDecimal.ZERO) > 0;
		if (hasTotalRemainingLimitAmount) {
			managePolicyCheckForNewOrFollowup(memberPolicyDetailsModel, policyRequestModel, policyResponseModel,
					idNumber);
		} else {
			throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.REJECTED.value(),
					PolicyResponseStatusDescription.NO_REMAINING_LIMIT.value(), String.valueOf(HttpStatus.OK.value()),
					PolicyResponseStatusDescription.NO_REMAINING_LIMIT.value(),
					PolicyConsumptionDenialCodes.BR_PC_NO_REMAINING_LIMIT.value(), idNumber));
		}
		return policyResponseModel;
	}

	private PolicyResponseModel populateInvalidPolicyResponse(String status, String statusDescription,
			String httpStatus, String httpDescription, String denialCode, String idNumber) {
		PolicyResponseModel policyResponseModel = new PolicyResponseModel();
		Optional<CommonDenial> commonDenialsOp = commonDenialsRepository.findByDenialCode(denialCode);
		if (commonDenialsOp.isPresent()) {
			policyResponseModel
					.setDenialDescription(commonDenialsOp.get().getDenialDescription().replace("<IdNumber>", idNumber));
		}
		policyResponseModel.setStatus(status);
		policyResponseModel.setStatusDescription(statusDescription);
		policyResponseModel.setHttpStatusCode(httpStatus);
		policyResponseModel.setHttpStatusDescription(httpDescription);
		policyResponseModel.setDenialCode(denialCode);
		return policyResponseModel;
	}

	private void managePolicyCheckForNewOrFollowup(MemberPolicyDetailsModel memberPolicyDetailsModel,
			PolicyRequestModel policyRequestModel, PolicyResponseModel policyResponseModel, String idNumber)
			throws PolicyException {
		Optional<PrescriptionMetadata> prescriptionMetadataOpt = prescriptionMetadataRepository
				.findByRequestId(policyRequestModel.getRequestId());
		if (prescriptionMetadataOpt.isPresent()) {
			if (prescriptionMetadataOpt.get().getActivePrescription().equals("1")) {
				handelFollowUp(policyRequestModel, policyResponseModel, prescriptionMetadataOpt.get(), idNumber,
						memberPolicyDetailsModel);
			} else {
				throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.REJECTED.value(),
						PolicyResponseStatusDescription.REQUEST_IS_INACTIVE.value(),
						String.valueOf(HttpStatus.OK.value()),
						PolicyResponseStatusDescription.REQUEST_IS_INACTIVE.value(),
						PolicyConsumptionDenialCodes.BR_PC_INACTIVE.value(), idNumber));
			}
		} else {
			handelNewPrescription(policyRequestModel, policyResponseModel, idNumber, memberPolicyDetailsModel);
		}
	}

	private void handelFollowUp(PolicyRequestModel policyRequestModel, PolicyResponseModel policyResponseModel,
			PrescriptionMetadata prescriptionMetadata, String idNumber,
			MemberPolicyDetailsModel memberPolicyDetailsModel) throws PolicyException {
		BigDecimal remainingLimitValue = memberPolicyDetailsModel.getRemainingLimitValue();
		if (null == remainingLimitValue || remainingLimitValue.compareTo(BigDecimal.ZERO) == 0) {
			throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.INVALID.value(),
					PolicyResponseStatusDescription.NO_REMAINING_LIMIT.value(),
					String.valueOf(HttpStatus.BAD_REQUEST.value()),
					PolicyResponseStatusDescription.NO_REMAINING_LIMIT.value(),
					PolicyConsumptionDenialCodes.BR_PC_NO_REMAINING_LIMIT.value(), idNumber));
		} else {
			if (comparePolicyNumbers(prescriptionMetadata.getPolicyNumber().toString(),
					memberPolicyDetailsModel.getPolicyNumber())) {
				prescriptionMetadata.setActivePrescription("0");
				prescriptionMetadataRepository.save(prescriptionMetadata);
				populateAuditLog(AuditType.UPDATE, prescriptionMetadata.getId(), EntitiesName.PRESCRIPTION_METADATA,
						prescriptionMetadata);
				managePolicyCheckForFollowupPrescription(memberPolicyDetailsModel, prescriptionMetadata,
						policyRequestModel, policyResponseModel, idNumber);
			} else {
				throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.REJECTED.value(),
						PolicyResponseStatusDescription.EXPIRED_PRESCRIPTION.value(),
						String.valueOf(HttpStatus.OK.value()),
						PolicyResponseStatusDescription.EXPIRED_PRESCRIPTION.value(),
						PolicyConsumptionDenialCodes.BR_PC_EXPIRED.value(), idNumber));
			}
		}
	}

	private void managePolicyCheckForFollowupPrescription(MemberPolicyDetailsModel memberPolicyDetailsModel,
			PrescriptionMetadata prescriptionMetadata, PolicyRequestModel policyRequestModel,
			PolicyResponseModel policyResponseModel, String idNumber) throws PolicyException {
		BigDecimal totalPatientShareValue = null;
		BigDecimal totalPayerShareValue = null;
		String statusDescription = null;
		BigDecimal prescriptionValue = new BigDecimal(policyRequestModel.getPrescriptionValue());
		BigDecimal remainingLimitValue = memberPolicyDetailsModel.getRemainingLimitValue();
		if (memberPolicyDetailsModel.getBrandAndGenericModel() == null) {
			if (prescriptionValue.equals(prescriptionMetadata.getPrescriptionValue())) {
				totalPatientShareValue = prescriptionMetadata.getPatientShare();
				totalPayerShareValue = prescriptionMetadata.getPayerShare();
				handleFollowupMetadata(totalPatientShareValue, totalPayerShareValue, remainingLimitValue,
						prescriptionMetadata, prescriptionValue);
				statusDescription = PolicyResponseStatusDescription.PASSED_POLICY_CHECK.value();
				populateAcceptedResponse(policyResponseModel, totalPatientShareValue, statusDescription,
						totalPayerShareValue, memberPolicyDetailsModel, policyRequestModel.getDrugList(),
						policyRequestModel.getRequestId());
			} else {
				BigDecimal maxPatientShareValue = memberPolicyDetailsModel.getMaxPatientShareValue();
				totalPatientShareValue = prescriptionValue
						.multiply(memberPolicyDetailsModel.getPatientShareValue().divide(new BigDecimal(100)));
				if (totalPatientShareValue.compareTo(maxPatientShareValue) >= 0) {
					totalPatientShareValue = maxPatientShareValue;
				}
				totalPayerShareValue = prescriptionValue.subtract(totalPatientShareValue);
				if (totalPayerShareValue.compareTo(remainingLimitValue) > 0) {
					statusDescription = PolicyResponseStatusDescription.EXTRA_PATIENT_SHARE.value()
							.replace(StatusDescription.AMOUNT.value(),
									totalPayerShareValue.subtract(remainingLimitValue).toString())
							.replace(StatusDescription.CURRENCY.value(),
									memberPolicyDetailsModel.getPatientShareCurrency());
					totalPatientShareValue = totalPatientShareValue
							.add(totalPayerShareValue.subtract(remainingLimitValue));
					totalPayerShareValue = prescriptionValue.subtract(totalPatientShareValue);
				} else {
					statusDescription = PolicyResponseStatusDescription.PASSED_POLICY_CHECK.value();
				}
				handleFollowupMetadata(totalPatientShareValue, totalPayerShareValue, remainingLimitValue,
						prescriptionMetadata, prescriptionValue);
				populateAcceptedResponse(policyResponseModel, totalPatientShareValue, statusDescription,
						totalPayerShareValue, memberPolicyDetailsModel, policyRequestModel.getDrugList(),
						policyRequestModel.getRequestId());
			}
		} else {
			BrandAndGenericModel brandAndGenericModel = memberPolicyDetailsModel.getBrandAndGenericModel();
			BigDecimal genericPatientSharePercentage = brandAndGenericModel.getGenericDrugPatientShareValue();
			BigDecimal brandPatientSharePercentage = brandAndGenericModel.getBrandDrugPatientShareValue();
			BigDecimal genericMaxPatientShareValue = brandAndGenericModel.getGenericDrugMaxPatientShare();
			BigDecimal brandMaxPatientShareValue = brandAndGenericModel.getBrandDrugMaxPatientShare();
			BigDecimal patientSharePercentage = memberPolicyDetailsModel.getPatientShareValue();
			Optional<List<String>> genericAndIrreplicableDrugs = genericIrreplicableBrandRepository
					.findAllNonDeletedDrugs();
			Optional<List<String>> replicableBrandDrugs = replicableBrandRepository.findAllNonDeletedDrugs();
			if (genericAndIrreplicableDrugs.isPresent() && replicableBrandDrugs.isPresent()) {
				handleDrugPatientShareForBrandAndGeneric(genericAndIrreplicableDrugs, replicableBrandDrugs,
						policyRequestModel, genericMaxPatientShareValue, brandMaxPatientShareValue,
						totalPatientShareValue, totalPayerShareValue, brandPatientSharePercentage,
						genericPatientSharePercentage, remainingLimitValue, idNumber, patientSharePercentage,
						memberPolicyDetailsModel.getTotalRemainingLimitCurrency());
				totalPatientShareValue = policyRequestModel.getDrugList().stream().map(drug -> drug.getPatientShare())
						.reduce(BigDecimal.ZERO, BigDecimal::add);
				totalPayerShareValue = policyRequestModel.getDrugList().stream().map(drug -> drug.getPayerShare())
						.reduce(BigDecimal.ZERO, BigDecimal::add);
				if (totalPayerShareValue.compareTo(remainingLimitValue) > 0) {
					statusDescription = PolicyResponseStatusDescription.EXTRA_PATIENT_SHARE.value()
							.replace("<amount>", totalPayerShareValue.subtract(remainingLimitValue).toString())
							.replace("<currency>",
									memberPolicyDetailsModel.getPatientShareCurrency() != null
											? memberPolicyDetailsModel.getPatientShareCurrency()
											: "SAR");
					totalPatientShareValue = totalPatientShareValue
							.add(totalPayerShareValue.subtract(remainingLimitValue));
					totalPayerShareValue = prescriptionValue.subtract(totalPatientShareValue);
				} else {
					statusDescription = PolicyResponseStatusDescription.PASSED_POLICY_CHECK.value();
				}
				handleFollowupMetadata(totalPatientShareValue, totalPayerShareValue, remainingLimitValue,
						prescriptionMetadata, prescriptionValue);
				policyResponseModel.setDrugList(policyRequestModel.getDrugList());
				populateCommonAcceptedResponse(policyResponseModel, totalPatientShareValue, statusDescription,
						totalPayerShareValue, memberPolicyDetailsModel, policyRequestModel.getDrugList(),
						policyRequestModel.getRequestId());
			}
		}
	}

	private void handleDrugPatientShareForBrandAndGeneric(Optional<List<String>> genericAndIrreplicableDrugs,
			Optional<List<String>> replicableBrandDrugs, PolicyRequestModel policyRequestModel,
			BigDecimal genericMaxPatientShareValue, BigDecimal brandMaxPatientShareValue,
			BigDecimal totalPatientShareValue, BigDecimal totalPayerShareValue, BigDecimal brandPatientSharePercentage,
			BigDecimal genericPatientSharePercentage, BigDecimal remainingLimitValue, String idNumber,
			BigDecimal patientShare, String currency) throws PolicyException {
		BigDecimal cumulativeBrandPatientShare[] = { new BigDecimal(0) };
		BigDecimal cumulativeGenericPatientShare[] = { new BigDecimal(0) };
		BigDecimal cumulativePayerShare[] = { new BigDecimal(0) };
		List<String> processedDrugs = new ArrayList<String>();
		policyRequestModel.getDrugList().stream().forEach(drug -> {
			drug.setPayerShareCurrency(currency);
			drug.setPatientShareCurrency(currency);
			BigDecimal drugAmount = drug.getAmount();
			BigDecimal drugPatientShareAmount = null;
			if (genericAndIrreplicableDrugs.get().contains(drug.getDrugCode())) {
				drugPatientShareAmount = cumulativeGenericPatientShare[0].compareTo(genericMaxPatientShareValue) < 0
						? drugAmount.multiply(genericPatientSharePercentage.divide(new BigDecimal(100)))
						: new BigDecimal(0);
				if (drugPatientShareAmount.compareTo(genericMaxPatientShareValue) >= 0) {
					drugPatientShareAmount = genericMaxPatientShareValue;
				}
				if ((cumulativeGenericPatientShare[0].add(drugPatientShareAmount))
						.compareTo(genericMaxPatientShareValue) > 0) {
					drugPatientShareAmount = genericMaxPatientShareValue.subtract(cumulativeGenericPatientShare[0]);
				}
				cumulativeGenericPatientShare[0] = cumulativeGenericPatientShare[0].add(drugPatientShareAmount);
				cumulativePayerShare[0] = cumulativePayerShare[0].add(drugAmount.subtract(drugPatientShareAmount));
				processedDrugs.add(drug.getDrugCode());
			}
			if (replicableBrandDrugs.get().contains(drug.getDrugCode())) {
				drugPatientShareAmount = cumulativeBrandPatientShare[0].compareTo(brandMaxPatientShareValue) < 0
						? drugAmount.multiply(brandPatientSharePercentage.divide(new BigDecimal(100)))
						: new BigDecimal(0);
				if (drugPatientShareAmount.compareTo(brandMaxPatientShareValue) >= 0) {
					drugPatientShareAmount = brandMaxPatientShareValue;
				}
				if ((cumulativeBrandPatientShare[0].add(drugPatientShareAmount))
						.compareTo(brandMaxPatientShareValue) > 0) {
					drugPatientShareAmount = brandMaxPatientShareValue.subtract(cumulativeBrandPatientShare[0]);
				}
				cumulativeBrandPatientShare[0] = cumulativeBrandPatientShare[0].add(drugPatientShareAmount);
				cumulativePayerShare[0] = cumulativePayerShare[0].add(drugAmount.subtract(drugPatientShareAmount));
				processedDrugs.add(drug.getDrugCode());
			}
			if (cumulativePayerShare[0].compareTo(remainingLimitValue) > 0) {
				BigDecimal oldCumlativePayerShare = cumulativePayerShare[0]
						.subtract(drugAmount.subtract(drugPatientShareAmount));
				if (drugPatientShareAmount != null && cumulativePayerShare[0] != null && remainingLimitValue != null)
					drug.setPatientShare(
							drugPatientShareAmount.add(cumulativePayerShare[0].subtract(remainingLimitValue)));
				if (oldCumlativePayerShare != null && remainingLimitValue != null) {
					drug.setPayerShare(remainingLimitValue.subtract(oldCumlativePayerShare));
					cumulativePayerShare[0] = oldCumlativePayerShare
							.add(remainingLimitValue.subtract(oldCumlativePayerShare));
				}
			} else {
				if (drugPatientShareAmount != null)
					drug.setPatientShare(drugPatientShareAmount);
				if (drugAmount != null && drugPatientShareAmount != null)
					drug.setPayerShare(drugAmount.subtract(drugPatientShareAmount));
			}
		});
		if (processedDrugs.size() != policyRequestModel.getDrugList().size()) {
			policyRequestModel.getDrugList().stream().filter(drug -> !processedDrugs.contains(drug.getDrugCode()))
					.forEach(drug -> {
						BigDecimal drugAmount = drug.getAmount();
						if (drugAmount != null && patientShare != null) {
							BigDecimal patientShareAmount = drugAmount
									.multiply(patientShare.divide(new BigDecimal(100)));
							drug.setPatientShare(patientShareAmount);
							drug.setPayerShare(drugAmount.subtract(patientShareAmount));
							drug.setPayerShareCurrency(currency);
							drug.setPatientShareCurrency(currency);
						}
					});
		}
	}

	private void populateAuditLog(AuditType auditType, Long entityId, EntitiesName entityName, Object entityData) {
		try {
			AuditLog auditLog = new AuditLog();
			// String entity = new Gson().toJson(entityData);
			auditLog.setEntityData("");
			auditLog.setEntityName(entityName.value());
			auditLog.setEntityId(entityId);
			auditLog.setUpdateBy(SERVICE_NAME);
			auditLog.setUpdateDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			auditLog.setUpdateType(auditType.name());
			auditLogRepository.save(auditLog);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void handleFollowupMetadata(BigDecimal patientShareValue, BigDecimal payerShareValue,
			BigDecimal remainingLimitValue, PrescriptionMetadata prescriptionMetadata, BigDecimal prescriptionValue) {
		prescriptionMetadata.setPatientShare(null == patientShareValue ? new BigDecimal(0) : patientShareValue);
		prescriptionMetadata.setPayerShare(null == payerShareValue ? new BigDecimal(0) : payerShareValue);
		prescriptionMetadata.setRemainingLimit(null == remainingLimitValue ? new BigDecimal(0) : remainingLimitValue);
		prescriptionMetadata.setPrescriptionValue(prescriptionValue);
		prescriptionMetadata.setActivePrescription("1");
		prescriptionMetadataRepository.save(prescriptionMetadata);
		populateAuditLog(AuditType.UPDATE, prescriptionMetadata.getId(), EntitiesName.PRESCRIPTION_METADATA,
				prescriptionMetadata);
	}

	private void handelNewPrescription(PolicyRequestModel policyRequestModel, PolicyResponseModel policyResponseModel,
			String idNumber, MemberPolicyDetailsModel memberPolicyDetailsModel) throws PolicyException {
		BigDecimal remainingLimitValue = memberPolicyDetailsModel.getRemainingLimitValue();
		BigDecimal prescriptionValue = new BigDecimal(policyRequestModel.getPrescriptionValue());
		String statusDescription = null;
		if (null == remainingLimitValue || remainingLimitValue.equals(BigDecimal.ZERO)) {
			throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.INVALID.value(),
					PolicyResponseStatusDescription.NO_REMAINING_LIMIT.value(),
					String.valueOf(HttpStatus.BAD_REQUEST.value()),
					PolicyResponseStatusDescription.NO_REMAINING_LIMIT.value(),
					PolicyConsumptionDenialCodes.BR_PC_NO_REMAINING_LIMIT.value(), idNumber));
		} else {
			BigDecimal totalPatientShareValue = null;
			BigDecimal totalPayerShareValue = null;
			BigDecimal patientSharePercentage = memberPolicyDetailsModel.getPatientShareValue();
			if (memberPolicyDetailsModel.getBrandAndGenericModel() == null) {
				BigDecimal maxPatientShareValue = memberPolicyDetailsModel.getMaxPatientShareValue();
				totalPatientShareValue = prescriptionValue.multiply(patientSharePercentage.divide(new BigDecimal(100)));
				if (totalPatientShareValue.compareTo(maxPatientShareValue) >= 0) {
					totalPatientShareValue = maxPatientShareValue;
				}
				totalPayerShareValue = prescriptionValue.subtract(totalPatientShareValue);
				if (totalPayerShareValue.compareTo(remainingLimitValue) > 0) {
					statusDescription = PolicyResponseStatusDescription.EXTRA_PATIENT_SHARE.value()
							.replace("<amount>", totalPayerShareValue.subtract(remainingLimitValue).toString())
							.replace("<currency>", memberPolicyDetailsModel.getPatientShareCurrency());
					totalPatientShareValue = totalPatientShareValue
							.add(totalPayerShareValue.subtract(remainingLimitValue));
					totalPayerShareValue = prescriptionValue.subtract(totalPatientShareValue);
				} else {
					statusDescription = PolicyResponseStatusDescription.PASSED_POLICY_CHECK.value();
				}
				populateAcceptedResponse(policyResponseModel, totalPatientShareValue, statusDescription,
						totalPayerShareValue, memberPolicyDetailsModel, policyRequestModel.getDrugList(),
						policyRequestModel.getRequestId());
			} else {
				BrandAndGenericModel brandAndGenericModel = memberPolicyDetailsModel.getBrandAndGenericModel();
				BigDecimal genericPatientSharePercentage = brandAndGenericModel.getGenericDrugPatientShareValue();
				BigDecimal brandPatientSharePercentage = brandAndGenericModel.getBrandDrugPatientShareValue();
				BigDecimal genericMaxPatientShareValue = brandAndGenericModel.getGenericDrugMaxPatientShare();
				BigDecimal brandMaxPatientShareValue = brandAndGenericModel.getBrandDrugMaxPatientShare();
				Optional<List<String>> genericAndIrreplicableDrugs = genericIrreplicableBrandRepository
						.findAllNonDeletedDrugs();
				Optional<List<String>> replicableBrandDrugs = replicableBrandRepository.findAllNonDeletedDrugs();

				if (genericAndIrreplicableDrugs.isPresent() && replicableBrandDrugs.isPresent()) {
					handleDrugPatientShareForBrandAndGeneric(genericAndIrreplicableDrugs, replicableBrandDrugs,
							policyRequestModel, genericMaxPatientShareValue, brandMaxPatientShareValue,
							totalPatientShareValue, totalPayerShareValue, brandPatientSharePercentage,
							genericPatientSharePercentage, remainingLimitValue, idNumber, patientSharePercentage,
							memberPolicyDetailsModel.getTotalRemainingLimitCurrency());
					totalPatientShareValue = policyRequestModel.getDrugList().stream()
							.map(drug -> drug.getPatientShare()).reduce(BigDecimal.ZERO, BigDecimal::add);
					totalPayerShareValue = policyRequestModel.getDrugList().stream().map(drug -> drug.getPayerShare())
							.reduce(BigDecimal.ZERO, BigDecimal::add);
					if (totalPayerShareValue.compareTo(remainingLimitValue) > 0) {
						statusDescription = PolicyResponseStatusDescription.EXTRA_PATIENT_SHARE.value()
								.replace("<amount>", totalPayerShareValue.subtract(remainingLimitValue).toString())
								.replace("<currency>",
										memberPolicyDetailsModel.getPatientShareCurrency() != null
												? memberPolicyDetailsModel.getPatientShareCurrency()
												: "SAR");
						totalPatientShareValue = totalPatientShareValue
								.add(totalPayerShareValue.subtract(remainingLimitValue));
						totalPayerShareValue = prescriptionValue.subtract(totalPatientShareValue);
					} else {
						statusDescription = PolicyResponseStatusDescription.PASSED_POLICY_CHECK.value();
					}
					policyResponseModel.setDrugList(policyRequestModel.getDrugList());
					populateCommonAcceptedResponse(policyResponseModel, totalPatientShareValue, statusDescription,
							totalPayerShareValue, memberPolicyDetailsModel, policyRequestModel.getDrugList(),
							policyRequestModel.getRequestId());
				}
			}
			if (policyResponseModel.getStatus().equals(PolicyResponseStatus.APPROVED.value())) {
				savePrescriptionMetadata(policyResponseModel);
			}
		}
	}

	private void savePrescriptionMetadata(PolicyResponseModel policyResponseModel) {
		PrescriptionMetadata prescriptionMetadata = new PrescriptionMetadata();
		BigDecimal payerShare = new BigDecimal(policyResponseModel.getPayerShare());
		BigDecimal patientShare = new BigDecimal(policyResponseModel.getPatientShare());
		prescriptionMetadata.setBenefitLimitValue(policyResponseModel.getBenefitLimitValue());
		prescriptionMetadata.setBenefitLimitCurr(policyResponseModel.getBenefitLimitCurrency());
		prescriptionMetadata.setPatientShare(patientShare);
		prescriptionMetadata.setPayerShare(payerShare);
		prescriptionMetadata.setPolicyNumber(new BigDecimal(policyResponseModel.getPolicyNumber()));
		prescriptionMetadata.setRemainingLimit(new BigDecimal(policyResponseModel.getRemainingLimit()));
		prescriptionMetadata.setRequestId(policyResponseModel.getRequestId());
		prescriptionMetadata.setUpdateDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		prescriptionMetadata.setPrescriptionValue(patientShare.add(payerShare));
		prescriptionMetadataRepository.save(prescriptionMetadata);
		populateAuditLog(AuditType.INSERT, prescriptionMetadata.getId(), EntitiesName.PRESCRIPTION_METADATA,
				prescriptionMetadata);
	}

	private void populateAcceptedResponse(PolicyResponseModel policyResponseModel, BigDecimal patientShareValue,
			String statusDescription, BigDecimal payerShareValue, MemberPolicyDetailsModel memberPolicyDetailsModel,
			List<DrugListModel> drugList, String requestId) {
		populateCommonAcceptedResponse(policyResponseModel, patientShareValue, statusDescription, payerShareValue,
				memberPolicyDetailsModel, drugList, requestId);
		if (null != drugList && !drugList.isEmpty()) {
			setPayerAndPatientShareForDrugs(drugList, policyResponseModel,
					memberPolicyDetailsModel.getPatientShareValue(),
					memberPolicyDetailsModel.getTotalRemainingLimitCurrency());
		}
	}

	private void populateCommonAcceptedResponse(PolicyResponseModel policyResponseModel,
			BigDecimal totalPatientShareValue, String statusDescription, BigDecimal totalPayerShareValue,
			MemberPolicyDetailsModel memberPolicyDetailsModel, List<DrugListModel> drugList, String requestId) {
		BigDecimal remainingLimit = memberPolicyDetailsModel.getRemainingLimitValue();
		policyResponseModel.setPatientShare(null == totalPatientShareValue ? "0" :
		totalPatientShareValue.toString());
		policyResponseModel.setRemainingLimit(null == remainingLimit ? "0" : remainingLimit.toString());
		policyResponseModel.setPayerShare(null == totalPayerShareValue ? "0" :
		totalPayerShareValue.toString());
		policyResponseModel.setStatus(PolicyResponseStatus.APPROVED.value());
		policyResponseModel.setStatusDescription(statusDescription);
		policyResponseModel.setPolicyNumber(memberPolicyDetailsModel.getPolicyNumber());
		policyResponseModel.setPolicyClass(memberPolicyDetailsModel.getPolicyClass());
		policyResponseModel.setBenefitLimitCurrency(memberPolicyDetailsModel.getBenefitLimitCurrency());
		policyResponseModel.setBenefitLimitValue(memberPolicyDetailsModel.getBenefitLimitValue());
		policyResponseModel.setHttpStatusCode(String.valueOf(HttpStatus.OK.value()));
		policyResponseModel.setHttpStatusDescription(statusDescription);
		policyResponseModel.setBenefitRemainingLimitCurrency(memberPolicyDetailsModel.getBenefitLimitCurrency());
		policyResponseModel.setMemberId(memberPolicyDetailsModel.getMemberId());
		policyResponseModel.setStatusDescription(PolicyResponseStatusDescription.PASSED_POLICY_CHECK.value());
		policyResponseModel.setPolicyBenefit("");
		policyResponseModel.setRequestId(requestId);
		String currency = memberPolicyDetailsModel.getTotalRemainingLimitCurrency();
		policyResponseModel.setPatientShareCurrency(currency);
		policyResponseModel.setPayerShareCurrency(currency);

	}

	public PolicyResponseModel handleDispensePrescription(String idNumber, String requestId, String payerId,
			String benefitCode, String benefitCase, List<DrugListModel> drugList, String providerId,
			ContentCachingRequestWrapper contentCachingRequestWrapper) throws PolicyException {
		AccountToAccountAssociation providerPayerCode = accountToAccountAssociationRepository
				.findByIdSourceAndIdDestinationsAndIsEnabled(new BigDecimal(providerId), new BigDecimal(payerId), true);
		handleTransactionLog(contentCachingRequestWrapper, payerId, providerId, requestId,
				new BigDecimal(BusinessRulesPrivilage.DISPENSE_PRIVILAGE.value()));
		PolicyResponseModel policyResponseModel = new PolicyResponseModel();
		policyResponseModel.setRequestId(requestId);
		Optional<PrescriptionMetadata> prescriptionMetadataOpt = prescriptionMetadataRepository
				.findByRequestId(requestId);
		if (prescriptionMetadataOpt.isPresent()) {
			PrescriptionMetadata prescriptionMetadata = prescriptionMetadataOpt.get();
			if (prescriptionMetadata.getActivePrescription().equals("1")) {
				MemberPolicyDetailsModel memberPolicyDetailsModel = memberDetailsService.fetchMemberPolicyDetails(
						idNumber, benefitCode, benefitCase, payerId, providerId, providerPayerCode.getCode());
				boolean hasTotalRemainingLimitAmount = null != memberPolicyDetailsModel.getTotalRemainingLimitAmount()
						&& memberPolicyDetailsModel.getTotalRemainingLimitAmount().compareTo(BigDecimal.ZERO) > 0;
				if (hasTotalRemainingLimitAmount) {
					verifyMemberPolicyNumber(prescriptionMetadata, memberPolicyDetailsModel, idNumber,
							policyResponseModel, requestId, payerId, drugList);
				} else {
					throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.REJECTED.value(),
							PolicyResponseStatusDescription.NO_REMAINING_LIMIT.value(),
							String.valueOf(HttpStatus.OK.value()),
							PolicyResponseStatusDescription.NO_REMAINING_LIMIT.value(),
							PolicyConsumptionDenialCodes.BR_PC_NO_REMAINING_LIMIT.value(), idNumber));
				}
			} else {
				throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.INVALID.value(),
						PolicyResponseStatusDescription.REQUEST_IS_INACTIVE.value(),
						String.valueOf(HttpStatus.BAD_REQUEST.value()),
						PolicyResponseStatusDescription.REQUEST_IS_INACTIVE.value(),
						PolicyConsumptionDenialCodes.BR_PC_INACTIVE.value(), idNumber));
			}
		} else {
			throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.INVALID.value(),
					PolicyResponseStatusDescription.REQUEST_NOT_FOUND.value(),
					String.valueOf(HttpStatus.BAD_REQUEST.value()),
					PolicyResponseStatusDescription.REQUEST_NOT_FOUND.value(),
					PolicyConsumptionDenialCodes.BR_PC_NO_REQUEST.value(), idNumber));
		}
		return policyResponseModel;
	}

	private void verifyMemberPolicyNumber(PrescriptionMetadata prescriptionMetadata,
			MemberPolicyDetailsModel memberPolicyDetailsModel, String idNumber, PolicyResponseModel policyResponseModel,
			String requestId, String payerId, List<DrugListModel> drugList) throws PolicyException {
		if (comparePolicyNumbers(prescriptionMetadata.getPolicyNumber().toString(),
				memberPolicyDetailsModel.getPolicyNumber())) {
			BigDecimal beneftiRemainingLimitValue = memberPolicyDetailsModel.getRemainingLimitValue();
			if (null == beneftiRemainingLimitValue || beneftiRemainingLimitValue.compareTo(BigDecimal.ZERO) == 0) {
				throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.INVALID.value(),
						PolicyResponseStatusDescription.DISPENSE_NO_REMAINING_LIMIT.value(),
						String.valueOf(HttpStatus.BAD_REQUEST.value()),
						PolicyResponseStatusDescription.DISPENSE_NO_REMAINING_LIMIT.value(),
						PolicyConsumptionDenialCodes.BR_PC_NO_REMAINING_LIMIT.value(), idNumber));
			} else {
				policyConsumptionCheckForDispensing(prescriptionMetadata, drugList, memberPolicyDetailsModel,
						policyResponseModel, requestId, payerId);
			}
		} else {
			throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.INVALID.value(),
					PolicyResponseStatusDescription.EXPIRED_PRESCRIPTION.value(),
					String.valueOf(HttpStatus.BAD_REQUEST.value()),
					PolicyResponseStatusDescription.EXPIRED_PRESCRIPTION.value(),
					PolicyConsumptionDenialCodes.BR_PC_EXPIRED.value(), idNumber));
		}

	}

	private void policyConsumptionCheckForDispensing(PrescriptionMetadata prescriptionMetadata,
			List<DrugListModel> drugList, MemberPolicyDetailsModel memberPolicyDetailsModel,
			PolicyResponseModel policyResponseModel, String requestId, String payerId) {
		BigDecimal benefitRemainingLimitValue = memberPolicyDetailsModel.getRemainingLimitValue();
		BigDecimal payerShare = prescriptionMetadata.getPayerShare();
		BigDecimal patientShare = prescriptionMetadata.getPatientShare();
		boolean isPartialDispense = null != drugList && !drugList.isEmpty();
		if (benefitRemainingLimitValue.compareTo(BigDecimal.ZERO) > 0
				&& benefitRemainingLimitValue.compareTo(payerShare) >= 0) {
			String statusDescription = isPartialDispense ? PolicyResponseStatusDescription.PARTIAL_DISPENSED.value()
					: PolicyResponseStatusDescription.DISPENSED.value();
			populateAcceptedResponse(policyResponseModel, patientShare, statusDescription, payerShare,
					memberPolicyDetailsModel, drugList, requestId);
		} else if (benefitRemainingLimitValue.compareTo(new BigDecimal(0)) > 0
				&& benefitRemainingLimitValue.compareTo(payerShare) < 0) {
			String statusDescription = isPartialDispense
					? PolicyResponseStatusDescription.PARTIALLY_DISPENSED_WITH_EXTRA_PATIENT_SHARE.value()
					: PolicyResponseStatusDescription.DISPENSED_WITH_EXTRA_PATIENT_SHARE.value();
			BigDecimal extraPatientShare = payerShare.subtract(benefitRemainingLimitValue);
			patientShare = patientShare.add(extraPatientShare);
			populateAcceptedResponse(policyResponseModel, patientShare,
					statusDescription.replace("<extraPatientShare>", extraPatientShare.toString())
							.replace("<totalPatientShare>", patientShare.toString())
							.replace("<currency>", prescriptionMetadata.getBenefitLimitCurr()),
					payerShare.subtract(extraPatientShare), memberPolicyDetailsModel, drugList, requestId);
		}
		if (!isPartialDispense) {
			markPrescriptionInactive(prescriptionMetadata);
		}
	}

	private void markPrescriptionInactive(PrescriptionMetadata prescriptionMetadata) {
		Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
		prescriptionMetadata.setUpdateDate(timestamp);
		prescriptionMetadata.setActivePrescription("0");
		prescriptionMetadataRepository.save(prescriptionMetadata);
		populateAuditLog(AuditType.UPDATE, prescriptionMetadata.getId(), EntitiesName.PRESCRIPTION_METADATA,
				prescriptionMetadata);
	}

	public PolicyResponseModel policyCheckForCancellation(String idNumber, String requestId, String payerId,
			String benefitCase, String benefitCode, String providerId,
			ContentCachingRequestWrapper contentCachingRequestWrapper) throws PolicyException {
		handleTransactionLog(contentCachingRequestWrapper, payerId, providerId, requestId,
				new BigDecimal(BusinessRulesPrivilage.PRESCRIPTION_CANCELLATION_PRIVILAGE.value()));
		Optional<PrescriptionMetadata> prescriptionMetadataOpt = prescriptionMetadataRepository
				.findByRequestId(requestId);
		if (prescriptionMetadataOpt.isPresent()) {
			PrescriptionMetadata prescriptionMetadata = prescriptionMetadataOpt.get();
			if (prescriptionMetadata.getActivePrescription().equals("1")) {
				return managePrescriptionMetadataForCancellation(prescriptionMetadata, idNumber, payerId, benefitCase,
						benefitCode, requestId, providerId);
			} else {
				throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.REJECTED.value(),
						PolicyResponseStatusDescription.REQUEST_IS_INACTIVE.value(),
						String.valueOf(HttpStatus.OK.value()),
						PolicyResponseStatusDescription.REQUEST_IS_INACTIVE.value(),
						PolicyConsumptionDenialCodes.BR_PC_INACTIVE.value(), idNumber));
			}
		} else {
			throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.INVALID.value(),
					PolicyResponseStatusDescription.INVALID_PRESCRIPTION.value(),
					String.valueOf(HttpStatus.BAD_REQUEST.value()),
					PolicyResponseStatusDescription.INVALID_PRESCRIPTION.value(),
					PolicyConsumptionDenialCodes.BR_PC_INVALID_PRESCRIPTION.value(), idNumber));
		}
	}

	private PolicyResponseModel managePrescriptionMetadataForCancellation(PrescriptionMetadata prescriptionMetadata,
			String idNumber, String payerId, String benefitCase, String benefitCode, String requestId,
			String providerId) throws PolicyException {
		AccountToAccountAssociation providerPayerCode = accountToAccountAssociationRepository
				.findByIdSourceAndIdDestinationsAndIsEnabled(new BigDecimal(providerId), new BigDecimal(payerId), true);
		MemberPolicyDetailsModel memberPolicyDetailsModel = memberDetailsService.fetchMemberPolicyDetails(idNumber,
				benefitCode, benefitCase, payerId, providerId, providerPayerCode.getCode());
		BigDecimal totalRemainingLimitAmount = memberPolicyDetailsModel.getTotalRemainingLimitAmount();
		boolean hasTotalRemainingLimitAmount = null != totalRemainingLimitAmount
				&& totalRemainingLimitAmount.compareTo(BigDecimal.ZERO) > 0;
		if (hasTotalRemainingLimitAmount) {
			if (comparePolicyNumbers(prescriptionMetadata.getPolicyNumber().toString(),
					memberPolicyDetailsModel.getPolicyNumber())) {
				return manageValidResponseForCancellation(prescriptionMetadata, memberPolicyDetailsModel, requestId);
			} else {
				throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.REJECTED.value(),
						PolicyResponseStatusDescription.EXPIRED_PRESCRIPTION.value(),
						String.valueOf(HttpStatus.OK.value()),
						PolicyResponseStatusDescription.EXPIRED_PRESCRIPTION.value(),
						PolicyConsumptionDenialCodes.BR_PC_EXPIRED.value(), idNumber));
			}
		} else {
			throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.REJECTED.value(),
					PolicyResponseStatusDescription.NO_REMAINING_LIMIT.value(), String.valueOf(HttpStatus.OK.value()),
					PolicyResponseStatusDescription.NO_REMAINING_LIMIT.value(),
					PolicyConsumptionDenialCodes.BR_PC_NO_REMAINING_LIMIT.value(), idNumber));
		}
	}

	private boolean comparePolicyNumbers(String metaDataPolicyNumber, String memberPolicyNumber) {
		return metaDataPolicyNumber.equals(memberPolicyNumber);
	}

	private PolicyResponseModel manageValidResponseForCancellation(PrescriptionMetadata prescriptionMetadata,
			MemberPolicyDetailsModel memberPolicyDetailsModel, String requestId) {
		prescriptionMetadata.setActivePrescription("0");
		prescriptionMetadataRepository.save(prescriptionMetadata);
		populateAuditLog(AuditType.UPDATE, prescriptionMetadata.getId(), EntitiesName.PRESCRIPTION_METADATA,
				prescriptionMetadata);
		PolicyResponseModel policyResponseModel = new PolicyResponseModel();
		populateAcceptedResponse(policyResponseModel, prescriptionMetadata.getPatientShare(),
				PolicyResponseStatusDescription.PASSED_POLICY_CHECK.value(), prescriptionMetadata.getPayerShare(),
				memberPolicyDetailsModel, null, requestId);
		return policyResponseModel;
	}

	public PolicyResponseModel populateBadRequestResponse(List<String> errors, HttpServletRequest request,
			ContentCachingRequestWrapper requestWrapper) {
		Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String idNumber = (String) pathVariables.get("idNumber");
		String requestId = (String) pathVariables.get("requestId");
		String denialCode = PolicyConsumptionDenialCodes.BR_PC_INVALID.value();
		String error = String.join(",", errors);
		String payerId = "";
		String providerId = "";
		BigDecimal transactionId = new BigDecimal(BusinessRulesPrivilage.PBM_PRESCRIPTION.value());
		if (StringUtils.isBlank(requestId)) {
			PolicyRequestModel policyRequestModel = mapperService.mapPolicyRequestModel(requestWrapper);
			if (null != policyRequestModel && (StringUtils.isNotBlank(policyRequestModel.getRequestType())
					&& (policyRequestModel.getRequestType().equals(RequestType.NEW.value())
							|| policyRequestModel.getRequestType().equals(RequestType.FOLLOWUP.value())))) {
				requestId = policyRequestModel.getRequestId();
				payerId = policyRequestModel.getPayerId();
				providerId = policyRequestModel.getProviderId();
				transactionId = policyRequestModel.getRequestType().equals(RequestType.NEW.value())
						? new BigDecimal(BusinessRulesPrivilage.NEW_PRESCRIPTION_PRIVILAGE.value())
						: new BigDecimal(BusinessRulesPrivilage.PRESCRIPTION_FOLLOWUP_PRIVILAGE.value());
			} else {
				CancellAndDispensePolicyRequestModel cancellAndDispensePolicyRequestModel = mapperService
						.mapCancellAndDispensePolicyRequestModel(requestWrapper);
				if (null != cancellAndDispensePolicyRequestModel
						&& StringUtils.isNotBlank(cancellAndDispensePolicyRequestModel.getRequestType())
						&& (cancellAndDispensePolicyRequestModel.getRequestType()
								.equals(RequestType.CANCELLATION.value())
								|| cancellAndDispensePolicyRequestModel.getRequestType()
										.equals(RequestType.PARTIALLY_DISPENSED.value())
								|| cancellAndDispensePolicyRequestModel.getRequestType()
										.equals(RequestType.DISPENSED.value()))) {
					requestId = cancellAndDispensePolicyRequestModel.getRequestId();
					payerId = cancellAndDispensePolicyRequestModel.getPayerId();
					providerId = cancellAndDispensePolicyRequestModel.getProviderId();
					transactionId = cancellAndDispensePolicyRequestModel.getRequestType()
							.equals(RequestType.CANCELLATION.value())
									? new BigDecimal(BusinessRulesPrivilage.PRESCRIPTION_CANCELLATION_PRIVILAGE.value())
									: new BigDecimal(BusinessRulesPrivilage.DISPENSE_PRIVILAGE.value());
				} else {
					DispensibleDrugsRequestModel dispensableDrugsRequestModel = mapperService
							.mapDispensableDrugsRequestModel(requestWrapper);
					if (null != dispensableDrugsRequestModel) {
						requestId = dispensableDrugsRequestModel.getRequestId();
						payerId = dispensableDrugsRequestModel.getPayerId();
						providerId = dispensableDrugsRequestModel.getProviderId();
					} else {
						DeactivatePrescriptionRequestModel deactivatePrescriptionRequestModel = mapperService
								.mapDeactivatePrescriptionRequestModel(requestWrapper);
						requestId = deactivatePrescriptionRequestModel.getRequestId();
						payerId = deactivatePrescriptionRequestModel.getPayerId();
						providerId = deactivatePrescriptionRequestModel.getProviderId();
					}
					transactionId = new BigDecimal(BusinessRulesPrivilage.DISPENSE_PRIVILAGE.value());
				}
			}
		}
		PolicyResponseModel responseModel = new PolicyResponseModel();
		responseModel.setRequestId(requestId);
		responseModel.setHttpStatusCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		responseModel.setStatus(PolicyResponseStatus.INVALID.value());
		responseModel.setDenialCode(denialCode);
		fetchDenialCodeDetails(idNumber, denialCode, responseModel);
		String denialDescription = responseModel.getDenialDescription().replace(".", " because " + error);
		responseModel.setDenialDescription(denialDescription);
		responseModel.setHttpStatusDescription(denialDescription);
		responseModel.setStatusDescription(denialDescription);
		handleTransactionLog(requestWrapper, payerId, providerId, requestId, transactionId);
		return responseModel;
	}

	public PolicyResponseModel populateServerErrorResponse(Exception ex, HttpServletRequest request,
			ContentCachingRequestWrapper requestWrapper) {
		Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String idNumber = (String) pathVariables.get("idNumber");
		String requestId = (String) pathVariables.get("requestId");
		String denialCode = PolicyConsumptionDenialCodes.BR_PC_FAILED.value();
		String payerId = "";
		String providerId = "";
		BigDecimal transactionId = new BigDecimal(BusinessRulesPrivilage.PBM_PRESCRIPTION.value());
		if (StringUtils.isBlank(requestId)) {
			PolicyRequestModel policyRequestModel = mapperService.mapPolicyRequestModel(requestWrapper);
			if (null != policyRequestModel && (StringUtils.isNotBlank(policyRequestModel.getRequestType())
					&& (policyRequestModel.getRequestType().equals(RequestType.NEW.value())
							|| policyRequestModel.getRequestType().equals(RequestType.FOLLOWUP.value())))) {
				requestId = policyRequestModel.getRequestId();
				payerId = policyRequestModel.getPayerId();
				providerId = policyRequestModel.getProviderId();
				transactionId = policyRequestModel.getRequestType().equals(RequestType.NEW.value())
						? new BigDecimal(BusinessRulesPrivilage.NEW_PRESCRIPTION_PRIVILAGE.value())
						: new BigDecimal(BusinessRulesPrivilage.PRESCRIPTION_FOLLOWUP_PRIVILAGE.value());
			} else {
				CancellAndDispensePolicyRequestModel cancellAndDispensePolicyRequestModel = mapperService
						.mapCancellAndDispensePolicyRequestModel(requestWrapper);
				if (null != cancellAndDispensePolicyRequestModel
						&& StringUtils.isNotBlank(cancellAndDispensePolicyRequestModel.getRequestType())
						&& (cancellAndDispensePolicyRequestModel.getRequestType()
								.equals(RequestType.CANCELLATION.value())
								|| cancellAndDispensePolicyRequestModel.getRequestType()
										.equals(RequestType.PARTIALLY_DISPENSED.value())
								|| cancellAndDispensePolicyRequestModel.getRequestType()
										.equals(RequestType.DISPENSED.value()))) {
					requestId = cancellAndDispensePolicyRequestModel.getRequestId();
					payerId = cancellAndDispensePolicyRequestModel.getPayerId();
					providerId = cancellAndDispensePolicyRequestModel.getProviderId();
					transactionId = cancellAndDispensePolicyRequestModel.getRequestType()
							.equals(RequestType.CANCELLATION.value())
									? new BigDecimal(BusinessRulesPrivilage.PRESCRIPTION_CANCELLATION_PRIVILAGE.value())
									: new BigDecimal(BusinessRulesPrivilage.DISPENSE_PRIVILAGE.value());
				} else {
					DispensibleDrugsRequestModel dispensableDrugsRequestModel = mapperService
							.mapDispensableDrugsRequestModel(requestWrapper);
					if (null != dispensableDrugsRequestModel) {
						requestId = dispensableDrugsRequestModel.getRequestId();
						payerId = dispensableDrugsRequestModel.getPayerId();
						providerId = dispensableDrugsRequestModel.getProviderId();
					} else {
						DeactivatePrescriptionRequestModel deactivatePrescriptionRequestModel = mapperService
								.mapDeactivatePrescriptionRequestModel(requestWrapper);
						requestId = deactivatePrescriptionRequestModel.getRequestId();
						payerId = deactivatePrescriptionRequestModel.getPayerId();
						providerId = deactivatePrescriptionRequestModel.getProviderId();
					}
					transactionId = new BigDecimal(BusinessRulesPrivilage.DISPENSE_PRIVILAGE.value());
				}
			}
		}
		PolicyResponseModel responseModel = new PolicyResponseModel();
		responseModel.setRequestId(requestId);
		responseModel.setHttpStatusCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		responseModel.setStatus(PolicyResponseStatus.FAILED.value());
		responseModel.setDenialCode(denialCode);
		fetchDenialCodeDetails(idNumber, denialCode, responseModel);
		String denialDescription = responseModel.getDenialDescription();
		responseModel.setHttpStatusDescription(denialDescription);
		responseModel.setStatusDescription(denialDescription);
		handleTransactionLog(requestWrapper, payerId, providerId, requestId, transactionId);
		return responseModel;
	}

	private void fetchDenialCodeDetails(String idNumber, String denialCode, PolicyResponseModel policyResponseModel) {
		Optional<CommonDenial> commonDenialsOp = commonDenialsRepository.findByDenialCode(denialCode);
		if (commonDenialsOp.isPresent()) {
			policyResponseModel
					.setDenialDescription(commonDenialsOp.get().getDenialDescription().replace("<IdNumber>", idNumber));
		}
	}

	private void setPayerAndPatientShareForDrugs(List<DrugListModel> drugList, PolicyResponseModel policyResponseModel,
			BigDecimal patientShare, String currency) {
		drugList.stream().forEach(drug -> {
			BigDecimal drugAmount = drug.getAmount();
			if (drugAmount != null && patientShare != null) {
				BigDecimal patientShareAmount = drugAmount.multiply(patientShare.divide(new BigDecimal(100)));
				drug.setPatientShare(patientShareAmount);
				drug.setPayerShare(drugAmount.subtract(patientShareAmount));
				drug.setPatientShareCurrency(currency);
				drug.setPayerShareCurrency(currency);
			}
		});
		policyResponseModel.setDrugList(drugList);
	}

	public PolicyResponseModel deactivatePrescription(String idNumber, String requestId,
			ContentCachingRequestWrapper contentCachingRequestWrapper, String payerId, String providerId)
			throws PolicyException {
		handleTransactionLog(contentCachingRequestWrapper, payerId, providerId, requestId,
				new BigDecimal(BusinessRulesPrivilage.DISPENSE_PRIVILAGE.value()));
		Optional<PrescriptionMetadata> prescriptionMetadataOpt = prescriptionMetadataRepository
				.findByRequestId(requestId);
		if (prescriptionMetadataOpt.isPresent()) {
			PrescriptionMetadata prescriptionMetadata = prescriptionMetadataOpt.get();
			if (prescriptionMetadata.getActivePrescription().equals("1")) {
				markPrescriptionInactive(prescriptionMetadata);
			} else {
				throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.INVALID.value(),
						PolicyResponseStatusDescription.REQUEST_IS_INACTIVE.value(),
						String.valueOf(HttpStatus.BAD_REQUEST.value()),
						PolicyResponseStatusDescription.REQUEST_IS_INACTIVE.value(),
						PolicyConsumptionDenialCodes.BR_PC_INACTIVE.value(), idNumber));
			}
		} else {
			throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.INVALID.value(),
					PolicyResponseStatusDescription.INVALID_REQUEST.value(),
					String.valueOf(HttpStatus.BAD_REQUEST.value()),
					PolicyResponseStatusDescription.INVALID_REQUEST.value(),
					PolicyConsumptionDenialCodes.BR_PC_INVALID.value(), idNumber));
		}
		return null;
	}

	private void handleTransactionLog(ContentCachingRequestWrapper contentCachingRequestWrapper, String payerId,
			String providerId, String reqeustId, BigDecimal transactionId) {
		try {
			Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
			TransactionLog transactionLog = new TransactionLog(payerId, providerId, timestamp, reqeustId,
					TransactionType.POLICY_CONSUMPTION.value(), TransactionStatusType.RECEIVED.value(), transactionId);
			transactionLog = transactionLogRepository.save(transactionLog);
			sessionService.setTransactionLogIdInSession(contentCachingRequestWrapper,
					transactionLog.getTransactionLogId());
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("{} {} .", ExceptionLogs.FAILED_TRANSACTION.value(), reqeustId);
		}
	}
}
