package com.waseel.pbmpayerapisservice.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.waseel.pbmpayerapisservice.model.InvalidResponseModel;
import com.waseel.pbmpayerapisservice.model.enums.ResponseErrors;

import feign.FeignException;

@Service
public class InvalidResponseService {

	@Autowired
	private MapperService mapperService;

	private String msgFailed = "FAILED";

	public InvalidResponseModel createInvalidResponseModel(FeignException ex) {
		InvalidResponseModel response = new InvalidResponseModel();
		int statusCode = ex.status();
		if (statusCode == HttpStatus.BAD_REQUEST.value() || statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
			response = mapperService.mapInvalidResponseModel(ex.contentUTF8());
		} else if (statusCode == -1) {
			response.setStatus(msgFailed);
			response.setStatusDescription("Not able to call Tawuniya server");
		} else if (statusCode == HttpStatus.REQUEST_TIMEOUT.value()) {
			List<String> errors = new ArrayList<>();
			errors.add("Request timeout.");
			response.setStatus(msgFailed);
			response.setErrors(errors);
		}
		return response;
	}

	public InvalidResponseModel createMethodArgumentNotValidInvalidResponse(MethodArgumentNotValidException ex) {
		InvalidResponseModel response = new InvalidResponseModel();
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
		String error = errors.toString().replace("[", "").replace("]", "");
		response.setStatus("INVALID");
		response.setStatusDescription(error);
		return response;
	}

	public InvalidResponseModel createFailedResponse() {
		InvalidResponseModel response = new InvalidResponseModel();
		response.setStatus(msgFailed);
		response.setStatusDescription(HttpStatus.INTERNAL_SERVER_ERROR.name());
		return response;
	}

	public InvalidResponseModel populateInvalidResponseForPolicyDetails(Long idNumber, String memberId,
			String policyNumber) {
		InvalidResponseModel invalidResponseModel = null;
		String error = validateRequestData(idNumber, memberId, policyNumber);
		if (StringUtils.isNotBlank(error)) {
			invalidResponseModel = new InvalidResponseModel();
			invalidResponseModel.setStatus("INVALID");
			invalidResponseModel.setStatusDescription(String.join(",", error));
		}
		return invalidResponseModel;
	}

	private String validateRequestData(Long idNumber, String memberId, String policyNumber) {
		if (null == idNumber && StringUtils.isBlank(memberId) && StringUtils.isBlank(policyNumber)) {
			return ResponseErrors.INVALID_REQUEST_PARAMETERS.value();
		}
		if (null == idNumber && (StringUtils.isNotBlank(policyNumber) && StringUtils.isBlank(memberId)
				|| StringUtils.isNotBlank(memberId) && StringUtils.isBlank(policyNumber))) {
			return ResponseErrors.MEMBER_ID_POLICY_NUMBER_ARE_MANDATORY.value();
		}
		return "";
	}

	public InvalidResponseModel populateInvalidResponseForMemberDetails(Long idNumber, String memberId,
			String policyNumber, String providerPayerCode) {
		if (!validMemberDetailsParams(idNumber, memberId, policyNumber, providerPayerCode)) {
			InvalidResponseModel invalidResponseModel = new InvalidResponseModel();
			invalidResponseModel.setStatus("INVALID");
			invalidResponseModel.setStatusDescription(ResponseErrors.INVALID_MEMBER_DETAILS_PARAMETERS.value());
			return invalidResponseModel;
		}
		return null;
	}

	private boolean validMemberDetailsParams(Long idNumber, String memberId, String policyNumber,
			String providerPayerCode) {
		return idNumber != null ? providerPayerCode != null
				: memberId != null && policyNumber != null && providerPayerCode != null;
	}
}
