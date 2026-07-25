package com.waseel.pbm.pbmadminservice.service.membermanagement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.waseel.pbm.pbmadminservice.enums.DssPayerTransactionType;
import com.waseel.pbm.pbmadminservice.enums.management.EntityFields;
import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.membermanagement.MemberHistoryModel;
import com.waseel.pbm.pbmadminservice.model.membermanagement.MemberHistoryResponseModel;
import com.waseel.pbm.pbmadminservice.model.membermanagement.MembersRequestModel;
import com.waseel.pbm.pbmadminservice.model.membermanagement.MembersResponseModel;
import com.waseel.pbm.pbmadminservice.persist.prescriptionservice.MappingPayerId;
import com.waseel.pbm.pbmadminservice.repository.prescriptionservice.MappingPayerIdRepository;
import com.waseel.pbm.pbmadminservice.specification.membermanagement.MemberManagementSpecification;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;

@Service
public class MemberManagementService {

	@Autowired
	private MemberManagementSpecification memberManagementSpecification;

	@Autowired
	private MappingPayerIdRepository mappingPayerIdRepository;

	private final Logger logger = LoggerFactory.getLogger(MemberManagementService.class);

	public Page<MembersResponseModel> fetchAllMembersWithPrescription(MembersRequestModel requestSearchFilters) {
		String payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		logger.info("Fetch member details for payer: [{}], page number: [{},], and recode size: [{}]", payerId,
				requestSearchFilters.getPageNumber(), requestSearchFilters.getRecordSize());
		requestSearchFilters.setPayerId(fetchPayerIdByMappedPayerId(payerId));
		validateRequestedSearchingFilterData(requestSearchFilters);
		return memberManagementSpecification.paginatedMembersListWithFilter(requestSearchFilters);
	}

	public Page<MemberHistoryResponseModel> getMemberHistoryWithPrescription(String idNumber, int pageNumber,
			int recordSize) {
		List<String> payerIds = new ArrayList<>();
		String payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		payerIds.add(payerId);
		payerIds.add(fetchPayerIdByMappedPayerId(payerId));
		logger.info(
				"Fetch member prescription history for payerId:[{}], idNumber: [{}], page number: [{},], and recode size: [{}]",
				payerId, idNumber, pageNumber, recordSize);
		MemberHistoryModel memberHistoryModel = new MemberHistoryModel(Long.parseLong(idNumber), pageNumber, recordSize,
				payerIds);
		return memberManagementSpecification.findMemberHistoryWithPagination(memberHistoryModel);
	}

	private void validateRequestedSearchingFilterData(MembersRequestModel requestSearchFilters) {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		Set<ConstraintViolation<MembersRequestModel>> constraintViolations = new HashSet<>();
		if (StringUtils.isNotBlank(requestSearchFilters.getName())) {
			constraintViolations.addAll(validator.validateProperty(requestSearchFilters, EntityFields.NAME.value()));
		}
		if (StringUtils.isNotBlank(requestSearchFilters.getIdNumber())) {
			constraintViolations
					.addAll(validator.validateProperty(requestSearchFilters, EntityFields.ID_NUMBER.value()));
		}
		if (StringUtils.isNotBlank(requestSearchFilters.getGender())) {
			constraintViolations.addAll(validator.validateProperty(requestSearchFilters, EntityFields.GENDER.value()));
		}
		if (StringUtils.isNotBlank(requestSearchFilters.getNationality())) {
			constraintViolations
					.addAll(validator.validateProperty(requestSearchFilters, EntityFields.NATIONALITY.value()));
		}
		constraintViolations.addAll(validator.validateProperty(requestSearchFilters, EntityFields.PAYER_ID.value()));
		if (!constraintViolations.isEmpty()) {
			throw new ConstraintViolationException(constraintViolations);
		}
	}

	public MembersResponseModel populateInvalidResponseForConstraints(Exception exception) {
		List<String> errors = new ArrayList<>();
		if (exception instanceof ConstraintViolationException) {
			ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
			errors.addAll(constraintViolationException.getConstraintViolations().stream()
					.map(ConstraintViolation::getMessage).collect(Collectors.toList()));
		}
		if (exception instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) exception;
			errors.addAll(methodArgumentNotValidException.getBindingResult().getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
		}
		return new MembersResponseModel(errors);
	}

	public MembersResponseModel populateInvalidResponse(AdminException adminException) {
		List<String> errors = new ArrayList<>();
		errors.add(adminException.getMessage());
		return new MembersResponseModel(errors);
	}

	public MembersResponseModel populateFailedResponse(Exception exception) {
		List<String> errors = new ArrayList<>();
		errors.add(exception.getMessage());
		return new MembersResponseModel(errors);
	}

	public MembersResponseModel populateUnAuthorizedResponse(AccessDeniedException ex) {
		List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());
		return new MembersResponseModel(errors);
	}

	public String fetchPayerIdByMappedPayerId(String payerId) {
		return mappingPayerIdRepository.findByPayerIdAndTransactionTypeAndIsEnabled(payerId,
				DssPayerTransactionType.PRESCRIPTION.value(), true).map(MappingPayerId::getMapperPayerId)
				.orElse(payerId);
	}
}
