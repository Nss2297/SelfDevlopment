package com.waseel.pbm.rtsservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.pbm.rtsservice.exceptions.RTSException;
import com.waseel.pbm.rtsservice.model.RTSRequest;
import com.waseel.pbm.rtsservice.model.RTSResponse;
import com.waseel.pbm.rtsservice.service.DMLService;
import com.waseel.pbm.rtsservice.service.DataPopulationService;
import com.waseel.pbm.rtsservice.service.RTSRequestService;

@RestController
public class RTSRequestController {

	private final Logger log = LoggerFactory.getLogger(RTSRequestController.class);

	@Autowired
	RTSRequestService rtsRequestService;

	@Autowired
	private DMLService loggingToDBService;

	@Autowired
	private DataPopulationService dataPopulationService;

	@PostMapping("/validate")
	public ResponseEntity<RTSResponse> validateRTSRequest(@RequestBody RTSRequest rtsRequest) {
		log.info("Start Validating Request: " + rtsRequest.getRequestId() + " By RTS Modules");
		RTSResponse response = rtsRequestService.validateRTSRequest(rtsRequest);
		log.info(response.getHttpStatusCode() +" Http Response for Request Id: "+ rtsRequest.getRequestId()+"Has been Returned From RTS Service");
		return ResponseEntity.ok().body(response);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(RTSException.class)
	ResponseEntity<Object> handleValidationExceptions(RTSException ex, ContentCachingRequestWrapper request) {
		log.info(ex.getRtsInvalidResponse().getHttpStatusCode() +" Http Response for Request Id: "+ ex.getRtsInvalidResponse().getRequestId()+"Has been Returned From RTS Service");
		loggingToDBService.saveInvalidRequestsToMongoDb(request, ex.getRtsInvalidResponse());
		return ResponseEntity.badRequest().body(ex.getRtsInvalidResponse());
	}


	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleValidationExceptions(Exception ex, ContentCachingRequestWrapper request) {
		RTSResponse invalidDssReponse = dataPopulationService.populateFailedResponse(request);
		log.info("Internal Server Error 500 : Has Been Returned From RTS Service , for Request Id:" + invalidDssReponse.getRequestId() + "Due To : " + ex.getCause());
		ex.printStackTrace();
		loggingToDBService.saveInvalidRequestsToMongoDb(request, invalidDssReponse);
		return ResponseEntity.badRequest().body(invalidDssReponse);
	}
}
