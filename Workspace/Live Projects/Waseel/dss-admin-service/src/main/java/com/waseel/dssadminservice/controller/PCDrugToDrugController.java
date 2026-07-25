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
import com.waseel.dssadminservice.model.customization.pcdrugtodrug.DrugToDrugResponseModel;
import com.waseel.dssadminservice.model.customization.pcdrugtodrug.DrugToDrugSearchModel;
import com.waseel.dssadminservice.model.customization.pcdrugtodrug.PcDrugToDrugRequestModel;
import com.waseel.dssadminservice.model.excelupload.BulkUploadResponseModel;
import com.waseel.dssadminservice.service.customization.PCDrugToDrugService;
import com.waseel.dssadminservice.validator.customannotation.IsNumber;

@CrossOrigin("*")
@RestController
@RequestMapping("/dss-customizations/drugs/drug")
@Validated
public class PCDrugToDrugController {

	private final Logger logger = LoggerFactory.getLogger(PCDrugToDrugController.class);

	@Autowired
	private PCDrugToDrugService drugToDrugService;

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("@securityService.hasAccessForUploadDrugCustomization(authentication)")
	public ResponseEntity<BulkUploadResponseModel> uploadDrugToDrugFile(@RequestPart MultipartFile file,
			@RequestParam(name = "isOverride", defaultValue = "false") boolean isOverride)
			throws AdminException, IOException {
		logger.info("Request to upload Drug-Drug customizations file: [{}].", file.getOriginalFilename());
		return ResponseEntity.ok(drugToDrugService.uploadDrugToDrugCustomizationsFile(file, isOverride));
	}

	@PostMapping
	@PreAuthorize("@securityService.hasAccessForDrugToDrugCustomization(authentication)")
	public ResponseEntity<Map<String, Long>> addPCDrugToDrugConfiguration(
			@Valid @RequestBody PcDrugToDrugRequestModel requestModel) throws NumberFormatException, AdminException {
		logger.info("Post Drug to drug customization request for Payer: [{}], Service Code: [{}].",
				requestModel.getPayerId(), requestModel.getServiceCode());
		return ResponseEntity.ok(drugToDrugService.addPCDrugToDrugConfiguration(requestModel));
	}

	@PutMapping("/{id}")
	@PreAuthorize("@securityService.hasAccessForDrugToDrugCustomization(authentication)")
	public ResponseEntity<Page<DrugToDrugResponseModel>> updatePCDrugToDrugConfiguration(
			@PathVariable("id") @IsNumber(message = "id {notANumberValidation}") String id,
			@Valid @RequestBody PcDrugToDrugRequestModel requestModel) throws NumberFormatException, AdminException {
		logger.info("Edit Drug to drug customization request for Payer: [{}], Service Code: [{}], and Id: [{}].",
				requestModel.getPayerId(), requestModel.getServiceCode(), id);
		drugToDrugService.updatePCDrugToDrugConfiguration(requestModel, Long.valueOf(id));
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("@securityService.hasAccessForDrugToDrugCustomization(authentication)")
	public ResponseEntity<Object> deletePCDrugToDrugCustomization(@PathVariable("id") Long id) {
		drugToDrugService.deletePCDrugToDrugCustomization(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping
	@PreAuthorize("@securityService.hasAccessForDrugToDrugCustomization(authentication)")
	public ResponseEntity<Page<DrugToDrugResponseModel>> getPCDrugToDrugConfigurationList(
			DrugToDrugSearchModel searchCriteria) {
		return ResponseEntity.ok(drugToDrugService.getPCDrugToDrugList(searchCriteria));
	}

	@GetMapping("/{id}")
	@PreAuthorize("@securityService.hasAccessForDrugToDrugCustomization(authentication)")
	public ResponseEntity<DrugToDrugResponseModel> getPcDrugToDrugDetails(
			@IsNumber(message = "Id {notANumberValidation}") @PathVariable("id") String id) throws AdminException {
		return ResponseEntity.ok(drugToDrugService.getDrugToDrugDetails(Long.parseLong(id.trim())));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ ConstraintViolationException.class, MethodArgumentNotValidException.class })
	ResponseEntity<Object> handleBeanValidationExceptions(Exception exception) {
		logger.error("Bad Request 400 : Has Been Returned From DSS-Admin-Service (PC Drug to Drug) exception: ",
				exception);
		return ResponseEntity.badRequest().body(drugToDrugService.populateInvalidResponseForConstraints(exception));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AdminException.class)
	ResponseEntity<Object> handleValidationExceptions(AdminException adminException) {
		logger.error("Bad Request 400 : Has Been Returned From DSS-Admin-Service (PC Drug to Drug) exception: ",
				adminException);
		return ResponseEntity.badRequest().body(drugToDrugService.populateInvalidResponse(adminException));
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleValidationExceptions(Exception exception) {
		logger.error(
				"Internal Server Error 500 : Has Been Returned From DSS-Admin-Service (PC Drug to Drug) exception: ",
				exception);
		return ResponseEntity.internalServerError().body(drugToDrugService.populateFailedResponse(exception));
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleUnauthorizedException(AccessDeniedException ex) {
		logger.error("AccessDenied Exception 401: Has Been Returned From DSS-Admin-Service (PC Drug to Drug) exception:",
				ex);
		return new ResponseEntity<>(drugToDrugService.populateUnAuthorizedResponse(ex), HttpStatus.UNAUTHORIZED);
	}
}
