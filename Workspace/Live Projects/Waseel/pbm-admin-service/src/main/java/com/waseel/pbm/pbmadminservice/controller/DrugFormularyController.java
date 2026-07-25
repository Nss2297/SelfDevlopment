package com.waseel.pbm.pbmadminservice.controller;

import java.text.ParseException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.drugformulary.AddMemberPolicyDetailsResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyDrugDetailsModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyDrugDetailsRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyDrugDetailsResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyInvalidResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyMetaDataResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyMetaDataSearchModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyMetadataRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.PolicyDetailsModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.PolicyDetailsRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.PolicyResponseModel;
import com.waseel.pbm.pbmadminservice.service.DrugFormularyService;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsNumber;

@RestController
@RequestMapping("/drug-formulary")
@Validated
public class DrugFormularyController {

    private final Logger log = LoggerFactory.getLogger(DrugFormularyController.class);

    @Autowired
    private DrugFormularyService drugFormularyService;

    @PostMapping()
    @PreAuthorize("@securityService.hasAccessForDrugFormulary(authentication)")
    public ResponseEntity<?> addDrugFormularyDetails(
            @Valid @RequestBody DrugFormularyRequestModel drugFormularyRequestModel) throws AdminException {
        drugFormularyService.addDrugFormularyDetails(drugFormularyRequestModel);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("@securityService.hasAccessForDrugFormulary(authentication)")
    public ResponseEntity<Page<DrugFormularyMetaDataResponseModel>> getAllDrugFormularyMetaData(
            @Valid DrugFormularyMetaDataSearchModel searchModel) {
        return ResponseEntity.ok(drugFormularyService.getAllDrugFormularyMetaData(searchModel));
    }

    @GetMapping("/{formularyId}/policy-details")
    @PreAuthorize("@securityService.hasAccessForDrugFormulary(authentication)")
    public ResponseEntity<Page<PolicyDetailsModel>> getPolicyDetailsUsingFormularyId(
            PolicyDetailsModel policyDetailsModel,
            @IsNumber(message = "formularyId {onlyAllowDigits}") @PathVariable("formularyId") String formularyId) {
        policyDetailsModel.setFormularyId(Long.parseLong(formularyId.trim()));
        return ResponseEntity.ok(drugFormularyService.getPolicyDetailsUsingFormularyId(policyDetailsModel));
    }

    @GetMapping("/{formularyId}/drug-details")
    @PreAuthorize("@securityService.hasAccessForDrugFormulary(authentication)")
    public ResponseEntity<Page<DrugFormularyDrugDetailsModel>> getAllDrugFormularyDrugsDetails(
            DrugFormularyDrugDetailsModel dfddModel,
            @IsNumber(message = "formularyId {onlyAllowDigits}") @PathVariable("formularyId") String formularyId) {
        dfddModel.setFormularyId(Long.parseLong(formularyId.trim()));
        return ResponseEntity.ok(drugFormularyService.getAllDrugFormularyDrugsDetails(dfddModel));
    }

    @GetMapping("/{formularyId}")
    @PreAuthorize("@securityService.hasAccessForDrugFormulary(authentication)")
    public ResponseEntity<DrugFormularyMetaDataResponseModel> getDrugFormularyMetadataDetails(
            @IsNumber(message = "formularyId {onlyAllowDigits}") @PathVariable("formularyId") String formularyId)
            throws AdminException {
        return ResponseEntity
                .ok(drugFormularyService.getDrugFormularyMetadataDetails(Long.parseLong(formularyId.trim())));
    }

    @PutMapping("/{formularyId}")
    @PreAuthorize("@securityService.hasAccessForDrugFormulary(authentication)")
    public ResponseEntity<DrugFormularyMetaDataResponseModel> updateDrugFormularyMetadataDetails(
            @IsNumber(message = "formularyId {onlyAllowDigits}") @PathVariable("formularyId") String formularyId,
            @Valid @RequestBody DrugFormularyMetadataRequestModel drugFormularyMetadataRequestModel)
            throws AdminException {
        return ResponseEntity.ok(drugFormularyService.updateDrugFormularyMetadataDetails(
                Long.parseLong(formularyId.trim()), drugFormularyMetadataRequestModel));
    }

    @DeleteMapping("/{formularyId}")
    @PreAuthorize("@securityService.hasAccessForDrugFormulary(authentication)")
    public ResponseEntity<Object> deleteDrugFormulary(
            @IsNumber(message = "formularyId {onlyAllowDigits}") @PathVariable("formularyId") String formularyId)
            throws AdminException {
        drugFormularyService.deleteDrugFormulary(Long.parseLong(formularyId.trim()));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{formularyId}/verify-policy")
    public ResponseEntity<PolicyResponseModel> verifyPolicyDetails(
            @IsNumber(message = "formularyId {onlyAllowDigits}") @PathVariable("formularyId") String formularyId,
            String idNumber, String policyNumber, String classCode) {
        return ResponseEntity.ok(drugFormularyService.verifyPolicyDetails(idNumber,
                classCode, policyNumber));
    }

    @DeleteMapping("/policy-associations/{drugFormularyAssociationId}")
    @PreAuthorize("@securityService.hasAccessForDrugFormulary(authentication)")
    public ResponseEntity<Object> deleteDrugFormularyPolicyAssociation(
            @IsNumber(message = "drugFormularyAssociationId {onlyAllowDigits}") @PathVariable("drugFormularyAssociationId") String drugFormularyAssociationId)
            throws AdminException {
        log.info("Delete drug-formulary and policy association for drugFormularyAssociationId : [{}]",
                drugFormularyAssociationId);
        drugFormularyService.deleteDrugFormularyAndPolicyAssociation(Long.valueOf(drugFormularyAssociationId.trim()));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/drugs/{drugFormularyDetailsId}")
    @PreAuthorize("@securityService.hasAccessForDrugFormulary(authentication)")
    public ResponseEntity<Object> deleteDrugFormularyDrugDetails(
            @IsNumber(message = "drugFormularyDetailsId {onlyAllowDigits}") @PathVariable("drugFormularyDetailsId") String drugFormularyDetailsId)
            throws AdminException {
        drugFormularyService.deleteDrugFormularyDrugDetails(Long.parseLong(drugFormularyDetailsId.trim()));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{formularyId}/drugs")
    @PreAuthorize("@securityService.hasAccessForDrugFormulary(authentication)")
    public ResponseEntity<DrugFormularyDrugDetailsResponseModel> addDrugFormularyDrugDetails(
            @IsNumber(message = "formularyId {onlyAllowDigits}") @PathVariable("formularyId") String formularyId,
            @Valid @RequestBody DrugFormularyDrugDetailsRequestModel requestModel) throws AdminException {
        return ResponseEntity.ok().body(
                drugFormularyService.addDrugFormularyDrugDetails(Long.parseLong(formularyId.trim()), requestModel));
    }

    @PostMapping("/{formularyId}/policy-details")
    @PreAuthorize("@securityService.hasAccessForDrugFormulary(authentication)")
    public ResponseEntity<AddMemberPolicyDetailsResponseModel> addMemberPolicyDetails(
            @IsNumber(message = "formularyId {onlyAllowDigits}") @PathVariable("formularyId") String formularyId,
            @Valid @RequestBody PolicyDetailsRequestModel policyDetailsRequestModel)
            throws ParseException, AdminException {
        return ResponseEntity.ok().body(drugFormularyService
                .addOrUpdateMemberPolicyDetails(Long.parseLong(formularyId.trim()), policyDetailsRequestModel));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, AdminException.class, ConstraintViolationException.class,
            BindException.class})
    ResponseEntity<DrugFormularyInvalidResponseModel> handleValidationExceptions(Exception ex) {
        log.info("400 Bad request: Has Been Returned From PBM-Admin-Service Due to '{}'", ex.getMessage());
        return ResponseEntity.badRequest().body(drugFormularyService.populateInvalidFailedResponse(ex));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(AccessDeniedException ex) {
        log.info("401 UNAUTHORIZED Error : Has Been Returned From PBM-Admin-Service Due To : ", ex.getCause());
        return new ResponseEntity<>(drugFormularyService.populateUnAuthorizedResponse(ex), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<DrugFormularyInvalidResponseModel> handleException(Exception ex) {
        log.info("Internal Server Error 500 : Has Been Returned From PBM-Admin-Service Due To : ", ex.getCause());
        ex.printStackTrace();
        return new ResponseEntity<>(drugFormularyService.populateInvalidFailedResponse(ex),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
