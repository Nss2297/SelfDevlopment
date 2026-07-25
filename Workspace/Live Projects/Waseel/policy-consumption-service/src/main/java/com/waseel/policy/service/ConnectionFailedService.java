package com.waseel.policy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.waseel.policy.model.client.InvalidResponseModel;
import com.waseel.policy.model.client.PbmPayerApiResponseModel;

@Service
public class ConnectionFailedService {
	public PbmPayerApiResponseModel pbmPayerApiResponseModelForConnectionFailure(String error) {
		List<String> errors = new ArrayList<>();
		errors.add(error);
		InvalidResponseModel invalidResponseModel = new InvalidResponseModel("FAILED", String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), errors);
		return new PbmPayerApiResponseModel(null, invalidResponseModel);
	}
}