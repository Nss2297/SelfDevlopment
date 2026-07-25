package com.waseel.pbm.fdbvalidationservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.pbm.fdbvalidationservice.exceptions.FdbException;
import com.waseel.pbm.fdbvalidationservice.model.DssRequest;
import com.waseel.pbm.fdbvalidationservice.model.DssResponse;
import com.waseel.pbm.fdbvalidationservice.service.manpulationservice.DMLService;
import com.waseel.pbm.fdbvalidationservice.service.manpulationservice.DataPopulationService;
import com.waseel.pbm.fdbvalidationservice.service.screeningservice.ScreeningService;

@RestController
public class ScreeningController {

	private final Logger log = LoggerFactory.getLogger(ScreeningController.class);

	@Autowired
	ScreeningService screeningService;
	@Autowired
	DMLService loggingToDBService;
	@Autowired
	DataPopulationService dataPopuationService;

	@PostMapping("/validate")
	@Retryable(value = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 200))
	public ResponseEntity<DssResponse> validate(@RequestBody DssRequest dssRequest) throws Exception {
		log.info("Start Validating Request: {} By FDB-Validation Modules", dssRequest.getRequestId());
		DssResponse response = screeningService.controlScreeningProcess(dssRequest);
		log.info("{} Http Response for Request Id: Has been Returned From FDB-Validation Service",
				response.getHttpStatusCode(), dssRequest.getRequestId());
		return ResponseEntity.ok().body(response);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(FdbException.class)
	ResponseEntity<Object> handleValidationExceptions(FdbException ex, ContentCachingRequestWrapper request) {
		log.info(ex.getDssInvalidResponse().getHttpStatusCode() + " Http Response for Request Id: "
				+ ex.getDssInvalidResponse().getRequestId() + "Has been Returned From FDB-Validation Service");
		loggingToDBService.saveInvalidRequestsToMongoDb(request, ex.getDssInvalidResponse());
		return ResponseEntity.badRequest().body(ex.getDssInvalidResponse());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleValidationExceptions(Exception ex, ContentCachingRequestWrapper request) {
		DssResponse invalidDssReponse = dataPopuationService.populateFailedResponse(request);
		log.info("Internal Server Error 500 : Has Been Returned From FDB-Validation Service , for Request Id: {} "
				+ invalidDssReponse.getRequestId() + "Due To : " + ex.getCause());
		ex.printStackTrace();
		loggingToDBService.saveInvalidRequestsToMongoDb(request, invalidDssReponse);
		return ResponseEntity.badRequest().body(invalidDssReponse);
	}
}
