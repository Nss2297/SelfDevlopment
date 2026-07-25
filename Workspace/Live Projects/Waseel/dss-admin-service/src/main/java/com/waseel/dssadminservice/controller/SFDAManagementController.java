package com.waseel.dssadminservice.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

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
import com.waseel.dssadminservice.model.sfdamanagement.DrugResponseModel;
import com.waseel.dssadminservice.model.sfdamanagement.SFDADrugListResponseModel;
import com.waseel.dssadminservice.model.sfdamanagement.SFDADrugRequestModel;
import com.waseel.dssadminservice.model.sfdamanagement.SFDADrugResponseModel;
import com.waseel.dssadminservice.model.sfdamanagement.SFDAManagementResponseModel;
import com.waseel.dssadminservice.model.sfdamanagement.SFDAMetaDataResponseModel;
import com.waseel.dssadminservice.model.sfdamanagement.SFDAMetaDataSearchModel;
import com.waseel.dssadminservice.model.sfdamanagement.SfdaDrugSearchModel;
import com.waseel.dssadminservice.service.SFDAManagementService;
import com.waseel.dssadminservice.validator.customannotation.IsNumber;
import com.waseel.dssadminservice.validator.customannotation.IsValidDateFormat;

@RestController
@RequestMapping("/sfda-management")
@Validated
public class SFDAManagementController {

	@Autowired
	private SFDAManagementService sfdaManagementService;

	private final Logger logger = LoggerFactory.getLogger(SFDAManagementController.class);

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("@securityService.hasSFDAManagementAccessForValidResponse(authentication)")
	public ResponseEntity<SFDAManagementResponseModel> uploadSFDAFile(
			@RequestParam(name = "effectiveDate") @NotEmpty(message = "Effective Date {emptyDataValidation}") @IsValidDateFormat(message = "Effective Date {dateFormatValidation}") String effectiveDate,
			@RequestPart MultipartFile sfdaFile) throws AdminException, IOException, ParseException {
		logger.info("Request to upload SFDA file for Effective Date: [{}]", effectiveDate);
		return ResponseEntity.ok(sfdaManagementService.uploadDrugsFromFile(effectiveDate, sfdaFile));
	}

	@GetMapping
	@PreAuthorize("@securityService.hasSFDAManagementAccessForValidResponse(authentication)")
	public ResponseEntity<Page<SFDAMetaDataResponseModel>> getAllSFDAList(
			SFDAMetaDataSearchModel sfdaMetaDataSearchModel) {
		return ResponseEntity.ok(sfdaManagementService.getAllSFDAList(sfdaMetaDataSearchModel));
	}

	@GetMapping("{drugListId}/drugs/{waseelDrugId}")
	@PreAuthorize("@securityService.hasSFDAManagementAccessForValidResponse(authentication)")
	public ResponseEntity<SFDADrugResponseModel> getSpecificDrugDetails(
			@IsNumber(message = "DrugListId {notANumberValidation}") @PathVariable(name = "drugListId") String drugListId,
			@IsNumber(message = "WaseelDrugId {notANumberValidation}") @PathVariable(name = "waseelDrugId") String waseelDrugId)
			throws NumberFormatException, AdminException {
		return ResponseEntity.ok(sfdaManagementService.getSpecificDrugDetails(Long.parseLong(waseelDrugId.trim()),
				Long.parseLong(drugListId.trim())));
	}

	@PutMapping("{drugListId}/drugs/{waseelDrugId}")
	@PreAuthorize("@securityService.hasSFDAManagementAccessForValidResponse(authentication)")
	public ResponseEntity<Object> updateDrugDetails(
			@IsNumber(message = "DrugListId {notANumberValidation}") @PathVariable(name = "drugListId") String drugListId,
			@IsNumber(message = "WaseelDrugId {notANumberValidation}") @PathVariable(name = "waseelDrugId") String waseelDrugId,
			@Valid @RequestBody SFDADrugRequestModel sfdaDrugRequestModel)
			throws NumberFormatException, AdminException {
		sfdaManagementService.updateDrugDetails(Long.parseLong(waseelDrugId.trim()), Long.parseLong(drugListId.trim()),
				sfdaDrugRequestModel);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("{drugListId}")
	@PreAuthorize("@securityService.hasSFDAManagementAccessForValidResponse(authentication)")
	public ResponseEntity<SFDADrugListResponseModel> getDrugListDetails(
			@IsNumber(message = "DrugListId {notANumberValidation}") @PathVariable(name = "drugListId") String drugListId,
			SfdaDrugSearchModel sfdaDrugSearchModel) throws NumberFormatException, AdminException {
		return ResponseEntity
				.ok(sfdaManagementService.getDrugListDetails(Long.parseLong(drugListId.trim()), sfdaDrugSearchModel));
	}

	@PostMapping("{drugListId}/drugs")
	@PreAuthorize("@securityService.hasSFDAManagementAccessForValidResponse(authentication)")
	public ResponseEntity<DrugResponseModel> addDrug(
			@IsNumber(message = "DrugListId {notANumberValidation}") @PathVariable(name = "drugListId") String drugListId,
			@RequestBody SFDADrugRequestModel sfdaRequestModel) throws NumberFormatException, AdminException {
		return ResponseEntity.ok(sfdaManagementService.addDrug(Long.parseLong(drugListId.trim()), sfdaRequestModel));
	}

	@DeleteMapping("{drugListId}/drugs/{waseelDrugId}")
	@PreAuthorize("@securityService.hasSFDAManagementAccessForValidResponse(authentication)")
	public ResponseEntity<Object> deleteDrugDetails(
			@IsNumber(message = "DrugListId {notANumberValidation}") @PathVariable(name = "drugListId") String drugListId,
			@IsNumber(message = "WaseelDrugId {notANumberValidation}") @PathVariable(name = "waseelDrugId") String waseelDrugId)
			throws NumberFormatException, AdminException {
		sfdaManagementService.deleteDrugDetails(Long.parseLong(waseelDrugId.trim()), Long.parseLong(drugListId.trim()));
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("{drugListId}")
	@PreAuthorize("@securityService.hasSFDAManagementAccessForValidResponse(authentication)")
	public ResponseEntity<Object> deleteSFDAListDetails(
			@IsNumber(message = "DrugListId {notANumberValidation}") @PathVariable(name = "drugListId") String drugListId)
			throws NumberFormatException, AdminException {
		sfdaManagementService.deleteSFDAListDetails(Long.parseLong(drugListId.trim()));
		return ResponseEntity.noContent().build();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ ConstraintViolationException.class, MethodArgumentNotValidException.class })
	ResponseEntity<Object> handleBeanValidationExceptions(Exception exception) {
		logger.error("Bad Request 400 : Has Been Returned From DSS-Admin-Service (sfda-management) exception: ",
				exception);
		return ResponseEntity.badRequest().body(sfdaManagementService.populateInvalidResponseForConstraints(exception));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AdminException.class)
	ResponseEntity<Object> handleValidationExceptions(AdminException adminException) {
		logger.error("Bad Request 400 : Has Been Returned From DSS-Admin-Service (sfda-management) exception: ",
				adminException);
		return ResponseEntity.badRequest().body(sfdaManagementService.populateInvalidResponse(adminException));
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleValidationExceptions(Exception exception) {
		logger.error(
				"Internal Server Error 500 : Has Been Returned From DSS-Admin-Service (sfda-management) exception: ",
				exception);
		return ResponseEntity.internalServerError().body(sfdaManagementService.populateFailedResponse(exception));
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleUnauthorizedException(AccessDeniedException ex) {
		logger.error(
				"AccessDenied Exception 401: Has Been Returned From DSS-Admin-Service (sfda-management) exception:",
				ex);
		return new ResponseEntity<>(sfdaManagementService.populateUnAuthorizedResponse(ex), HttpStatus.UNAUTHORIZED);
	}
}
