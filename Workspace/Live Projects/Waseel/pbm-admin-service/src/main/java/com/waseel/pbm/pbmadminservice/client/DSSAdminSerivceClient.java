package com.waseel.pbm.pbmadminservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.waseel.pbm.pbmadminservice.model.mdss.MemberChronicDiseaseResponseModel;

@FeignClient(name = "DSSAdminSerivceClient", url = "${dssAdminSerivceClient.url}")
public interface DSSAdminSerivceClient {

    @GetMapping("/member-chronic-disease/{memberId}")
	public ResponseEntity<List<MemberChronicDiseaseResponseModel>> getChronicData(@RequestParam(name = "memberId") String idNumber);
}