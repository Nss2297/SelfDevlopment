package com.waseel.pbm.pbmadminservice.controller;

import com.waseel.pbm.pbmadminservice.model.*;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.network.NetworkExclusionModel;
import com.waseel.pbm.pbmadminservice.model.payer.PolicyMetadataResponseModel;
import com.waseel.pbm.pbmadminservice.persist.hira.ICDDiagnosis;
import com.waseel.pbm.pbmadminservice.service.*;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/lov")
public class CommonController {

    private final Logger log = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private DataPopulationService dataPopulationService;

    @Autowired
    private DrugServiceInfoService serviceCodeAndDescService;

    @Autowired
    private ICDDiagnosisService icdCodeAndDescService;

    @Autowired
    private PayerConfigService payerConfigService;

    @Autowired
    private DrugToDiagnosisApprovalCategoryService approvalCategoryService;

    @Autowired
    private PCDrugToDiagnosisService pcDrugToDiagnosisService;

    @Autowired
    PBMPayerApisService memberDetailService;

    @Autowired
    private NetworkExclusionService networkExclusionService;

    @Autowired
    private ProviderInformationService providerInformationService;

    @Autowired
    private SpecialityService specialityService;

    @GetMapping("/drugs")
    public ResponseEntity<Page<DrugServiceModel>> getAllServiceCodeAndDescription(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
            @RequestParam(name = "serviceCode", required = false) String serviceCode,
            @RequestParam(name = "description", required = false) String description) {
        return ResponseEntity.ok(serviceCodeAndDescService.getAllServiceCodeAndDescription(pageNumber, recordSize,
                serviceCode, description));
    }

    @GetMapping("/diagnosis")
    public ResponseEntity<Page<ICDDiagnosis>> getAllIcdCodeAndDescription(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
            @RequestParam(name = "icdCode", required = false) String icdCode,
            @RequestParam(name = "description", required = false) String description) {
        return ResponseEntity
                .ok(icdCodeAndDescService.getAllIcdCodeAndDescription(pageNumber, recordSize, icdCode, description));
    }

    @GetMapping("/payers")
    public ResponseEntity<Page<PayerConfigModel>> getAllPayerDetails(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
            @RequestParam(name = "payerId", required = false) String payerId) {
        return ResponseEntity.ok(payerConfigService.getAllPayerDetails(pageNumber, recordSize, payerId));
    }

    @GetMapping("/providers")
    public ResponseEntity<Page<ProviderInformationModel>> getAllProvidersInformation(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
            @RequestParam(name = "value", required = false) String value) {
        return ResponseEntity.ok(providerInformationService.getAllProvidersInformation(pageNumber, recordSize, value));
    }


    @GetMapping("/approval-categories")
    public ResponseEntity<Page<DrugToDiagnosisApprovalCategoryModel>> getAllCategoryOfApproval(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
            @RequestParam(name = "name", required = false) String name) {
        return ResponseEntity.ok(approvalCategoryService.getAllCategoryOfApproval(pageNumber, recordSize, name));
    }

    @GetMapping("/rejection-categories")
    public ResponseEntity<List<String>> getAllRejectionCategory() {
        return ResponseEntity.ok(pcDrugToDiagnosisService.getAllRejectionCategory());
    }

    @GetMapping("/drugs/statuses")
    public ResponseEntity<List<String>> getAllServiceStatus() {
        return ResponseEntity.ok(pcDrugToDiagnosisService.getAllServiceStatus());
    }

    @GetMapping("/modules")
    public ResponseEntity<List<String>> getAllModuleName() {
        return ResponseEntity.ok(pcDrugToDiagnosisService.getAllModuleName());
    }

    @GetMapping("/members/{idNumber}")
    public ResponseEntity<MemberDetailsResponseModel> getMemberDetails(@PathVariable Long idNumber) {
        return memberDetailService.getMemberDetails(idNumber);
    }

    @GetMapping("/policy-details/{policyNumber}")
    public ResponseEntity<PolicyMetadataResponseModel> getPolicyDetails(@PathVariable String policyNumber) {
        return memberDetailService.getPolicyDetails(policyNumber);
    }

    @GetMapping("/drug-exclusions/networks")
    @PreAuthorize("@securityService.hasAccessForExclusionManagement(authentication)")
    public ResponseEntity<Page<NetworkExclusionModel>> getAllNetworks(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
            @RequestParam(name = "value", required = false) String value) {
        log.info("Get all Networks for Network exclusion.");
        return ResponseEntity
                .ok(networkExclusionService.getAllNetworkList(pageNumber, recordSize,value));
    }

    @GetMapping("/specialities")
    public ResponseEntity<Page<SpecialityModel>> getAllSpecialities(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
            @RequestParam(name = "value", required = false) String value){
        return ResponseEntity.ok(specialityService.findSpecialitiesWithPagination(pageNumber, recordSize,
                value));
    }

    @ExceptionHandler(FeignException.class)
    ResponseEntity<CommonResponse> handleFeignExceptions(FeignException ex) {
        log.info("FeignException with status {}: {}", ex.status(), ex);
        int status = ex.status() == -1 ? HttpStatus.SERVICE_UNAVAILABLE.value() : ex.status();
        return ResponseEntity.status(status)
                .body(dataPopulationService.populateInvalidResponse(ex.contentUTF8(), status));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleValidationExceptions(Exception ex) {
        CommonResponse invalidResponse = dataPopulationService.populateFailedResponse();
        log.info("Internal Server Error 500 : Has Been Returned From PBM-Admin-Service Due To : ", ex.getCause());
        ex.printStackTrace();
        return ResponseEntity.internalServerError().body(invalidResponse);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(AccessDeniedException ex) {
        CommonResponse invalidResponse = dataPopulationService.populateUnAuthorizedResponse(ex);
        log.error("AccessDenied Exception 401: Has Been Returned From PBM-Admin-Service Due To :", ex);
        return new ResponseEntity<>(invalidResponse, HttpStatus.UNAUTHORIZED);
    }
}
