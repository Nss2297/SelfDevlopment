package com.waseel.pbm.pbmadminservice.controller;

import com.waseel.pbm.pbmadminservice.model.CommonResponse;
import com.waseel.pbm.pbmadminservice.model.ServiceCodeGCNSeqNoMappingModel;
import com.waseel.pbm.pbmadminservice.model.ServiceCodeGCNSeqNoMappingRequest;
import com.waseel.pbm.pbmadminservice.service.DataPopulationService;
import com.waseel.pbm.pbmadminservice.service.ServiceCodeGCNSeqNoMappingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drugs/gcn-number")
public class ServiceCodeGCNSeqNoMappingController {

    private final Logger log = LoggerFactory.getLogger(ServiceCodeGCNSeqNoMappingController.class);

    @Autowired
    ServiceCodeGCNSeqNoMappingService serviceCodeGCNSeqNoMappingService;
    @Autowired
    private DataPopulationService dataPopulationService;

    @GetMapping
    public ResponseEntity<Page<ServiceCodeGCNSeqNoMappingModel>> getServiceCodeGCNSequenceNumberMappingData(
            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
            @RequestParam(name = "gcnSeqNumber", required = false, defaultValue = "0") int gcnSeqNumber,
            @RequestParam(name = "serviceCode", required = false) String serviceCode) {
        return ResponseEntity.ok().body(serviceCodeGCNSeqNoMappingService
                .getServiceCodeGCNSequenceNumberMappingData(pageNumber, recordSize, gcnSeqNumber, serviceCode));
    }

    @PostMapping
    public ResponseEntity<Object> addServiceCodeGCNSequenceNumberMappingData(
            @RequestBody ServiceCodeGCNSeqNoMappingRequest request) {
        serviceCodeGCNSeqNoMappingService.addServiceCodeGCNSequenceNumberMappingData(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateServiceCodeGCNSequenceNumberMappingData(
            @RequestBody ServiceCodeGCNSeqNoMappingRequest request, @PathVariable("id") Long id) {
        serviceCodeGCNSeqNoMappingService.updateServiceCodeGCNSequenceNumberMappingData(request, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteServiceCodeGCNSequenceNumberMappingData(
            @PathVariable("id") Long id) {
        serviceCodeGCNSeqNoMappingService.deleteServiceCodeGCNSequenceNumberMappingData(id);
        return ResponseEntity.status(HttpStatus.OK).build();
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
