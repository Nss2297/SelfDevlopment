package com.waseel.pbm.dssservice.service.managementservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waseel.pbm.dssservice.enums.RequestType;
import com.waseel.pbm.dssservice.model.CancelOverrideResponse;
import com.waseel.pbm.dssservice.model.CancellationOverrideRequest;
import com.waseel.pbm.dssservice.model.DssRequest;
import com.waseel.pbm.dssservice.model.Result;
import com.waseel.pbm.dssservice.persist.mdss.RequestInfo;
import com.waseel.pbm.dssservice.repository.mdss.RequestInfoRepository;

@Service
public class DssDataManipulationService {

	@Autowired
	private DMLService dmlService;

	@Autowired
	private RequestInfoRepository requestInfoRepo;

	@Autowired
	private CancellationDMLService cancellationDMLService;

	@Autowired
	private OverrideDMLService overrideDMLService;

	// New Api
	public void saveDssRequest(DssRequest dssRequest, RequestType requestType, List<Result> drugResults) {
		dmlService.saveDssRequest(dssRequest, requestType, drugResults);
	}

	// FollowUp Api
	public RequestInfo getRequestInfo(String requestId) {
		return requestInfoRepo.findByRequestId(requestId);
	}

	public void updateDssRequest(DssRequest dssRequest, RequestType requestType, List<Result> drugResults) {
		dmlService.updateFollowUpRequest(dssRequest, drugResults, requestType);
	}

	// CancellationApi
	@Transactional(rollbackFor = RuntimeException.class)
	public CancelOverrideResponse validateCancellationRequest(CancellationOverrideRequest cancelRequest) {
		String responseMsg = "";
		CancelOverrideResponse response = new CancelOverrideResponse();
		RequestInfo requestInfo = requestInfoRepo.findExistsRequestByRequestId(cancelRequest.getRequestId());
		if (cancelRequest.getDrugList() != null && !cancelRequest.getDrugList().isEmpty()) {
			responseMsg = cancellationDMLService.populateNotEmptyDrugList(cancelRequest, requestInfo);
		} else {
			responseMsg = cancellationDMLService.populateEmptyDrugList(cancelRequest, requestInfo);
		}
		response.setCode(HttpStatus.OK.value());
		response.setMessage(responseMsg);
		return response;
	}

	// Override Api
	@Transactional(rollbackFor = RuntimeException.class)
	public CancelOverrideResponse validateOverrideRequest(CancellationOverrideRequest overrideRequest) {
		String responseMsg = "";
		CancelOverrideResponse response = new CancelOverrideResponse();
		RequestInfo requestInfo = requestInfoRepo.findExistsRequestByRequestId(overrideRequest.getRequestId());
		if (overrideRequest.getDrugList() != null && !overrideRequest.getDrugList().isEmpty()) {
			responseMsg = overrideDMLService.populateNotEmptyDrugList(overrideRequest, requestInfo);
		} else {
			responseMsg = overrideDMLService.populateEmptyDrugList(overrideRequest, requestInfo);
		}
		response.setCode(HttpStatus.OK.value());
		response.setMessage(responseMsg);
		return response;
	}

}
