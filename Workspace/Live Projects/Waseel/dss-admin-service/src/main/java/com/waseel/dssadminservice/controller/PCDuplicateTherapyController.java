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
import com.waseel.dssadminservice.model.customization.pcduplicatetherapy.DuplicateTherapyResponseModel;
import com.waseel.dssadminservice.model.customization.pcduplicatetherapy.DuplicateTherapySearchModel;
import com.waseel.dssadminservice.model.customization.pcduplicatetherapy.PcDuplicateTherapyRequestModel;
import com.waseel.dssadminservice.model.excelupload.BulkUploadResponseModel;
import com.waseel.dssadminservice.service.customization.PCDuplicateTherapyService;
import com.waseel.dssadminservice.validator.customannotation.IsNumber;

@CrossOrigin("*")
@RestController
@RequestMapping("/dss-customizations/drugs/duplicateTherapy")
@Validated
public class PCDuplicateTherapyController {

    private final Logger logger = LoggerFactory.getLogger(PCDuplicateTherapyController.class);

    @Autowired
    private PCDuplicateTherapyService pcDuplicateTherapyService;

    @GetMapping
    @PreAuthorize("@securityService.hasAccessForDuplicateTherapyCustomization(authentication)")
    public ResponseEntity<Page<DuplicateTherapyResponseModel>> getPCDuplicateTherapyConfigurationList(
            DuplicateTherapySearchModel searchCriteria) {
        return ResponseEntity.ok(pcDuplicateTherapyService.getPCDuplicateTherapyList(searchCriteria));
    }

    @PostMapping
    @PreAuthorize("@securityService.hasAccessForDuplicateTherapyCustomization(authentication)")
    public ResponseEntity<Map<String, Long>> addPCDuplicateTherapyConfiguration(
            @Valid @RequestBody PcDuplicateTherapyRequestModel requestModel)
            throws NumberFormatException, AdminException {
        logger.info("Post Duplicate Therapy customization request for Payer: [{}], Service Code: [{}].",
                requestModel.getPayerId(), requestModel.getServiceCode());
        return ResponseEntity.ok(pcDuplicateTherapyService.addPCDuplicateTherapyConfiguration(requestModel));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@securityService.hasAccessForDuplicateTherapyCustomization(authentication)")
    public ResponseEntity<DuplicateTherapyResponseModel> getPcDuplicateTherapyDetails(
            @IsNumber(message = "Id {notANumberValidation}") @PathVariable("id") String id) throws AdminException {
        return ResponseEntity.ok(pcDuplicateTherapyService.getDuplicateTherapyDetails(Long.parseLong(id.trim())));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("@securityService.hasAccessForUploadDuplicateTherapyCustomization(authentication)")
	public ResponseEntity<BulkUploadResponseModel> uploadDuplicateTherapyFile(@RequestPart MultipartFile file,
			@RequestParam(name = "isOverride", defaultValue = "false") boolean isOverride)
			throws AdminException, IOException {
		logger.info("Request to upload Duplicate Therapy customizations file: [{}].", file.getOriginalFilename());
		return ResponseEntity.ok(pcDuplicateTherapyService.uploadDuplicateTherapyCustomizationsFile(file, isOverride));
	}
    
    @PutMapping("/{id}")
    @PreAuthorize("@securityService.hasAccessForDuplicateTherapyCustomization(authentication)")
	public ResponseEntity<Page<DuplicateTherapyResponseModel>> updatePCDuplicateTherapyConfiguration(
			@PathVariable("id") @IsNumber(message = "id {notANumberValidation}") String id,
			@Valid @RequestBody PcDuplicateTherapyRequestModel requestModel) throws NumberFormatException, AdminException {
		logger.info("Edit Duplicate Therapy customization request for Payer: [{}], Service Code: [{}], and Id: [{}].",
				requestModel.getPayerId(), requestModel.getServiceCode(), id);
		pcDuplicateTherapyService.updatePCDuplicateTherapyConfiguration(requestModel, Long.valueOf(id));
		return ResponseEntity.noContent().build();
	}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ ConstraintViolationException.class, MethodArgumentNotValidException.class })
    ResponseEntity<Object> handleBeanValidationExceptions(Exception exception) {
        logger.error("Bad Request 400 : Has Been Returned From DSS-Admin-Service (PC Duplicate Therapy) exception: ",
                exception);
        return ResponseEntity.badRequest()
                .body(pcDuplicateTherapyService.populateInvalidResponseForConstraints(exception));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AdminException.class)
    ResponseEntity<Object> handleValidationExceptions(AdminException adminException) {
        logger.error("Bad Request 400 : Has Been Returned From DSS-Admin-Service (PC Duplicate Therapy) exception: ",
                adminException);
        return ResponseEntity.badRequest().body(pcDuplicateTherapyService.populateInvalidResponse(adminException));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleValidationExceptions(Exception exception) {
        logger.error(
                "Internal Server Error 500 : Has Been Returned From DSS-Admin-Service (PC Duplicate Therapy) exception: ",
                exception);
        return ResponseEntity.internalServerError().body(pcDuplicateTherapyService.populateFailedResponse(exception));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(AccessDeniedException ex) {
        logger.error(
                "AccessDenied Exception 401: Has Been Returned From DSS-Admin-Service (PC Duplicate Therapy) exception:",
                ex);
        return new ResponseEntity<>(pcDuplicateTherapyService.populateUnAuthorizedResponse(ex), HttpStatus.UNAUTHORIZED);
    }
    
    @DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteDuplicateTherapyConfiguration(@PathVariable("id") Long id) {
    	pcDuplicateTherapyService.deletePCDrugToDrugCustomization(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}