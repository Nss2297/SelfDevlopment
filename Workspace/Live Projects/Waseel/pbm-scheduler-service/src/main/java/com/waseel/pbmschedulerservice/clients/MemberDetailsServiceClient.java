package com.waseel.pbmschedulerservice.clients;

import com.waseel.pbmschedulerservice.model.memberdetails.MemberDetailsModel;
import com.waseel.pbmschedulerservice.model.memberdetails.MemberDetailsResponseModel;
import com.waseel.pbmschedulerservice.model.memberdetails.MembersResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "MemberDetailsServiceClient", url = "${client.tawuniya.url}" + "members")
public interface MemberDetailsServiceClient {

    @GetMapping("/{policyNumber}")
    public ResponseEntity<MembersResponseModel> getMemberDetailsByPolicyNumber(
            @PathVariable(name = "policyNumber") String policyNumber,
            @RequestParam(name = "pageSize") int pageSize,
            @RequestParam(name = "pageNumber") int pageNumber);

    @GetMapping
    public ResponseEntity<MemberDetailsModel> getMemberDetailsByIDNumber(
            @RequestParam(name = "idNumber") Long idNumber);

    @GetMapping
    public ResponseEntity<MemberDetailsResponseModel> getMemberDetailsByLastUpdateDate(
            @RequestParam(name = "lastUpdateDate") String lastUpdateDate,
            @RequestParam(name = "pageSize") int pageSize,
            @RequestParam(name = "pageNumber") int pageNumber);
}
