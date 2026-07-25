package com.waseel.dssadminservice.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.waseel.dssadminservice.exceptions.AdminException;
import com.waseel.dssadminservice.model.AgeRangeResponseModel;
import com.waseel.dssadminservice.model.LovResponseModel;
import com.waseel.dssadminservice.service.LovService;

@RestController
@RequestMapping("/dss-lov")
public class LovController {

	@Autowired
	private LovService lovService;

	private final Logger logger = LoggerFactory.getLogger(LovController.class);

	@GetMapping("{key}")
	public ResponseEntity<LovResponseModel> getLovsByKey(@PathVariable String key) {
		logger.info("Request to fetch LOVs for Key: [{}]", key);
		return ResponseEntity.ok(lovService.getListOfValuesByKey(key));
	}

	@GetMapping("/gender")
	public ResponseEntity<List<String>> getAllServiceStatus() {
		return ResponseEntity.ok(lovService.getGenders());
	}

	@GetMapping("/age")
	public ResponseEntity<Map<String, List<AgeRangeResponseModel>>> getAgeList() {
		return ResponseEntity.ok(lovService.getAgeList());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AdminException.class)
	ResponseEntity<Object> handleValidationExceptions(AdminException adminException) {
		logger.error("Bad Request 400 : Has Been Returned From DSS-Admin-Service (lov) exception: ", adminException);
		return ResponseEntity.badRequest().body(lovService.populateInvalidResponse(adminException));
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleValidationExceptions(Exception exception) {
		logger.error("Internal Server Error 500 : Has Been Returned From DSS-Admin-Service (lov) exception: ",
				exception);
		return ResponseEntity.internalServerError().body(lovService.populateFailedResponse());
	}
}
