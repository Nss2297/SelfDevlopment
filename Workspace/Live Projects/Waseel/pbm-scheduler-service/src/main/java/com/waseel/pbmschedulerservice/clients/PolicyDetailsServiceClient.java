package com.waseel.pbmschedulerservice.clients;

import com.waseel.pbmschedulerservice.model.policydetails.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PolicyDetailsServiceClient", url = "${client.tawuniya.url}" + "policies")
public interface PolicyDetailsServiceClient {

    @GetMapping
    public ResponseEntity<PolicyMetaDataResponseModel> getPolicyMetadataDetails(
            @RequestParam(name = "fromDate") String fromDate, @RequestParam(name = "toDate") String toDate,
            @RequestParam(name = "lastUpdateDate") String lastUpdateDate, @RequestParam(name = "pageSize") int pageSize,
            @RequestParam(name = "pageNumber") int pageNumber);

    @GetMapping("/{policyNumber}")
    public ResponseEntity<PolicyMetadataModel> getPolicyMetadataDetailsByPolicyNumber(
            @PathVariable(name = "policyNumber") String policyNumber);

    @GetMapping("/{policyNumber}/classes")
    public ResponseEntity<PolicyClassesResponseModel> getPolicyClassesDetails(
            @PathVariable(name = "policyNumber") String policyNumber);

    @GetMapping("/{policyNumber}/classes/{classCode}/benefits")
    public ResponseEntity<ClassBenefitsResponseModel> getPolicyClassBenefitsDetails(
            @PathVariable(name = "policyNumber") String policyNumber,
            @PathVariable(name = "classCode") String classCode);

    @GetMapping("/{policyNumber}/classes/{classCode}/benefits/{benefitCode}/cases")
    public ResponseEntity<BenefitCasesResponseModel> getPolicyClassBenefitCasesDetails(
            @PathVariable(name = "policyNumber") String policyNumber,
            @PathVariable(name = "classCode") String classCode, @PathVariable(name = "benefitCode") String benefitCode);

    @GetMapping("/{policyNumber}/classes/{classCode}/benefits/{benefitCode}/subcoverage")
    public ResponseEntity<BenefitSubCoverageResponseModel> getPolicyClassBenefitSubCoverageDetails(
            @PathVariable(name = "policyNumber") String policyNumber,
            @PathVariable(name = "classCode") String classCode, @PathVariable(name = "benefitCode") String benefitCode,
            @RequestParam(name = "pageSize") int pageSize, @RequestParam(name = "pageNumber") int pageNumber);

    @GetMapping("/{policyNumber}/endorsements")
    public ResponseEntity<PolicyEndorsementResponseModel> getPolicyEndorsementsDetails(
            @PathVariable(name = "policyNumber") String policyNumber);
}
