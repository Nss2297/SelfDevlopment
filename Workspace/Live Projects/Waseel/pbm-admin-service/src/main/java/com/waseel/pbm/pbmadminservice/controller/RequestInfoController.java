//package com.waseel.pbm.pbmadminservice.controller;
//
//import com.waseel.pbm.pbmadminservice.model.CommonResponse;
//import com.waseel.pbm.pbmadminservice.model.RequestInfoModel;
//import com.waseel.pbm.pbmadminservice.model.RequestInfoResponse;
//import com.waseel.pbm.pbmadminservice.service.DataPopulationService;
//import com.waseel.pbm.pbmadminservice.service.RequestInfoService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/dss/requests")
//public class RequestInfoController {
//    private final Logger log = LoggerFactory.getLogger(RequestInfoController.class);
//    @Autowired
//    private RequestInfoService requestInfoService;
//    @Autowired
//    private DataPopulationService dataPopulationService;
//
//    @GetMapping
//    public ResponseEntity<Page<RequestInfoModel>> getRequestInfoList(
//            @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
//            @RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
//            @RequestParam(name = "requestId", required = false) String requestId,
//            @RequestParam(name = "memberId", required = false) String memberId,
//            @RequestParam(name = "dateFrom") String dateFrom,
//            @RequestParam(name = "dateTo") String dateTo,
//            @RequestParam(name = "payerId") String payerId) {
//        return ResponseEntity.ok(
//                requestInfoService.getRequestInfoList(requestId, memberId, dateFrom, dateTo, payerId, pageNumber, recordSize));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<RequestInfoResponse> getRequestInfoDetail(
//            @PathVariable("id") String requestId) {
//        return ResponseEntity.ok(requestInfoService.getRequestInfoDetail(requestId));
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    ResponseEntity<Object> handleValidationExceptions(Exception ex) {
//        CommonResponse invalidResponse = dataPopulationService.populateFailedResponse();
//        log.info("Internal Server Error 500 : Has Been Returned From PBM-Admin-Service Due To : ", ex.getCause());
//        ex.printStackTrace();
//		return ResponseEntity.internalServerError().body(invalidResponse);
//    }
//}
