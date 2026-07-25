package com.waseel.pbm.pbmadminservice.controller;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.membermanagement.MemberHistoryResponseModel;
import com.waseel.pbm.pbmadminservice.model.membermanagement.MembersRequestModel;
import com.waseel.pbm.pbmadminservice.model.membermanagement.MembersResponseModel;
import com.waseel.pbm.pbmadminservice.service.membermanagement.MemberManagementService;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsNumber;

@RestController
@RequestMapping("/member-management")
@Validated
public class MemberManagementController {

	@Autowired
	private MemberManagementService memberManagementService;

	private final Logger logger = LoggerFactory.getLogger(MemberManagementController.class);

	@GetMapping
	@PreAuthorize("@securityService.hasAccessForMemberManagement(authentication)")
	public ResponseEntity<Page<MembersResponseModel>> getAllMembersWithPrescription(
			MembersRequestModel membersRequestModel) {
		return ResponseEntity.ok(memberManagementService.fetchAllMembersWithPrescription(membersRequestModel));
	}

	@GetMapping("{idNumber}")
	@PreAuthorize("@securityService.hasAccessForMemberManagement(authentication)")
	public ResponseEntity<Page<MemberHistoryResponseModel>> getMemberHistoryWithPrescription(
			@IsNumber(message = "idNumber {onlyAllowDigits}") @PathVariable(name = "idNumber") String idNumber,
			@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(name = "recordSize", defaultValue = "10") int recordSize) {
		return ResponseEntity
				.ok(memberManagementService.getMemberHistoryWithPrescription(idNumber.trim(), pageNumber, recordSize));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({ ConstraintViolationException.class, MethodArgumentNotValidException.class })
	ResponseEntity<Object> handleBeanValidationExceptions(Exception exception) {
		logger.error("Bad Request 400 : Has Been Returned From PBM-Admin-Service (member-management) exception: ",
				exception);
		return ResponseEntity.badRequest()
				.body(memberManagementService.populateInvalidResponseForConstraints(exception));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(AdminException.class)
	ResponseEntity<Object> handleValidationExceptions(AdminException adminException) {
		logger.error("Bad Request 400 : Has Been Returned From PBM-Admin-Service (member-management) exception: ",
				adminException);
		return ResponseEntity.badRequest().body(memberManagementService.populateInvalidResponse(adminException));
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleValidationExceptions(Exception exception) {
		logger.error(
				"Internal Server Error 500 : Has Been Returned From PBM-Admin-Service (member-management) exception: ",
				exception);
		return ResponseEntity.internalServerError().body(memberManagementService.populateFailedResponse(exception));
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleUnauthorizedException(AccessDeniedException ex) {
		logger.error(
				"AccessDenied Exception 401: Has Been Returned From PBM-Admin-Service (member-management) exception:",
				ex);
		return new ResponseEntity<>(memberManagementService.populateUnAuthorizedResponse(ex), HttpStatus.UNAUTHORIZED);
	}
}
