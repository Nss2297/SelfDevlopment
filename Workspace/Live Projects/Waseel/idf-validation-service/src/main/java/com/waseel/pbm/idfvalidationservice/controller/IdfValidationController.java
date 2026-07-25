package com.waseel.pbm.idfvalidationservice.controller;

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

import com.waseel.pbm.idfvalidationservice.exceptions.DssException;
import com.waseel.pbm.idfvalidationservice.model.DssRequest;
import com.waseel.pbm.idfvalidationservice.model.DssResponse;
import com.waseel.pbm.idfvalidationservice.service.AuditService;
import com.waseel.pbm.idfvalidationservice.service.DataPopulationService;
import com.waseel.pbm.idfvalidationservice.service.screeningservice.ScreeningService;

@RestController
public class IdfValidationController {

	private final Logger log = LoggerFactory.getLogger(IdfValidationController.class);

	@Autowired
	ScreeningService screeningService;

	@Autowired
	private AuditService auditService;

	@Autowired
	private DataPopulationService dataPopulationService;

	@PostMapping("/validate")
	public ResponseEntity<DssResponse> validate(@RequestBody DssRequest dssRequest) throws DssException {
		log.info("Start Validating Request: {} By IDF-Validation Modules", dssRequest.getRequestId());
		DssResponse response = new DssResponse();
		response = screeningService.controller(dssRequest);
		log.info("{} Http Response for Request Id: {} Has been Returned From IDF-Validation Service",
				response.getHttpStatusCode(), dssRequest.getRequestId());
		return ResponseEntity.ok().body(response);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleValidationExceptions(Exception ex, ContentCachingRequestWrapper request) {
		DssResponse invalidDssResponse = dataPopulationService.populateFailedResponse(request);
		log.info(
				"Internal Server Error 500 : Has Been Returned From IDF-Validation Service , for Request Id:{} Due To : {}",
				invalidDssResponse.getRequestId(), ex.getCause());
		ex.printStackTrace();
		auditService.saveInvalidRequestsToMongoDb(request, invalidDssResponse);
		return ResponseEntity.badRequest().body(invalidDssResponse);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DssException.class)
	ResponseEntity<Object> handleValidationExceptions(DssException ex, ContentCachingRequestWrapper request) {
		log.info("{} Http Response for Request Id: {} Has been Returned From IDF-Validation Service",
				ex.getDssInvalidResponse().getHttpStatusCode(), ex.getDssInvalidResponse().getRequestId());
		auditService.saveInvalidRequestsToMongoDb(request, ex.getDssInvalidResponse());
		return ResponseEntity.badRequest().body(ex.getDssInvalidResponse());
	}
}
