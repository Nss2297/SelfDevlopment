package com.waseel.prescription.service.management;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.prescription.model.br.SensitiveDrugRequestModel;
import com.waseel.prescription.model.br.SensitiveDrugResponseModel;
import com.waseel.prescription.model.eligibility.EligibilityResponseModel;
import com.waseel.prescription.model.exclusion.DrugExclusionResponseModel;
import com.waseel.prescription.model.formulary.DrugFormularyResponseModel;
import com.waseel.prescription.model.policyconsumption.CancellAndDispensePolicyRequestModel;
import com.waseel.prescription.model.policyconsumption.DeactivatePrescriptionRequestModel;
import com.waseel.prescription.model.policyconsumption.DrugListModel;
import com.waseel.prescription.model.policyconsumption.PolicyRequestModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;
import com.waseel.prescription.model.prescription.DrugList;
import com.waseel.prescription.service.clienthandler.EligibilityRestHandler;
import com.waseel.prescription.service.clienthandler.PolicyConsumptionRestHandler;
import com.waseel.prescription.service.clienthandler.RestHandler;

@Service
public class BusinessRuleService {

	private final Logger log = LoggerFactory.getLogger(BusinessRuleService.class);

	@Autowired
	private EligibilityRestHandler eligibilityRestHandler;

	@Autowired
	private PolicyConsumptionRestHandler policyConsumptionRestHandler;

	@Autowired
	private RestHandler restHandler;

	public EligibilityResponseModel eligibilityCheck(String idNumber, String payerId, String providerId,
			String requestId) {
		return eligibilityRestHandler.handleMemberEligibility(idNumber, payerId, providerId, requestId);
	}

	public PolicyResponseModel policyConsumptionCheck(String idNumber, String benefitCode, String benefitCase,
			String prescriptionValue, String requestId, String payerId, List<DrugListModel> drugList, String providerId,
			String requestType) {
		return policyConsumptionRestHandler.handleMemberPolicyConsumption(idNumber, new PolicyRequestModel(benefitCode,
				benefitCase, prescriptionValue, requestId, payerId, drugList, providerId, requestType));
	}

	public List<DrugFormularyResponseModel> drugFormularyCheck(String payerId, String idNumber, List<DrugList> drugList,
			String requestId) {
		return restHandler.handleDrugFormularyRequest(payerId, idNumber, getDrugCodeList(drugList), requestId);
	}

	public DrugExclusionResponseModel checkDrugExclusion(List<DrugList> drugList, String requestId,
			String licenseNumber, String speciality, String payerId, String providerId) {
		return restHandler.handleDrugExclusion(getDrugCodeList(drugList), requestId, licenseNumber, speciality, payerId,
				providerId);
	}

	private List<String> getDrugCodeList(List<DrugList> drugLists) {
		return drugLists.stream().map(DrugList::getDrugCode).collect(Collectors.toList());
	}

	public PolicyResponseModel policyConsumptionCheckForCancellation(String idNumber,
			CancellAndDispensePolicyRequestModel policyCancellationRequestModel) {
		return policyConsumptionRestHandler.managePolicyConsumptionForCancellation(idNumber,
				policyCancellationRequestModel);
	}

	public PolicyResponseModel policyConsumptionCheckForDispense(String idNumber,
			CancellAndDispensePolicyRequestModel policyCancellationRequestModel) {
		return policyConsumptionRestHandler.managePolicyConsumptionForDispense(idNumber,
				policyCancellationRequestModel);
	}

	public PolicyResponseModel deactivatePrescriptionAfterCompletingParialDispense(String idNumber,
			DeactivatePrescriptionRequestModel deactivatePrescriptionRequestModel) {
		return policyConsumptionRestHandler.managePrescriptionAfterCompletingPartialDispense(idNumber,
				deactivatePrescriptionRequestModel);
	}
	
	public SensitiveDrugResponseModel validateNewPrescriptionForSensitiveDrugs(SensitiveDrugRequestModel sensitiveDrugRequestModel) {
		return restHandler.sendPrescriptionToBrServiceForSensitiveDrug(sensitiveDrugRequestModel);
	}
}
