package com.waseel.prescription.clients;

import com.waseel.prescription.model.formulary.DrugFormularyDetailsModel;
import com.waseel.prescription.model.formulary.DrugFormularyRequestModel;
import com.waseel.prescription.model.formulary.DrugFormularyResponseModel;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "DrugFormularyServiceClient", url = "${drugformularyservice.url}")
public interface DrugFormularyServiceClient {

    @PostMapping("/payers/{payerId}/patients/{idNumber}/formulary")
    public ResponseEntity<List<DrugFormularyResponseModel>> sendDrugFormularyRequest(
            @PathVariable String idNumber, @PathVariable String payerId, @RequestBody DrugFormularyRequestModel model);

    @GetMapping("/payers/{payerId}/patients/{idNumber}/formulary")
    public ResponseEntity<DrugFormularyDetailsModel> getDrugFormularyDetails(
            @PathVariable String idNumber, @PathVariable String payerId);
}
