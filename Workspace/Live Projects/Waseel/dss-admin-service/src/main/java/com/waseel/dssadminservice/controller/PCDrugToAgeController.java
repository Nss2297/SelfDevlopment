package com.waseel.dssadminservice.controller;

import java.io.IOException;
import java.util.Map;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.waseel.dssadminservice.exceptions.AdminException;
import com.waseel.dssadminservice.model.customization.pcdrugtoage.DrugToAgeResponseModel;
import com.waseel.dssadminservice.model.customization.pcdrugtoage.DrugToAgeSearchModel;
import com.waseel.dssadminservice.model.customization.pcdrugtoage.PcDrugToAgeRequestModel;
import com.waseel.dssadminservice.model.excelupload.BulkUploadResponseModel;
import com.waseel.dssadminservice.service.customization.PCDrugToAgeService;
import com.waseel.dssadminservice.validator.customannotation.IsNumber;

@CrossOrigin("*")
@RestController
@RequestMapping("/dss-customizations/drugs/age")
@Validated
public class PCDrugToAgeController {

	private final Logger logger = LoggerFactory.getLogger(PCDrugToAgeController.class);

	@Autowired
	private PCDrugToAgeService drugToAgeService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("@securityService.hasAccessForUploadAgeCustomization(authentication)")
	public ResponseEntity<BulkUploadResponseModel> uploadDrugToAgeFile(@RequestPart MultipartFile file,
			@RequestParam(name = "isOverride", defaultValue = "false") boolean isOverride)
			throws AdminException, IOException {
		logger.info("Request to upload Drug-Age customizations file: [{}].", file.getOriginalFilename());
		return ResponseEntity.ok(drugToAgeService.uploadDrugToAgeCustomizationsFile(file, isOverride));
	}

	@GetMapping
	@PreAuthorize("@securityService.hasAccessForDrugToAgeCustomization(authentication)")
	public ResponseEntity<Page<DrugToAgeResponseModel>> getPCDrugToAgesConfigurationList(
			DrugToAgeSearchModel searchCriteria) {
		return ResponseEntity.ok(drugToAgeService.getPCDrugToAgeList(searchCriteria));
	}

	@PostMapping
	@PreAuthorize("@securityService.hasAccessForDrugToAgeCustomization(authentication)")
	public ResponseEntity<Map<String, String>> addPCDrugToAgesConfiguration(
			@Valid @RequestBody PcDrugToAgeRequestModel requestModel) throws NumberFormatException, AdminException {
		logger.info("Post Drug to age customization request for Payer: [{}], Service Code: [{}].",
				requestModel.getPayerId(), requestModel.getServiceCode());
		return ResponseEntity.ok(drugToAgeService.addPCDrugToAgesConfiguration(requestModel));
	}

	@PutMapping("/{id}")
	@PreAuthorize("@securityService.hasAccessForDrugToAgeCustomization(authentication)")
	public ResponseEntity<Page<DrugToAgeResponseModel>> updatePCDrugToAgesConfiguration(
			@PathVariable("id") @IsNumber(message = "id {notANumberValidation}") String id,
			@Valid @RequestBody PcDrugToAgeRequestModel requestModel) throws NumberFormatException, AdminException {
		logger.info("Edit Drug to age customization request for Payer: [{}], Service Code: [{}], and Id: [{}].",
				requestModel.getPayerId(), requestModel.getServiceCode(), id);
		drugToAgeService.updatePCDrugToAgesConfiguration(requestModel, Long.valueOf(id));
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	@PreAuthorize("@securityService.hasAccessForDrugToAgeCustomization(authentication)")
	public ResponseEntity<DrugToAgeResponseModel> getPcDrugToAgeDetails(
			@IsNumber(message = "Id {notANumberValidation}") @PathVariable("id") String id) throws AdminException {
		return ResponseEntity.ok(drugToAgeService.getDrugToAgeDetails(Long.parseLong(id.trim())));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("@securityService.hasAccessForDrugToAgeCustomization(authentication)")
	public ResponseEntity<Object> deletePCDrugToAgeCustomization(@PathVariable("id") Long id) {
		drugToAgeService.deletePCDrugToAge(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ ConstraintViolationException.class, MethodArgumentNotValidException.class })
	ResponseEntity<Object> handleBeanValidationExceptions(Exception exception) {
		logger.error("Bad Request 400 : Has Been Returned From DSS-Admin-Service (PC Drug to Age) exception: ",
				exception);
		return ResponseEntity.badRequest().body(drugToAgeService.populateInvalidResponseForConstraints(exception));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AdminException.class)
	ResponseEntity<Object> handleValidationExceptions(AdminException adminException) {
		logger.error("Bad Request 400 : Has Been Returned From DSS-Admin-Service (PC Drug to Age) exception: ",
				adminException);
		return ResponseEntity.badRequest().body(drugToAgeService.populateInvalidResponse(adminException));
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleValidationExceptions(Exception exception) {
		logger.error(
				"Internal Server Error 500 : Has Been Returned From DSS-Admin-Service (PC Drug to Age) exception: ",
				exception);
		return ResponseEntity.internalServerError().body(drugToAgeService.populateFailedResponse(exception));
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleUnauthorizedException(AccessDeniedException ex) {
		logger.error("AccessDenied Exception 401: Has Been Returned From DSS-Admin-Service (PC Drug to Age) exception:",
				ex);
		return new ResponseEntity<>(drugToAgeService.populateUnAuthorizedResponse(ex), HttpStatus.UNAUTHORIZED);
	}
}
