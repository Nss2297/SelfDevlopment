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
import com.waseel.dssadminservice.model.customization.pcdrugtogender.DrugToGenderCustomizationRequestModel;
import com.waseel.dssadminservice.model.customization.pcdrugtogender.DrugToGenderResponseModel;
import com.waseel.dssadminservice.model.customization.pcdrugtogender.PcDrugToGenderRequestModel;
import com.waseel.dssadminservice.model.excelupload.BulkUploadResponseModel;
import com.waseel.dssadminservice.service.customization.PCDrugToGenderService;
import com.waseel.dssadminservice.validator.customannotation.IsNumber;

@CrossOrigin("*")
@RestController
@RequestMapping("/dss-customizations/drugs/gender")
@Validated
public class PCDrugToGenderController {

	private final Logger logger = LoggerFactory.getLogger(PCDrugToGenderController.class);

	@Autowired
	PCDrugToGenderService pcDrugToGenderService;

	@GetMapping
	@PreAuthorize("@securityService.hasAccessForDrugToGenderCustomization(authentication)")
	public ResponseEntity<Page<DrugToGenderResponseModel>> getPCDrugToDiagnosisConfigurationDetails(
			DrugToGenderCustomizationRequestModel searchCriteria) {
		return ResponseEntity.ok(pcDrugToGenderService.getPCDrugToGenderList(searchCriteria));
	}

	@PostMapping
	@PreAuthorize("@securityService.hasAccessForDrugToGenderCustomization(authentication)")
	public ResponseEntity<Map<String, String>> addPcDrugToGenderConfiguration(
			@Valid @RequestBody PcDrugToGenderRequestModel pcDrugToGenderRequestModel) throws AdminException {
		return ResponseEntity.ok(pcDrugToGenderService.addPcDrugToGenderConfiguration(pcDrugToGenderRequestModel));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("@securityService.hasAccessForDrugToGenderCustomization(authentication)")
	public ResponseEntity<Object> deletePCDrugToGenderCustomization(@PathVariable("id") Long id) {
		pcDrugToGenderService.deletePCDrugToGender(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/{id}")
	@PreAuthorize("@securityService.hasAccessForDrugToGenderCustomization(authentication)")
	public ResponseEntity<DrugToGenderResponseModel> getPcDrugToGenderDetails(
			@IsNumber(message = "Id {notANumberValidation}") @PathVariable("id") String id) throws AdminException {
		return ResponseEntity.ok(pcDrugToGenderService.getPcDrugToGenderDetails(Long.parseLong(id.trim())));
	}

	@PutMapping("/{id}")
	@PreAuthorize("@securityService.hasAccessForDrugToGenderCustomization(authentication)")
	public ResponseEntity<Object> editDrugToGenderCustomization(
			@PathVariable("id") @IsNumber(message = "id {notANumberValidation}") String id,
			@Valid @RequestBody PcDrugToGenderRequestModel drugToGenderRequestModel)
			throws NumberFormatException, AdminException {
		logger.info("Edit Gender customization request for Payer: [{}], Service Code: [{}], and Id: [{}].",
				drugToGenderRequestModel.getPayerId(), drugToGenderRequestModel.getServiceCode(), id);
		pcDrugToGenderService.updateDrugToGenderCustomization(Long.valueOf(id), drugToGenderRequestModel);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("@securityService.hasAccessForDrugToGenderCustomization(authentication)")
	public ResponseEntity<BulkUploadResponseModel> uploadDrugToGenderFile(@RequestPart MultipartFile file,
			@RequestParam(name = "isOverride", defaultValue = "false") boolean isOverride)
			throws AdminException, IOException {
		logger.info("Request to upload Drug-Gender customizations file: [{}].", file.getOriginalFilename());
		return ResponseEntity.ok(pcDrugToGenderService.uploadDrugToGenderCustomizationsFile(file, isOverride));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ ConstraintViolationException.class, MethodArgumentNotValidException.class })
	ResponseEntity<Object> handleBeanValidationExceptions(Exception exception) {
		logger.error("Bad Request 400 : Has Been Returned From DSS-Admin-Service (PC Drug to Gender) exception: ",
				exception);
		return ResponseEntity.badRequest().body(pcDrugToGenderService.populateInvalidResponseForConstraints(exception));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AdminException.class)
	ResponseEntity<Object> handleValidationExceptions(AdminException adminException) {
		logger.error("Bad Request 400 : Has Been Returned From DSS-Admin-Service (PC Drug to Gender) exception: ",
				adminException);
		return ResponseEntity.badRequest().body(pcDrugToGenderService.populateInvalidResponse(adminException));
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleValidationExceptions(Exception exception) {
		logger.error(
				"Internal Server Error 500 : Has Been Returned From DSS-Admin-Service (PC Drug to Gender) exception: ",
				exception);
		return ResponseEntity.internalServerError().body(pcDrugToGenderService.populateFailedResponse(exception));
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleUnauthorizedException(AccessDeniedException ex) {
		logger.error(
				"AccessDenied Exception 401: Has Been Returned From DSS-Admin-Service (PC Drug to Gender) exception:",
				ex);
		return new ResponseEntity<>(pcDrugToGenderService.populateUnAuthorizedResponse(ex), HttpStatus.UNAUTHORIZED);
	}
}
