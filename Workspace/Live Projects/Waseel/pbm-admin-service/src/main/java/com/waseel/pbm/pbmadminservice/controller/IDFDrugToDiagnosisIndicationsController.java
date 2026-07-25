package com.waseel.pbm.pbmadminservice.controller;

import com.waseel.pbm.pbmadminservice.model.CommonResponse;
import com.waseel.pbm.pbmadminservice.model.IDFDrugToDiagnosisIndicationsRequest;
import com.waseel.pbm.pbmadminservice.model.IDFDrugToDiagnosisModel;
import com.waseel.pbm.pbmadminservice.service.DataPopulationService;
import com.waseel.pbm.pbmadminservice.service.IDFDrugToDiagnosisIndicationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/idf/drugs/diagnosis/indications")
public class IDFDrugToDiagnosisIndicationsController {

    private final Logger log = LoggerFactory.getLogger(IDFDrugToDiagnosisIndicationsController.class);

    @Autowired
    private IDFDrugToDiagnosisIndicationsService idfDrugToDiagnosisIndicationsService;

    @Autowired
    private DataPopulationService dataPopulationService;

    @PostMapping
    public ResponseEntity<Object> addIDFDrugToDiagnosisIndications(
            @RequestBody IDFDrugToDiagnosisIndicationsRequest request) {
        idfDrugToDiagnosisIndicationsService.addIDFDrugToDiagnosisIndications(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteIDFDrugToDiagnosisIndications(@PathVariable("id") Long id) {
        idfDrugToDiagnosisIndicationsService.deleteIDFDrugToDiagnosisIndications(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateIDFDrugToDiagnosisIndications(
            @RequestBody IDFDrugToDiagnosisIndicationsRequest request, @PathVariable("id") Long id) {
        idfDrugToDiagnosisIndicationsService.updateIDFDrugToDiagnosisIndications(request, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping()
    public ResponseEntity<Page<IDFDrugToDiagnosisModel>> getIDFDrugToDiagnosisIndications(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
            @RequestParam(name = "serviceCode", required = false) String serviceCode,
            @RequestParam(name = "icdDiagnosisCode", required = false) String icdCode) {
        return ResponseEntity.ok(idfDrugToDiagnosisIndicationsService
                .getIDFDrugToDiagnosisIndicationsDetails(pageNumber, recordSize, serviceCode, icdCode));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleValidationExceptions(Exception ex) {
        CommonResponse invalidResponse = dataPopulationService.populateFailedResponse();
        log.info(
                "Internal Server Error 500 : Has Been Returned From PBM-Admin-Service (IDFDrugToDiagnosisIndications) Due To : ",
                ex.getCause());
        ex.printStackTrace();
		return ResponseEntity.internalServerError().body(invalidResponse);
    }
}
