package com.waseel.prescription.controller;

import com.waseel.prescription.model.common.*;
import com.waseel.prescription.model.pbmpayerapis.MemberDemographicDataResponseModel;
import com.waseel.prescription.service.management.DataPopulationService;
import com.waseel.prescription.service.prescriptions.*;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/prescriptions/lov")
@Hidden
public class CommonController {

	private final Logger log = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private PayerConfigService payerConfigService;

	@Autowired
	private PayerMemberInfoService payerMemberInfoService;

	@Autowired
	private DataPopulationService dataPopulationService;

	@Autowired
	private PhysicianConfigService physicianConfigService;

	@Autowired
	private DrugServiceInfoService service;
	
	@Autowired
	private ProviderInformationService providerInformationService;

	@GetMapping("/payers")
	public ResponseEntity<List<PayerConfigModel>> getAllPayers(@RequestParam(name = "payer") String payer) {
		return ResponseEntity.ok(payerConfigService.getAllPayerDetails(payer));
	}

	@GetMapping("/physicians")
	public ResponseEntity<List<PhysicianConfigModel>> getAllPhysicianInformation(
			@RequestParam(name = "physician") String physician) {
		return ResponseEntity.ok(physicianConfigService.getAllPhysicianDetails(physician));
	}

	@GetMapping("/member-info")
	public ResponseEntity<List<PayerMemberInfoModel>> getMemberInfo(@RequestParam(name = "payerId") String payerId,
			@RequestParam(name = "value") String value) {
		return ResponseEntity.ok(payerMemberInfoService.getMemberInfo(payerId, value));
	}

	@GetMapping("/member-demographic-data/{idNumber}")
	public ResponseEntity<MemberDemographicDataResponseModel> getMemberDemographicData(
			@PathVariable(name = "idNumber") Long idNumber) {
		return payerMemberInfoService.getMemberDemographicData(idNumber);
	}

    @GetMapping("/drugs")
    public ResponseEntity<Page<DrugServiceModel>> getAllServiceCodeAndDescription(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
            @RequestParam(name = "value") String value,
            @RequestParam(name = "searchBy", defaultValue = "tradeName") String searchBy,
            @RequestParam(name = "payerId", required = false) String payerId,
            @RequestParam(name = "idNumber", required = false) String idNumber) {
        return ResponseEntity.ok(service.getDrugs(pageNumber, recordSize, value, payerId, idNumber, searchBy));
    }

	@GetMapping("/unit-types")
	public ResponseEntity<List<String>> getUnitTypes() {
		return ResponseEntity.ok(dataPopulationService.getUnitTypes());
	}

	@GetMapping("/frequency-types")
	public ResponseEntity<List<String>> getFrequencyTypes() {
		return ResponseEntity.ok(dataPopulationService.getFrequencyTypes());
	}

	@GetMapping("/status-types")
	public ResponseEntity<List<String>> getRequestStatusTypes() {
		return ResponseEntity.ok(dataPopulationService.getRequestStatusTypes());
	}
	
	@GetMapping("/providers")
	public ResponseEntity<List<ProviderInformationModel>> getAllProvidersInformation(
			@RequestParam(name = "value") String value) {
		return ResponseEntity.ok(providerInformationService.getAllProvidersInformation(value));
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleValidationExceptions(Exception ex,
			ContentCachingRequestWrapper requestWrapper) {
		CommonResponse invalidResponse = dataPopulationService.populateFailedResponse();
		log.info("Internal Server Error 500 : Has Been Returned From Prescription-Service Due To : ", ex.getCause());
		ex.printStackTrace();
		return new ResponseEntity<>(invalidResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping(value = "/insert/drug")
	public ResponseEntity<? > insertDrug() {
		return ResponseEntity.ok(dataPopulationService.insertDrug());
	}
	
}
