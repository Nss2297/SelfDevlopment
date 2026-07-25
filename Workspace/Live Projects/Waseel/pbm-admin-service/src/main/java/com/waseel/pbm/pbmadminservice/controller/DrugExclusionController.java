package com.waseel.pbm.pbmadminservice.controller;

import java.io.IOException;

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
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionDrugDetailsModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionDrugDetailsRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionMetaDataRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionMetaDataResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.ExclusionDrugListUploadResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.ExclusionTypeSearchModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.ExclusionTypeSearchResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.network.NetworkExclusionModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.network.NetworkExclusionRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.provider.ProviderExclusionRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.provider.ProviderExclusionResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.speciality.SpecialityExclusionModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.speciality.SpecialityExclusionRequestModel;
import com.waseel.pbm.pbmadminservice.service.DrugExclusionService;
import com.waseel.pbm.pbmadminservice.service.ExclusionDrugListUploadService;
import com.waseel.pbm.pbmadminservice.service.NetworkExclusionService;
import com.waseel.pbm.pbmadminservice.service.ProviderExclusionService;
import com.waseel.pbm.pbmadminservice.service.SpecialityExclusionService;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsNumber;

@RestController
@RequestMapping("/drug-exclusion")
@Validated
public class DrugExclusionController {

	private final Logger logger = LoggerFactory.getLogger(DrugExclusionController.class);

	@Autowired
	private DrugExclusionService exclusionService;
	@Autowired
	private NetworkExclusionService networkExclusionService;
	@Autowired
	private ProviderExclusionService providerExclusionService;
	@Autowired
	ExclusionDrugListUploadService exclusionDrugListUploadService;
	@Autowired
	private SpecialityExclusionService specialityExclusionService;

	@PutMapping("/{exclusionId}/name/{exclusion-name}")
	@PreAuthorize("@securityService.hasAccessForExclusionManagement(authentication)")
	public ResponseEntity<?> createExclusion(
			@IsNumber(message = "exclusionId {onlyAllowDigits}") @PathVariable("exclusionId") String exclusionId,
			@PathVariable("exclusion-name") String exclusionName) throws AdminException {
		exclusionService.createDrugExclusion(Long.parseLong(exclusionId.trim()), exclusionName.trim());
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	@PreAuthorize("@securityService.hasAccessForExclusionManagement(authentication)")
	public ResponseEntity<Page<DrugExclusionMetaDataResponseModel>> getDrugExclusionMetadataWithPagination(
			@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
			@RequestParam(name = "exclusionId", required = false) Long id,
			@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "createdDateFrom", required = false) String createdDateFrom,
			@RequestParam(name = "createdDateTo", required = false) String createdDateTo,
			@RequestParam(name = "updatedDateFrom", required = false) String updatedDateFrom,
			@RequestParam(name = "updatedDateTo", required = false) String updatedDateTo) {
		logger.info("Fetch exclusion metadata.");
		return ResponseEntity.ok(exclusionService.getDrugExclusionMetadataWithPagination(pageNumber, recordSize,
				new DrugExclusionMetaDataRequestModel(id, name, createdDateFrom, createdDateTo, updatedDateFrom,
						updatedDateTo)));
	}

	@PostMapping
	@PreAuthorize("@securityService.hasAccessForExclusionManagement(authentication)")
	public ResponseEntity<DrugExclusionResponseModel> addDrugExclusionList(
			@Valid @RequestBody DrugExclusionRequestModel drugExclusionRequestModel) throws AdminException {
		logger.info("Add new drug exclusion list request.");
		return ResponseEntity.ok(exclusionService.addNewDrugExclusionList(drugExclusionRequestModel));
	}

	@PutMapping("/{exclusionId}/high-cost")
	@PreAuthorize("@securityService.hasAccessForExclusionManagement(authentication)")
	public ResponseEntity<?> addHighCostExclusion(
			@IsNumber(message = "exclusionId {onlyAllowDigits}") @PathVariable("exclusionId") String exclusionId)
			throws AdminException {
		logger.info("Add high cost exclusion");
		exclusionService.addHighCostDrugExclusion(exclusionId.trim());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{exclusionId}/networks")
	@PreAuthorize("@securityService.hasAccessForExclusionManagement(authentication)")
	public ResponseEntity<NetworkExclusionModel> addNetworkExclusion(
			@IsNumber(message = "exclusionId {onlyAllowDigits}") @PathVariable("exclusionId") String exclusionId,
			@Valid @RequestBody NetworkExclusionRequestModel networkExclusionRequestModel) throws AdminException {
		logger.info("Add new Network exclusion");
		return ResponseEntity.ok(networkExclusionService.addNetworkExclusion(networkExclusionRequestModel,
				Long.parseLong(exclusionId.trim())));
	}

	@PutMapping("/{exclusionId}/specialities")
	@PreAuthorize("@securityService.hasAccessForExclusionManagement(authentication)")
	public ResponseEntity<SpecialityExclusionModel> addSpecialityExclusion(
			@IsNumber(message = "exclusionId {onlyAllowDigits}") @PathVariable("exclusionId") String exclusionId,
			@Valid @RequestBody SpecialityExclusionRequestModel specialityExclusionRequestModel) throws AdminException {
		logger.info("Add new Speciality exclusion");
		return ResponseEntity.ok(specialityExclusionService.addSpecialityExclusion(specialityExclusionRequestModel,
				Long.parseLong(exclusionId.trim())));
	}

	@DeleteMapping("/{exclusionType}/{exclusionAsscId}")
	@PreAuthorize("@securityService.hasAccessForExclusionManagement(authentication)")
	public ResponseEntity<Object> deleteExclusionType(@PathVariable("exclusionType") String exclusionType,
			@IsNumber(message = "exclusionAsscId {onlyAllowDigits}") @PathVariable("exclusionAsscId") String exclusionAsscId)
			throws AdminException {
		logger.info("Delete drug exclusion association type.");
		exclusionService.deleteExclusionType(exclusionType, exclusionAsscId.trim());
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{exclusionId}")
	@PreAuthorize("@securityService.hasAccessForExclusionManagement(authentication)")
	public ResponseEntity<?> deleteDrugExclusionMetadata(
			@IsNumber(message = "exclusionId {onlyAllowDigits}") @PathVariable("exclusionId") String exclusionId)
			throws AdminException {
		logger.info("Delete drug exclusion metadata");
		exclusionService.deleteDrugExclusionMetadata(Long.parseLong(exclusionId.trim()));
		return ResponseEntity.ok().build();
	}

	@PostMapping("/{exclusionId}/drugs")
	@PreAuthorize("@securityService.hasAccessForExclusionManagement(authentication)")
	public ResponseEntity<?> addDrugExclusionDrugDetails(
			@IsNumber(message = "exclusionId {onlyAllowDigits}") @PathVariable("exclusionId") String exclusionId,
			@Valid @RequestBody DrugExclusionDrugDetailsRequestModel requestModel) throws AdminException {
		exclusionService.addDrugExclusionDrugDetails(requestModel, Long.parseLong(exclusionId.trim()));
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{exclusionId}/drugs")
	@PreAuthorize("@securityService.hasAccessForExclusionManagement(authentication)")
	public ResponseEntity<Page<DrugExclusionDrugDetailsModel>> getAllDrugExclusionDrugsDetails(
			@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
			DrugExclusionDrugDetailsModel drugDetailsModel,
			@IsNumber(message = "exclusionId {onlyAllowDigits}") @PathVariable("exclusionId") String exclusionId) {
		drugDetailsModel.setPageNumber(pageNumber);
		drugDetailsModel.setRecordSize(recordSize);
		drugDetailsModel.setExclusionId(Long.valueOf(exclusionId.trim()));
		return ResponseEntity.ok(exclusionService.getAllDrugExclusionDrugsDetails(drugDetailsModel));
	}

	@DeleteMapping("/drugs/{drugExclusionDetailsId}")
	@PreAuthorize("@securityService.hasAccessForExclusionManagement(authentication)")
	public ResponseEntity<?> deleteDrugExclusionDrugDetails(
			@IsNumber(message = "drugExclusionDetailsId {onlyAllowDigits}") @PathVariable("drugExclusionDetailsId") String drugExclusionDetailsId)
			throws AdminException {
		exclusionService.deleteDrugExclusionDrugDetails(Long.valueOf(drugExclusionDetailsId.trim()));
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{exclusionId}")
	@PreAuthorize("@securityService.hasAccessForExclusionManagement(authentication)")
	public ResponseEntity<Page<ExclusionTypeSearchResponseModel>> getAllExclusionTypes(
			ExclusionTypeSearchModel exclusionTypeSearchModel,
			@IsNumber(message = "exclusionId {onlyAllowDigits}") @PathVariable("exclusionId") String exclusionId) {
		return ResponseEntity
				.ok(exclusionService.getExclusionTypes(exclusionTypeSearchModel, Long.valueOf(exclusionId.trim())));
	}

	@GetMapping("/{exclusionId}/name")
	@PreAuthorize("@securityService.hasAccessForExclusionManagement(authentication)")
	public ResponseEntity<ExclusionTypeSearchResponseModel> getExclusionName(
			@IsNumber(message = "exclusionId {onlyAllowDigits}") @PathVariable("exclusionId") String exclusionId)
			throws AdminException {
		return ResponseEntity.ok(exclusionService.getExclusionName(Long.valueOf(exclusionId.trim())));
	}

	@PutMapping("/{exclusionId}/providers")
	@PreAuthorize("@securityService.hasAccessForExclusionManagement(authentication)")
	public ResponseEntity<ProviderExclusionResponseModel> addProviderExclusion(
			@IsNumber(message = "exclusionId {onlyAllowDigits}") @PathVariable("exclusionId") String exclusionId,
			@Valid @RequestBody ProviderExclusionRequestModel providerExclusionRequestModel) throws AdminException {
		logger.info("Add new Provider exclusion");
		return ResponseEntity.ok(providerExclusionService.addProviderExclusion(providerExclusionRequestModel,
				Long.parseLong(exclusionId.trim())));
	}

	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@PreAuthorize("@securityService.hasAccessForExclusionManagement(authentication)")
	public ResponseEntity<ExclusionDrugListUploadResponseModel> uploadDrugListFile(@RequestPart MultipartFile file)
			throws AdminException, IOException {
		return ResponseEntity.ok(exclusionDrugListUploadService.uploadDrugListFile(file));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ ConstraintViolationException.class, MethodArgumentNotValidException.class })
	ResponseEntity<DrugExclusionResponseModel> handleValidationExceptions(Exception ex) {
		DrugExclusionResponseModel invalidResponse = exclusionService.populateInvalidResponse(ex);
		logger.info("Bad Request 400 : Has Been Returned From PBM-Admin-Service (Drug Exclusion) Due To:{} ",
				ex.getMessage());
		return ResponseEntity.badRequest().body(invalidResponse);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AdminException.class)
	ResponseEntity<Object> handleValidationExceptions(AdminException ex) {
		DrugExclusionResponseModel invalidResponse = exclusionService.populateInvalidResponse(ex);
		logger.info("Bad Request 400 : Has Been Returned From PBM-Admin-Service (Drug Exclusion) Due To:{} ",
				ex.getMessage());
		return ResponseEntity.badRequest().body(invalidResponse);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleExceptions(Exception ex) {
		DrugExclusionResponseModel invalidResponse = exclusionService.populateFailedResponse();
		logger.info("Internal Server Error 500 : Has Been Returned From PBM-Admin-Service (Drug Exclusion) Due To : ",
				ex.getCause());
		ex.printStackTrace();
		return ResponseEntity.internalServerError().body(invalidResponse);
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleUnauthorizedException(AccessDeniedException ex) {
		DrugExclusionResponseModel invalidResponse = exclusionService.populateUnAuthorizedResponse(ex);
		logger.error("AccessDenied Exception 401: Has Been Returned From PBM-Admin-Service (Drug Exclusion) Due To :",
				ex);
		return new ResponseEntity<>(invalidResponse, HttpStatus.UNAUTHORIZED);
	}
}
