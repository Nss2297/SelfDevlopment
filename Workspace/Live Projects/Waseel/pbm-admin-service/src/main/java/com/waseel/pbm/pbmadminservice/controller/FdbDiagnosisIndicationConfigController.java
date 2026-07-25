package com.waseel.pbm.pbmadminservice.controller;

import com.waseel.pbm.pbmadminservice.model.CommonResponse;
import com.waseel.pbm.pbmadminservice.model.FdbDiagnosisIndicationConfigModel;
import com.waseel.pbm.pbmadminservice.model.FdbDiagnosisIndicationConfigRequest;
import com.waseel.pbm.pbmadminservice.service.DataPopulationService;
import com.waseel.pbm.pbmadminservice.service.FdbDiagnosisIndicationConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fdb/diagnosis/indications")
public class FdbDiagnosisIndicationConfigController {

    private final Logger log = LoggerFactory.getLogger(FdbDiagnosisIndicationConfigController.class);

    @Autowired
    private FdbDiagnosisIndicationConfigService fdbDiagnosisIndicationConfigService;

    @Autowired
    private DataPopulationService dataPopulationService;

    @PostMapping
    public ResponseEntity<Object> addFDBDiagnosisConfiguration(
            @RequestBody() FdbDiagnosisIndicationConfigRequest fdbDiagnosisConfig) {
        fdbDiagnosisIndicationConfigService.addFDBDiagnosisConfiguration(fdbDiagnosisConfig);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFDBDiagnosisConfiguration(@PathVariable("id") Long id) {
        fdbDiagnosisIndicationConfigService.deleteFDBDiagnosisConfiguration(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFDBDiagnosisConfiguration(
            @RequestBody() FdbDiagnosisIndicationConfigRequest fdbDiagnosisConfig, @PathVariable("id") Long id) {
        fdbDiagnosisIndicationConfigService.updateFDBDiagnosisConfiguration(fdbDiagnosisConfig, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping
    public ResponseEntity<Page<FdbDiagnosisIndicationConfigModel>> getAllFDBDiagnosisConfiguration(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
            @RequestParam(name = "icdCode", required = false) String icdCode) {
        return ResponseEntity
                .ok(fdbDiagnosisIndicationConfigService.getFDBDiagnosisConfiguration(pageNumber, recordSize, icdCode));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleValidationExceptions(Exception ex) {
        CommonResponse invalidResponse = dataPopulationService.populateFailedResponse();
        log.info("Internal Server Error 500 : Has Been Returned From PBM-Admin-Service Due To : ", ex.getCause());
        ex.printStackTrace();
		return ResponseEntity.internalServerError().body(invalidResponse);
    }
}
