package com.waseel.prescription.controller;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.dispense.*;
import com.waseel.prescription.model.inquiry.detail.PrescriptionDetailInquiryResponseModel;
import com.waseel.prescription.service.prescriptions.DispensePrescriptionService;
import com.waseel.prescription.service.prescriptions.DrugSuggestionsService;
import com.waseel.prescription.service.validation.DispenseTechnicalValidationService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(value = "/payers/{payerId}/prescriptions/dispense")
public class PrescriptionDispenseController {

    private final Logger log = LoggerFactory.getLogger(PrescriptionDispenseController.class);

    @Autowired
    private DispenseTechnicalValidationService dispenseTechnicalValidationService;
    @Autowired
    private DrugSuggestionsService drugSuggestionsService;
    @Autowired
    private DispensePrescriptionService dispensePrescriptionService;

    @Hidden
    @PreAuthorize("@securityService.hasDispenseAccessForValidResponse(authentication,#payerId)")
    @Operation(summary = "Dispense the prescription", description = "This API is used to dispense a prescription.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Prescription dispensed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Data missing from request body", content = {
                    @Content(schema = @Schema(implementation = PrescriptionDispenseResponseModel.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized Access - The user do not have privilege to access this API", content = {
                    @Content(schema = @Schema(implementation = PrescriptionDispenseResponseModel.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Reach out to Waseel for further details", content = {
                    @Content(schema = @Schema(implementation = PrescriptionDispenseResponseModel.class))})})
    @PostMapping
    public ResponseEntity<PrescriptionDispenseResponseModel> managePrescriptionDispensedRequest(
            @RequestHeader(value = HttpHeaders.ORIGIN, required = false) String headerOrigin,
            @PathVariable @Parameter(name = "payerId", description = "Example: 102 i.e. Tawuniya code in Waseel system") String payerId,
            @Valid @RequestBody PrescriptionDispenseRequestModel dispensedRequestModel,
            ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) throws PrescriptionException {
        return ResponseEntity.ok(dispensePrescriptionService.managePrescriptionDispensedRequest(dispensedRequestModel,
                requestWrapper, payerId, headerOrigin));
    }

    @Hidden
    @PreAuthorize("@securityService.hasDispenseAccessForValidResponse(authentication,#payerId)")
    @PostMapping("/{ePrescriptionReferenceNumber}/dispensable-drugs")
    @Operation(summary = "Dispense the prescription", description = "This API is used to dispense a prescription.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Prescription dispensed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Data missing from request body", content = {
                    @Content(schema = @Schema(implementation = PrescriptionDispenseResponseModel.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized Access - The user do not have privilege to access this API", content = {
                    @Content(schema = @Schema(implementation = PrescriptionDispenseResponseModel.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Reach out to Waseel for further details", content = {
                    @Content(schema = @Schema(implementation = PrescriptionDispenseResponseModel.class))})})
    public ResponseEntity<PrescriptionDispenseResponseModel> managePrescriptionDispensedRequestWithoutPolicyCheck(
            @RequestHeader(value = HttpHeaders.ORIGIN, required = false) String headerOrigin,
            @PathVariable @Parameter(name = "payerId", description = "Example: 102 i.e. Tawuniya code in Waseel system")
                    String payerId, @PathVariable(name = "ePrescriptionReferenceNumber") String ePrescriptionReferenceNumber,
            @Valid @RequestBody DispenseDrugsRequestModel dispenseDrugsRequestModel,
            ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) throws PrescriptionException {
        return ResponseEntity.ok(dispensePrescriptionService.managePrescriptionDispensedRequestWithoutPolicyCheck(
                dispenseDrugsRequestModel, ePrescriptionReferenceNumber,requestWrapper, payerId, headerOrigin));
    }

    @PreAuthorize("@securityService.hasDispenseAccessForValidResponse(authentication,#payerId)")
    @GetMapping("/{ePrescriptionReferenceNumber}/dispensable-drugs")
    @Hidden
    public ResponseEntity<SuggestedDrugsModel> getSuggestedDrugs(
            @PathVariable String payerId,
            @PathVariable(name = "ePrescriptionReferenceNumber") String ePrescriptionReferenceNumber)
            throws PrescriptionException {
        return ResponseEntity.ok(drugSuggestionsService.getSuggestedDrugs(ePrescriptionReferenceNumber, payerId, false));
    }
    
    @PreAuthorize("@securityService.hasDispenseAccessForValidResponse(authentication,#payerId)")
    @PostMapping("/{ePrescriptionReferenceNumber}")
    @Operation(summary = "Dispense the prescription", description = "This API is used to dispense a prescription.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Prescription dispensed successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Data missing from request body", content = {
                    @Content(schema = @Schema(implementation = PrescriptionDispenseResponseModel.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized Access - The user do not have privilege to access this API", content = {
                    @Content(schema = @Schema(implementation = PrescriptionDispenseResponseModel.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - Reach out to Waseel for further details", content = {
                    @Content(schema = @Schema(implementation = PrescriptionDispenseResponseModel.class))})})
    public ResponseEntity<PrescriptionDetailInquiryResponseModel> managePrescriptionDispensedRequest(
            @RequestHeader(value = HttpHeaders.ORIGIN, required = false) String headerOrigin,
            @PathVariable @Parameter(name = "payerId", description = "Example: 102 i.e. Tawuniya code in Waseel system")
                    String payerId, @PathVariable(name = "ePrescriptionReferenceNumber")
            		String ePrescriptionReferenceNumber,
            @Valid @RequestBody DispenseDrugsRequestModel dispenseDrugsRequestModel,
            ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) throws PrescriptionException {
        return ResponseEntity.ok(dispensePrescriptionService.manageThirdPartyPrescriptionDispensedRequest(
                dispenseDrugsRequestModel, ePrescriptionReferenceNumber,requestWrapper, payerId, headerOrigin));
    }

    @PreAuthorize("@securityService.hasDispenseAccessForValidResponse(authentication,#payerId)")
    @GetMapping("/{ePrescriptionReferenceNumber}/drugs")
    @Hidden
    public ResponseEntity<Page<DispensableDrugsResponseModel>> getApprovedDrugForPartiallyDispense(
            @PathVariable String payerId, @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
            @RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
            @PathVariable(name = "ePrescriptionReferenceNumber") String ePrescriptionReferenceNumber,
            ContentCachingRequestWrapper requestWrapper) throws PrescriptionException {
        return ResponseEntity.ok(dispensePrescriptionService.getApprovedAndPartiallyDispenseDrugs(
                ePrescriptionReferenceNumber, payerId, requestWrapper, pageNumber, recordSize));
    }

    @PreAuthorize("@securityService.hasDispenseAccessForValidResponse(authentication,#payerId)")
    @GetMapping("/{ePrescriptionReferenceNumber}/drugs/summary")
    @Hidden
    public ResponseEntity<DispensableDrugsSummaryModel> getDispensableDrugsSummary(@PathVariable String payerId,
                                                                                   @PathVariable(name = "ePrescriptionReferenceNumber") String ePrescriptionReferenceNumber) {
        return ResponseEntity.ok(dispensePrescriptionService.getDispensableDrugsSummary(ePrescriptionReferenceNumber));
    }

    @GetMapping("/{ePrescriptionReferenceNumber}")
    @Hidden
    public ResponseEntity<Page<DispensedPrescriptionModel>> getDispenseDetail(@PathVariable String payerId,
                                                                              @RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
                                                                              @RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
                                                                              @PathVariable(name = "ePrescriptionReferenceNumber") String ePrescriptionReferenceNumber) {
        return ResponseEntity.ok(
                dispensePrescriptionService.getDispenseDetail(ePrescriptionReferenceNumber, pageNumber, recordSize));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PrescriptionException.class)
    public ResponseEntity<Object> handleValidationExceptions(PrescriptionException ex) {
        log.info(
                "{} Http Response for ePrescriptionReferenceNumber Id: {} Has been Returned From dispense Prescription-Service.",
                ex.getDispensedResponseModel().getStatus(),
                ex.getDispensedResponseModel().getePrescriptionReferenceNumber());
        return ResponseEntity.badRequest().body(ex.getDispensedResponseModel());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                             ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) {
        PrescriptionDispenseResponseModel invalidResponse = dispenseTechnicalValidationService
                .populateInvalidPrescriptionResponse(ex, requestWrapper, request);
        log.info("{} Http Response Has been Returned From dispense Prescription-Service", invalidResponse.getStatus());
        return ResponseEntity.badRequest().body(invalidResponse);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleValidationExceptions(Exception ex, ContentCachingRequestWrapper requestWrapper,
                                                             HttpServletRequest request) {
        PrescriptionDispenseResponseModel invalidDssResponse = dispenseTechnicalValidationService
                .populateFailedPrescriptionResponse(requestWrapper, request);
        log.info(
                "Internal Server Error 500 : Has Been Returned From Prescription-Service,"
                        + " for ePrescriptionReferenceNumber Id: {} Due To : {} ",
                invalidDssResponse.getePrescriptionReferenceNumber(), ex.getCause());
        ex.printStackTrace();
        return new ResponseEntity<>(invalidDssResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<Object> handleValidationExceptions(HttpMessageNotReadableException ex,
                                                      ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) {
        PrescriptionDispenseResponseModel invalidResponse = dispenseTechnicalValidationService
                .populateInvalidPrescriptionResponse(ex, requestWrapper, request);
        log.error("Exception:-", ex);
        return ResponseEntity.badRequest().body(invalidResponse);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleUnautorizedException(AccessDeniedException ex,
                                                             ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) {
        PrescriptionDispenseResponseModel invalidResponse = dispenseTechnicalValidationService
                .populateUnautorizedPrescriptionResponse(ex, requestWrapper, request);
        log.info("{} Http Response Has been Returned From dispense Prescription-Service", invalidResponse.getStatus());
        return new ResponseEntity<>(invalidResponse, HttpStatus.UNAUTHORIZED);
    }
}
