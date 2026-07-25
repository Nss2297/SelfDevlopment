package com.waseel.dssadminservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.dssadminservice.enums.AgeGroup;
import com.waseel.dssadminservice.enums.Gender;
import com.waseel.dssadminservice.exceptions.AdminException;
import com.waseel.dssadminservice.model.AgeRangeResponseModel;
import com.waseel.dssadminservice.model.LovResponseModel;
import com.waseel.dssadminservice.repository.mdss.LOVRepository;

@Service
public class LovService {

	@Autowired
	private LOVRepository lovRepository;

	public LovResponseModel getListOfValuesByKey(String key) {
		return new LovResponseModel(lovRepository.findByKey(key), null);
	}

	public LovResponseModel populateInvalidResponse(AdminException adminException) {
		List<String> errors = new ArrayList<>();
		errors.add(adminException.getMessage());
		return new LovResponseModel(null, errors);
	}

	public LovResponseModel populateFailedResponse() {
		List<String> errors = new ArrayList<>();
		errors.add("Internal Server Error.");
		return new LovResponseModel(null, errors);
	}

	public List<String> getGenders() {
		return Arrays.stream(Gender.values()).map(Gender::value).collect(Collectors.toList());
	}

	public Map<String, List<AgeRangeResponseModel>> getAgeList() {
		Map<String, List<AgeRangeResponseModel>> map = new HashMap<>();
		map.put("lov", AgeGroup.getAgeRange());
		return map;
	}
}