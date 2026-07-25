package com.waseel.prescription.clients;

import com.waseel.prescription.model.exclusion.DrugExclusionRequestModel;
import com.waseel.prescription.model.exclusion.DrugExclusionResponseModel;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "DrugExclusionServiceClient", url = "${drugexclusionservice.url}")
public interface DrugExclusionServiceClient {

    @PostMapping("/drug-exclusion")
    public ResponseEntity<DrugExclusionResponseModel> sendDrugExclusionRequest(
            @RequestBody DrugExclusionRequestModel model);
}
