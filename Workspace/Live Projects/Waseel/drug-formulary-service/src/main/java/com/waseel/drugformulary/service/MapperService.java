package com.waseel.drugformulary.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.drugformulary.model.DrugFormularyRequestModel;
import com.waseel.drugformulary.model.DrugFormularyResponseModel;
import java.util.Collections;

@Service
public class MapperService {

	public DrugFormularyResponseModel mapDrugFormularyResponseModel(ContentCachingResponseWrapper response) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(response.getContentAsByteArray()), DrugFormularyResponseModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<DrugFormularyResponseModel> mapDrugFormularyResponseModelList(ContentCachingResponseWrapper response) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(response.getContentAsByteArray()),
					new TypeReference<List<DrugFormularyResponseModel>>() {
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}

	public DrugFormularyRequestModel mapDrugFormularyRequestModel(ContentCachingRequestWrapper requestWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(requestWrapper.getContentAsByteArray()),
					DrugFormularyRequestModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
