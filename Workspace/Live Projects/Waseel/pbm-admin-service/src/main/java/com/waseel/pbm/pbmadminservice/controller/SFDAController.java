package com.waseel.pbm.pbmadminservice.controller;

import com.waseel.pbm.pbmadminservice.model.SFDADrugReponseModel;
import com.waseel.pbm.pbmadminservice.model.SFDADrugRequestModel;
import com.waseel.pbm.pbmadminservice.model.SFDARequestModel;
import com.waseel.pbm.pbmadminservice.model.SFDAResponseModel;
import com.waseel.pbm.pbmadminservice.service.DataPopulationService;
import com.waseel.pbm.pbmadminservice.service.SFDAService;
import com.waseel.pbm.pbmadminservice.validationservice.TechnicalValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/payers/{payerId}/sfda")
public class SFDAController {

    private final Logger log = LoggerFactory.getLogger(SFDAController.class);

    @Autowired
    SFDAService sfdaService;

    @Autowired
    private DataPopulationService dataPopulationService;

    @Autowired
    TechnicalValidationService validationService;

    @PostMapping(consumes = "application/json")
    @PreAuthorize("@securityService.hasSFDAAccessForValidResponse(authentication,#payerId)")
    public ResponseEntity<SFDAResponseModel> addSFDACodeDetails(@PathVariable(name = "payerId") String payerId,
                                                                @Valid @RequestBody SFDARequestModel sfdaRequestModel) {
        return ResponseEntity.ok(sfdaService.addSFDACodeDetails(sfdaRequestModel));
    }

    @PostMapping(consumes = "multipart/form-data")
    @PreAuthorize("@securityService.hasSFDAAccessForValidResponse(authentication,#payerId)")
    public ResponseEntity<Map<Object, Object>> addSFDACodeDetailsFromFile(@PathVariable(name = "payerId") String payerId,
                                                                          @RequestPart MultipartFile file)
            throws IOException {
        return ResponseEntity.ok(sfdaService.addSFDACodeDetailsFromFile(file));
    }

    @PutMapping
    @PreAuthorize("@securityService.hasSFDAAccessForValidResponse(authentication,#payerId)")
    public ResponseEntity<SFDAResponseModel> updateSFDADrug(@PathVariable(name = "payerId") String payerId,
                                                            @Valid @RequestBody SFDARequestModel sfdaRequestModel) {
        return ResponseEntity.ok(sfdaService.editSFDADrug(sfdaRequestModel));
    }

    @GetMapping
    @PreAuthorize("@securityService.hasSFDAAccessForValidResponse(authentication,#payerId)")
    public ResponseEntity<Page<SFDADrugReponseModel>> getAllSFDADrugs(@PathVariable(name = "payerId") String payerId,
                                                                      @Valid @RequestBody SFDADrugRequestModel sfdaDrugModel) {
        return ResponseEntity.ok(sfdaService.getAllSFDADrug(sfdaDrugModel));
    }

    @DeleteMapping("/{sfdaCode}")
    @PreAuthorize("@securityService.hasSFDAAccessForValidResponse(authentication,#payerId)")
    public ResponseEntity<SFDAResponseModel> deleteSFDADrug(@PathVariable(name = "payerId") String payerId,
                                                            @PathVariable(name = "sfdaCode") String sfdaCode) {
        return ResponseEntity.ok(sfdaService.deleteSFDADrug(sfdaCode));
    }

    @PreAuthorize("@securityService.hasSFDAAccessForInvalidFailedResponse(authentication)")
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleValidationExceptions(Exception ex) {
        SFDAResponseModel sfdaResponseModel = validationService.populateInvalidResponse(ex);
        log.info("Internal Server Error 500 : Has Been Returned From PBM-Admin-Service Due To : ", ex.getCause());
        return new ResponseEntity<>(sfdaResponseModel, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PreAuthorize("@securityService.hasSFDAAccessForInvalidFailedResponse(authentication)")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        SFDAResponseModel sfdaResponseModel = validationService.populateInvalidResponse(ex);
        log.info("Bad request Due to : ", ex.getCause());
        return ResponseEntity.badRequest().body(sfdaResponseModel);
    }
}
