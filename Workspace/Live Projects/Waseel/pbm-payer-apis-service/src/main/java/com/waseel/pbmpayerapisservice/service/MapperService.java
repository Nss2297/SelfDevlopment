package com.waseel.pbmpayerapisservice.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.pbmpayerapisservice.model.InvalidResponseModel;

@Service
public class MapperService {

	public InvalidResponseModel mapInvalidResponseModel(String response) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(response, InvalidResponseModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
