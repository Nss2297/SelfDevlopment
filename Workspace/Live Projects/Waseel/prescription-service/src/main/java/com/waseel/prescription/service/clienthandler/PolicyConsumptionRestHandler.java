package com.waseel.prescription.service.clienthandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.waseel.prescription.clients.PolicyConsumptionClient;
import com.waseel.prescription.model.enums.ServiceName;
import com.waseel.prescription.model.policyconsumption.CancellAndDispensePolicyRequestModel;
import com.waseel.prescription.model.policyconsumption.DeactivatePrescriptionRequestModel;
import com.waseel.prescription.model.policyconsumption.DispensibleDrugsRequestModel;
import com.waseel.prescription.model.policyconsumption.PolicyRequestModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;
import com.waseel.prescription.service.mapper.MapperService;
import com.waseel.prescription.service.validation.TechnicalValidationService;

import feign.FeignException;

@Service
public class PolicyConsumptionRestHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(PolicyConsumptionRestHandler.class);

	@Autowired
	private PolicyConsumptionClient policyConsumptionClient;

	@Autowired
	private MapperService mapperService;

	@Autowired
	private TechnicalValidationService technicalValidationService;

	public PolicyResponseModel handleMemberPolicyConsumption(String idNumber, PolicyRequestModel policyRequestModel) {
		return sendMemberDetailsToPolicyService(idNumber, policyRequestModel);
	}

	public PolicyResponseModel managePolicyConsumptionForCancellation(String idNumber,
			CancellAndDispensePolicyRequestModel policyCancellationRequestModel) {
		return sendCancelPrescriptionDetailsToPolicyService(idNumber, policyCancellationRequestModel);
	}

	public PolicyResponseModel managePolicyConsumptionForDispense(String idNumber,
			CancellAndDispensePolicyRequestModel policyCancellationRequestModel) {
		return sendDispensePrescriptionDetailsToPolicyService(idNumber, policyCancellationRequestModel);
	}

	public PolicyResponseModel managePrescriptionAfterCompletingPartialDispense(String idNumber,
			DeactivatePrescriptionRequestModel deactivatePrescriptionRequestModel) {
		return sendPrescriptionDetailsToDeactive(idNumber, deactivatePrescriptionRequestModel);
	}

	public PolicyResponseModel getPayerAndPatientShareForDispensibleDrugs(String idNumber,
			DispensibleDrugsRequestModel dispensableDrugsRequestModel) {
		return sendDrugsToPolicyConsumptionService(idNumber, dispensableDrugsRequestModel);
	}

	public PolicyResponseModel sendMemberDetailsToPolicyService(String idNumber,
			PolicyRequestModel policyRequestModel) {
		try {
			LOGGER.info("Send member IdNumber: {} to Policy Consumption Service", idNumber);
			ResponseEntity<PolicyResponseModel> response = policyConsumptionClient.checkPolicyConsumption(idNumber,
					policyRequestModel);
			return response.getBody();
		} catch (FeignException e) {
			LOGGER.error(
					"FeignException Has Been Thrown While Reading The Response From Policy Consumption service For IdNumber : {}, failed with status [{}]",
					idNumber, e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapPolicyConsumptionResponse(e.contentUTF8());
			}
			if (e.status() == -1) {
				return (PolicyResponseModel) technicalValidationService.populateInvalidResForConnectionIssue(
						"Not able to call Policy-Consumption-Service", policyRequestModel.getRequestId(),
						ServiceName.POLICY_CONSUMPTION_SERVICE.getValue());
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Has Been Thrown While Reading The Response From Policy Consumption service For IdNumber :{} Error: {}",
					idNumber, e);
		}
		return null;
	}

	private PolicyResponseModel sendDispensePrescriptionDetailsToPolicyService(String idNumber,
			CancellAndDispensePolicyRequestModel policyCancellationRequestModel) {
		String requestId = policyCancellationRequestModel.getRequestId();
		try {
			LOGGER.info("Send member IdNumber: {} with RequestId: {} to Policy Consumption Service", idNumber,
					requestId);
			ResponseEntity<PolicyResponseModel> response = policyConsumptionClient
					.checkPolicyConsumptionForDispensePrescription(idNumber, policyCancellationRequestModel);
			return response.getBody();
		} catch (FeignException e) {
			LOGGER.error(
					"FeignException Has Been Thrown While Reading The Response From Policy Consumption service For IdNumber : {} and RequestId: {}, failed with status [{}]",
					idNumber, requestId, e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapPolicyConsumptionResponse(e.contentUTF8());
			}
			if (e.status() == -1) {
				return (PolicyResponseModel) technicalValidationService.populateInvalidResForConnectionIssue(
						"Not able to call Policy-Consumption-Service", requestId,
						ServiceName.POLICY_CONSUMPTION_SERVICE.getValue());
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Has Been Thrown While Reading The Response From Policy Consumption service For IdNumber :{} and RequestId:{} Error: {}",
					idNumber, requestId, e);
		}
		return null;
	}

	private PolicyResponseModel sendCancelPrescriptionDetailsToPolicyService(String idNumber,
			CancellAndDispensePolicyRequestModel policyCancellationRequestModel) {
		String requestId = policyCancellationRequestModel.getRequestId();
		try {
			LOGGER.info("Send member IdNumber: {} with RequestId: {} to Policy Consumption Service", idNumber,
					requestId);
			ResponseEntity<PolicyResponseModel> response = policyConsumptionClient
					.checkPolicyConsumptionForCancelPrescription(idNumber, policyCancellationRequestModel);
			return response.getBody();
		} catch (FeignException e) {
			LOGGER.error(
					"FeignException Has Been Thrown While Reading The Response From Policy Consumption service For IdNumber : {} and RequestId: {}, failed with status [{}]",
					idNumber, requestId, e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapPolicyConsumptionResponse(e.contentUTF8());
			}
			if (e.status() == -1) {
				return (PolicyResponseModel) technicalValidationService.populateInvalidResForConnectionIssue(
						"Not able to call Policy-Consumption-Service", requestId,
						ServiceName.POLICY_CONSUMPTION_SERVICE.getValue());
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Has Been Thrown While Reading The Response From Policy Consumption service For IdNumber :{} and RequestId:{} Error: {}",
					idNumber, requestId, e);
		}
		return null;
	}

	private PolicyResponseModel sendPrescriptionDetailsToDeactive(String idNumber,
			DeactivatePrescriptionRequestModel deactivatePrescriptionRequestModel) {
		String requestId = deactivatePrescriptionRequestModel.getRequestId();
		try {
			LOGGER.info("Send member IdNumber: {} with RequestId: {} to Policy Consumption Service", idNumber,
					requestId);
			ResponseEntity<PolicyResponseModel> response = policyConsumptionClient.markPrescriptionInactive(idNumber,
					deactivatePrescriptionRequestModel);
			return response.getBody();
		} catch (FeignException e) {
			LOGGER.error(
					"FeignException Has Been Thrown While Reading The Response From Policy Consumption service For IdNumber : {} and RequestId: {}, failed with status [{}]",
					idNumber, requestId, e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapPolicyConsumptionResponse(e.contentUTF8());
			}
			if (e.status() == -1) {
				return (PolicyResponseModel) technicalValidationService.populateInvalidResForConnectionIssue(
						"Not able to call Policy-Consumption-Service", requestId,
						ServiceName.POLICY_CONSUMPTION_SERVICE.getValue());
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Has Been Thrown While Reading The Response From Policy Consumption service For IdNumber :{} and RequestId:{} Error: {}",
					idNumber, requestId, e);
		}
		return null;
	}

	private PolicyResponseModel sendDrugsToPolicyConsumptionService(String idNumber,
			DispensibleDrugsRequestModel dispensableDrugsRequestModel) {
		String requestId = dispensableDrugsRequestModel.getRequestId();
		try {
			LOGGER.info("Send member IdNumber: [{}] to Policy Consumption Service for RequestID: [{}]", idNumber,
					requestId);
			ResponseEntity<PolicyResponseModel> response = policyConsumptionClient
					.fetchPayerAndPatientShareForDispensibleDrugs(idNumber,
							dispensableDrugsRequestModel.getBenefitCode(),
							dispensableDrugsRequestModel.getBenefitCase(), dispensableDrugsRequestModel.getPayerId(),
							requestId, dispensableDrugsRequestModel.getProviderId(),
							dispensableDrugsRequestModel.getDispensibleDrugs());
			return response.getBody();
		} catch (FeignException e) {
			LOGGER.error(
					"FeignException Has Been Thrown While Reading The Response From Policy Consumption service For IdNumber : {}, failed with status [{}]",
					idNumber, e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapPolicyConsumptionResponse(e.contentUTF8());
			}
			if (e.status() == -1) {
				return (PolicyResponseModel) technicalValidationService.populateInvalidResForConnectionIssue(
						"Not able to call Policy-Consumption-Service", requestId,
						ServiceName.POLICY_CONSUMPTION_SERVICE.getValue());
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Has Been Thrown While Reading The Response From Policy Consumption service For IdNumber :{} Error: {}",
					idNumber, e);
		}
		return null;
	}
}
