package com.waseel.pbm.pbmadminservice.controller;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.PCDrugToDiagnosisRequest;
import com.waseel.pbm.pbmadminservice.model.customization.DrugToDiagnosisModel;
import com.waseel.pbm.pbmadminservice.model.customization.PCDrugToDiagnosisRequestModel;
import com.waseel.pbm.pbmadminservice.service.PCDrugToDiagnosisService;

@CrossOrigin("*")
@RestController
@RequestMapping("/customizations/drugs/diagnosis")
public class PCDrugToDiagnosisController {

	private final Logger log = LoggerFactory.getLogger(PCDrugToDiagnosisController.class);

	@Autowired
	private PCDrugToDiagnosisService pcDrugToDiagnosisService;

	@GetMapping
	@PreAuthorize("@securityService.hasAccessForDrugToDiagnosisCustomization(authentication)")
	public ResponseEntity<Page<DrugToDiagnosisModel>> getPCDrugToDiagnosisConfigurationDetails(
			@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
			@RequestParam(name = "serviceCode", required = false) String serviceCode,
			@RequestParam(name = "icdCode", required = false) String icdCode,
			@RequestParam(name = "payerId", required = false) String payerId,
			@RequestParam(name = "moduleName", required = false) String moduleName,
			@RequestParam(name = "categoryOfApproval", required = false) String categoryOfApproval,
			@RequestParam(name = "rejectionCategory", required = false) String rejectionCategory,
			@RequestParam(name = "serviceStatus", required = false) String serviceStatus) {
		PCDrugToDiagnosisRequest request = new PCDrugToDiagnosisRequest(serviceCode, icdCode, payerId, moduleName,
				categoryOfApproval, rejectionCategory, serviceStatus);
		return ResponseEntity
				.ok(pcDrugToDiagnosisService.getPCDrugToDiagnosisConfigurationDetails(pageNumber, recordSize, request));
	}

	@PostMapping
	@PreAuthorize("@securityService.hasAccessForDrugToDiagnosisCustomization(authentication)")
	public ResponseEntity<Map<String, String>> addPCDrugToDiagnosisConfiguration(
			@RequestBody PCDrugToDiagnosisRequestModel pcDrugToDiagnosis) {
		try {
			return ResponseEntity.ok(pcDrugToDiagnosisService.addPCDrugToDiagnosisConfiguration(pcDrugToDiagnosis));
		} catch (DuplicateKeyException ex) {
			return ResponseEntity.badRequest()
					.body(Map.of("code", "CUSTOMIZATION_ALREADY_EXIST", "message", ex.getMessage()));
		}
	}

	@GetMapping("/{id}")
	@PreAuthorize("@securityService.hasAccessForDrugToDiagnosisCustomization(authentication)")
	public ResponseEntity<DrugToDiagnosisModel> getPCDrugToDiagnosisConfiguration(@PathVariable("id") Long id) {
		return ResponseEntity.ok(pcDrugToDiagnosisService.getPCDrugToDiagnosisConfiguration(id));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("@securityService.hasAccessForDrugToDiagnosisCustomization(authentication)")
	public ResponseEntity<Object> deletePCDrugToDiagnosisConfiguration(@PathVariable("id") Long id) {
		pcDrugToDiagnosisService.deletePCDrugToDiagnosisConfiguration(id);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PutMapping("/{id}")
	@PreAuthorize("@securityService.hasAccessForDrugToDiagnosisCustomization(authentication)")
	public ResponseEntity<Object> updatePCDrugToDiagnosisConfiguration(
			@RequestBody PCDrugToDiagnosisRequestModel pcDrugToDiagnosis, @PathVariable("id") Long id)
			throws AdminException {
		pcDrugToDiagnosisService.updatePCDrugToDiagnosisConfiguration(pcDrugToDiagnosis, id);
		return ResponseEntity.status(HttpStatus.OK).body(Map.of("id", id));
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("@securityService.hasAccessForCustomizationUpload(authentication)")
	public ResponseEntity<Map<Object, Object>> addPcDrugToDiagnosisDetailsFromFile(@RequestPart MultipartFile file,
			@RequestParam(name = "isOverride") boolean isOverride) throws AdminException, IOException {
		return ResponseEntity.ok(pcDrugToDiagnosisService.addPcDrugToDiagnosisDetailsFromFile(file, isOverride));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AdminException.class)
	ResponseEntity<Object> handleValidationExceptions(AdminException ex) {
		DrugToDiagnosisModel invalidResponse = pcDrugToDiagnosisService.populateInvalidResponse(ex);
		log.info("Bad Request 400 : Has Been Returned From PBM-Admin-Service (PCDrugToDiagnosis) Due To:{} ",
				ex.getMessage());
		return ResponseEntity.badRequest().body(invalidResponse);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleValidationExceptions(Exception ex) {
		DrugToDiagnosisModel invalidResponse = pcDrugToDiagnosisService.populateFailedResponse();
		log.info("Internal Server Error 500 : Has Been Returned From PBM-Admin-Service (PCDrugToDiagnosis) Due To : ",
				ex.getCause());
		ex.printStackTrace();
		return ResponseEntity.internalServerError().body(invalidResponse);
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleUnauthorizedException(AccessDeniedException ex) {
		DrugToDiagnosisModel invalidResponse = pcDrugToDiagnosisService.populateUnAuthorizedResponse(ex);
		log.error("AccessDenied Exception 401: Has Been Returned From PBM-Admin-Service (PCDrugToDiagnosis) Due To :",
				ex);
		return new ResponseEntity<>(invalidResponse, HttpStatus.UNAUTHORIZED);
	}

}
