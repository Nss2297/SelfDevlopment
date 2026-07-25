package com.waseel.prescription.service.policyconsumption;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.prescription.model.enums.PolicyConsumptionStatus;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.policyconsumption.DrugListModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;
import com.waseel.prescription.model.policyconsumption.RecalculatedDrugListModel;
import com.waseel.prescription.model.policyconsumption.RecalculatedPayerAndPatientShareModel;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.MemberPolicyUsage;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.repository.prescriptionservice.MemberInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberPolicyUsageRepository;
import com.waseel.prescription.service.management.BusinessRuleService;
import com.waseel.prescription.service.prescriptions.FetchBenefitCodeService;

@Service
public class PolicyConsumptionService {

	private final Logger logger = LoggerFactory.getLogger(PolicyConsumptionService.class);

	@Autowired
	private BusinessRuleService businessRuleService;

	@Autowired
	private MemberPolicyUsageRepository memberPolicyUsageRepository;

	@Autowired
	private MemberInfoRepository memberInfoRepository;

	@Autowired
	private FetchBenefitCodeService fetchBenefitCodeService;

	public RecalculatedPayerAndPatientShareModel fetchPatientAndPatientShareAfterUpdationByPayer(
			List<RecalculatedDrugListModel> drugList, PrescriptionRequest prescriptionRequest,
			String physicianLicenseNumber, String idNumber) {
		RecalculatedPayerAndPatientShareModel recalculatedPayerAndPatientShareModel = new RecalculatedPayerAndPatientShareModel();
		PolicyResponseModel policyResponseModel = null;
		String requestId = prescriptionRequest.getRequestId();
		if (StringUtils.isBlank(idNumber)) {
			idNumber = fetchIdNumber(requestId);
		}
		String providerId = prescriptionRequest.getProviderId();
		String ePrescriptionReferenceNumber = prescriptionRequest.getePrescriptionReferenceNumber();
		BigDecimal totalPrice = BigDecimal.ZERO;
		List<DrugListModel> policyConsumptionDrugList = new ArrayList<>();
		totalPrice = calculateTotalPriceAndPopulatePolicyConsumptionDrugList(drugList, totalPrice,
				policyConsumptionDrugList);
		if (!policyConsumptionDrugList.isEmpty()) {
			String payerId = prescriptionRequest.getPayerId();
			String benefitCode = fetchBenefitCodeService.fetchBenefitCodeByRequestId(requestId);
			logger.info("Policy consumption check for prescription: [{}] after manual review by payer: [{}]",
					ePrescriptionReferenceNumber, payerId);
			policyResponseModel = businessRuleService.policyConsumptionCheck(idNumber, benefitCode,
					prescriptionRequest.getCaseType().toUpperCase(), String.valueOf(totalPrice), requestId, payerId,
					policyConsumptionDrugList, providerId, RequestType.FOLLOWUP.value());
		}
		if (null != policyResponseModel) {
			if (StringUtils.isNotBlank(policyResponseModel.getStatus())
					&& policyResponseModel.getStatus().equals(PolicyConsumptionStatus.APPROVED.getValue())) {
				populateMemberPolicyUsage(prescriptionRequest.getPayerId(), idNumber, ePrescriptionReferenceNumber,
						policyResponseModel, providerId, RequestType.FOLLOWUP.name());
				populatePayerAndPatientShare(policyResponseModel, recalculatedPayerAndPatientShareModel, drugList);
			}
		} else {
			recalculatedPayerAndPatientShareModel.setTotalPatientShare(totalPrice);
			recalculatedPayerAndPatientShareModel.setTotalPayerShare(totalPrice);
			recalculatedPayerAndPatientShareModel.setTotalPrescriptionValue(totalPrice);
			recalculatedPayerAndPatientShareModel.setDrugList(drugList);
		}
		return recalculatedPayerAndPatientShareModel;
	}

	private String fetchIdNumber(String requestId) {
		Optional<MemberInfo> memberInfoOpt = memberInfoRepository.findByRequestId(requestId);
		if (memberInfoOpt.isPresent()) {
			return memberInfoOpt.get().getIdNumber().toString();
		}
		return null;
	}

	private BigDecimal calculateTotalPriceAndPopulatePolicyConsumptionDrugList(List<RecalculatedDrugListModel> drugList,
			BigDecimal totalPrice, List<DrugListModel> policyConsumptionDrugList) {
		for (int index = 0; index < drugList.size(); index++) {
			String payerDrugDecision = drugList.get(index).getStatus();
			if (payerDrugDecision.equals(ServiceStatus.APPROVED.name())) {
				DrugListModel drugListModel = new DrugListModel();
				BigDecimal requestedQuantity = drugList.get(index).getQuantity();
				BigDecimal drugPrice = drugList.get(index).getUnitPrice();
				BigDecimal drugCost = requestedQuantity.multiply(drugPrice).setScale(2, RoundingMode.HALF_UP);
				totalPrice = totalPrice.add(drugCost);
				drugListModel.setDrugCode(drugList.get(index).getDrugCode());
				drugListModel.setAmount(drugCost);
				policyConsumptionDrugList.add(drugListModel);
			} else if (payerDrugDecision.equals(ServiceStatus.REJECTED.name())) {
				drugList.get(index).setPatientShare(BigDecimal.ZERO);
				drugList.get(index).setPayerShare(BigDecimal.ZERO);
			}
		}
		return totalPrice;
	}

	private void populateMemberPolicyUsage(String payerId, String idNumber, String ePrescriptionReferenceNumber,
			PolicyResponseModel policyResponseModel, String providerId, String prescriptionStatus) {
		MemberPolicyUsage memberPolicyUsage = new MemberPolicyUsage(payerId, providerId,
				policyResponseModel.getMemberId(), Long.valueOf(idNumber), policyResponseModel.getPolicyNumber(),
				policyResponseModel.getPolicyClass(), policyResponseModel.getPolicyBenefit(),
				null != policyResponseModel.getBenefitLimitValue() ? policyResponseModel.getBenefitLimitValue()
						: new BigDecimal(0),
				policyResponseModel.getBenefitLimitCurrency(), new BigDecimal(policyResponseModel.getRemainingLimit()),
				policyResponseModel.getBenefitRemainingLimitCurrency(), ePrescriptionReferenceNumber,
				prescriptionStatus);
		memberPolicyUsageRepository.save(memberPolicyUsage);
	}

	private void populatePayerAndPatientShare(PolicyResponseModel policyResponseModel,
			RecalculatedPayerAndPatientShareModel recalculatedPayerAndPatientShareModel,
			List<RecalculatedDrugListModel> drugList) {
		BigDecimal totalPayerShare = new BigDecimal(policyResponseModel.getPayerShare()).setScale(2,
				RoundingMode.HALF_UP);
		BigDecimal totalPatientShare = new BigDecimal(policyResponseModel.getPatientShare()).setScale(2,
				RoundingMode.HALF_UP);
		recalculatedPayerAndPatientShareModel.setTotalPatientShare(totalPatientShare);
		recalculatedPayerAndPatientShareModel.setTotalPayerShare(totalPayerShare);
		recalculatedPayerAndPatientShareModel
				.setTotalPrescriptionValue(totalPayerShare.add(totalPatientShare).setScale(2, RoundingMode.HALF_UP));
		drugList.stream().forEach(drug -> {
			policyResponseModel.getDrugList().stream()
					.filter(policyCheckedDrug -> drug.getDrugCode().equals(policyCheckedDrug.getDrugCode())).findAny()
					.ifPresent(policyCheckedDrug -> {
						drug.setPatientShare(policyCheckedDrug.getPatientShare().setScale(2, RoundingMode.HALF_UP));
						drug.setPayerShare(policyCheckedDrug.getPayerShare().setScale(2, RoundingMode.HALF_UP));
					});
		});
		recalculatedPayerAndPatientShareModel.setDrugList(drugList);
	}
}
