package com.waseel.drugexclusionvalidationservice.exclusions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.drugexclusionvalidationservice.model.DrugExclusionRequestModel;
import com.waseel.drugexclusionvalidationservice.model.DrugExclusionResponseModel;
import com.waseel.drugexclusionvalidationservice.service.InvalidResponseService;

@SpringBootTest
@ActiveProfiles("test")
class ExclusionRequestValidationTests {

	private String requestId = "f90abad5-8c8d-4f61-afe9-36af91e30637";
	private String payerId = "102";
	private String providerId = "99999";

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private Validator validator;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private InvalidResponseService invalidResponseService;

	private DrugExclusionRequestModel drugExclusionRequestModel;

	@BeforeEach
	void setUpData() {
		List<String> drugList = new ArrayList<>();
		drugList.add("123-88-99");
		drugExclusionRequestModel = generateSpecialityExclusionRequestModel(requestId, null, "12345678901234567890123",
				drugList, payerId, providerId);
	}

	@Test
	@DisplayName("Request model Not Empty validations")
	void testNotEmptyValidation() {
		DrugExclusionRequestModel requestModel = generateSpecialityExclusionRequestModel(null, null, "", null, null,
				null);
		List<ConstraintViolation<DrugExclusionRequestModel>> sortedViolations = getSortedViolations(requestModel);
		Assertions.assertFalse(sortedViolations.isEmpty());
		Assertions.assertEquals(6, sortedViolations.size());
		List<String> messages = new ArrayList<>();
		for (ConstraintViolation<DrugExclusionRequestModel> violation : sortedViolations) {
			String propertyPath = violation.getPropertyPath().toString() + " ";
			String expectedMessage = propertyPath
					+ messageSource.getMessage("notEmptyValidation", null, Locale.getDefault());
			messages.add(expectedMessage);
		}
		Assertions.assertEquals(
				"drugList shouldn't be null or empty, payerId shouldn't be null or empty,"
						+ " physicianLicenseNumber shouldn't be null or empty,"
						+ " physicianSpeciality shouldn't be null or empty,"
						+ " providerId shouldn't be null or empty, requestId shouldn't be null or empty",
				messages.toString().replace("[", "").replace("]", ""));
	}

	@Test
	@DisplayName("Request model other validations")
	void testOtherValidation() {
		List<String> drugList = new ArrayList<>();
		drugList.add("123-88-99");
		DrugExclusionRequestModel requestModel = generateSpecialityExclusionRequestModel(requestId, " ", "123 45",
				drugList, payerId, providerId);
		List<ConstraintViolation<DrugExclusionRequestModel>> sortedViolations = getSortedViolations(requestModel);
		Assertions.assertFalse(sortedViolations.isEmpty());
		Assertions.assertEquals(2, sortedViolations.size());
		List<String> messages = new ArrayList<>();
		for (ConstraintViolation<DrugExclusionRequestModel> violation : sortedViolations) {
			String propertyPath = violation.getPropertyPath().toString() + " ";
			String expectedMessage = propertyPath
					+ messageSource.getMessage("noWhiteSpaceCharacterValidation", null, Locale.getDefault());
			messages.add(expectedMessage);
		}
		Assertions.assertEquals(
				"physicianLicenseNumber should not contain white space,"
						+ " physicianSpeciality should not contain white space",
				messages.toString().replace("[", "").replace("]", ""));
	}

	@Test
	@DisplayName("Bean/Technical field validation for requestModel of Speciality Exclusion Api.")
	void beanLenghtFieldValidationTest() {

		DrugExclusionResponseModel invalidReponse = invalidResponseService.populateInvalidResponse(
				getMethodArgumentNotValidExceptionLengthField(), getContentCachingRequestWrapper());
		Assertions.assertNotNull(invalidReponse);
		assertThat(invalidReponse.getErrorCode()).isEqualTo("Invalid");
		assertThat(invalidReponse.getErrorDescription()).isEqualTo(
				"physicianLicenseNumber shouldn't be more than 20, physicianSpeciality shouldn't be null or empty");
		assertThat(invalidReponse.getRequestId()).isEqualTo(requestId);
	}

	private MethodArgumentNotValidException getMethodArgumentNotValidExceptionLengthField() {
		FieldError fieldError1 = new FieldError("physicianLicenseNumber", "physicianLicenseNumber",
				"12345678901234567890123", false, null, new Object[] {}, "physicianLicenseNumber "
						+ messageSource.getMessage("noMoreThan20LengthValidation", null, Locale.ENGLISH));
		FieldError fieldError2 = new FieldError("physicianSpeciality", "physicianSpeciality", null, false, null,
				new Object[] {},
				"physicianSpeciality " + messageSource.getMessage("notEmptyValidation", null, Locale.ENGLISH));
		FieldError[] errors = { fieldError1, fieldError2 };
		return createExceptionWithFieldErrors(errors);
	}

	private MethodArgumentNotValidException createExceptionWithFieldErrors(FieldError... fieldErrors) {
		BindingResult bindingResult = new BeanPropertyBindingResult(drugExclusionRequestModel, "");
		for (FieldError fieldError : fieldErrors) {
			bindingResult.addError(fieldError);
		}
		return new MethodArgumentNotValidException(mock(MethodParameter.class), bindingResult);
	}

	private ContentCachingRequestWrapper getContentCachingRequestWrapper() {
		ContentCachingRequestWrapper cachingRequestWrapper = null;
		try {
			MockHttpServletRequest hRequest = new MockHttpServletRequest();
			String req = this.mapper.writeValueAsString(drugExclusionRequestModel);
			hRequest.setRequestURI("/drug-exclusion");
			hRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
			hRequest.setContent(req.getBytes(StandardCharsets.UTF_8.name()));
			cachingRequestWrapper = new ContentCachingRequestWrapper(hRequest);
			cachingRequestWrapper.setRequest(hRequest);
			FileCopyUtils.copyToByteArray(cachingRequestWrapper.getInputStream());
			return cachingRequestWrapper;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cachingRequestWrapper;
	}

	private List<ConstraintViolation<DrugExclusionRequestModel>> getSortedViolations(
			DrugExclusionRequestModel requestModel) {
		Set<ConstraintViolation<DrugExclusionRequestModel>> violations = validator.validate(requestModel);
		List<ConstraintViolation<DrugExclusionRequestModel>> sortedViolations = new ArrayList<>(violations);
		Collections.sort(sortedViolations, Comparator.comparing(violation -> violation.getPropertyPath().toString()));
		return sortedViolations;
	}

	private DrugExclusionRequestModel generateSpecialityExclusionRequestModel(String requestId,
			String physicianSpeciality, String physicianLicenseNumber, List<String> drugList, String payerId,
			String providerId) {
		return new DrugExclusionRequestModel(requestId, physicianLicenseNumber, drugList, physicianSpeciality, payerId,
				providerId);
	}

}
