package com.waseel.eligibility.service.portal;

import org.springframework.stereotype.Service;

import com.waseel.eligibility.client.portal.model.TransactionWrapper;
import com.waseel.eligibility.model.EligibilityResponseModel;

@Service
public class EligibilityResponseHandler {

	public EligibilityResponseModel handleResponse(TransactionWrapper transactionWrapperResponse,
			EligibilityResponseModel responseModel, String idNumber) {
		if (transactionWrapperResponse != null && transactionWrapperResponse.getErrorResponse() != null
				&& transactionWrapperResponse.getErrorResponse().size() > 0) {
			handleErrorResponse(responseModel, transactionWrapperResponse);
		} else {
			handleValidResponse(responseModel, transactionWrapperResponse);
		}
		return responseModel;
	}

	private void handleErrorResponse(EligibilityResponseModel responseModel,
			TransactionWrapper transactionWrapperResponse) {
		if (transactionWrapperResponse != null && transactionWrapperResponse.getErrorResponse() != null
				&& transactionWrapperResponse.getErrorResponse().size() > 0) {
			StringBuilder errorStr = new StringBuilder();
			transactionWrapperResponse.getErrorResponse().forEach(errorResponseCT -> {
				errorStr.append(errorResponseCT.getErrorDescription());
			});
			populateResponseModel("Invalid", errorStr.toString(), responseModel, null, null);
		}
	}

	private void handleValidResponse(EligibilityResponseModel responseModel,
			TransactionWrapper transactionWrapperResponse) {
		if (transactionWrapperResponse != null && transactionWrapperResponse.getEligibilitySubmissionResponse() != null
				&& transactionWrapperResponse.getEligibilitySubmissionResponse().getStatus() != null) {
			String status = transactionWrapperResponse.getEligibilitySubmissionResponse().getStatus().getStatusCode()
					.toString();
			String statusDescription = transactionWrapperResponse.getEligibilitySubmissionResponse().getStatus()
					.getStatusDescription();
			String denialCode = transactionWrapperResponse.getEligibilitySubmissionResponse().getStatus()
					.getDenialCode();
			String referenceNumber = transactionWrapperResponse.getEligibilitySubmissionResponse().getStatus()
					.getReferenceNumber();
			populateResponseModel(status, statusDescription, responseModel, referenceNumber, denialCode);
		}
	}

	private void populateResponseModel(String status, String statusDescription, EligibilityResponseModel responseModel,
			String referenceNumber, String denialCode) {
		responseModel.setStatus(status);
		responseModel.setStatusDescription(statusDescription);
		responseModel.setDenialCode(denialCode);
		responseModel.setReferenceNumber(referenceNumber);
	}
}
