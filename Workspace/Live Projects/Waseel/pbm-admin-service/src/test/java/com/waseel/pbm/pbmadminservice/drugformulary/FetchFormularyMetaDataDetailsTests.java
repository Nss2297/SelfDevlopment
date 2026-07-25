package com.waseel.pbm.pbmadminservice.drugformulary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyInvalidResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyMetaDataResponseModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyMetadata;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugFormularyMetadataRepository;
import com.waseel.pbm.pbmadminservice.service.DrugFormularyService;

@SpringBootTest
@ActiveProfiles("test")
class FetchFormularyMetaDataDetailsTests {

	@Autowired
	private DrugFormularyService drugFormularyService;

	@MockBean
	private DrugFormularyMetadataRepository drugFormularyMetadataRepository;

	private Long formularyId = 1L;
	private String payerId = "102";
	private Date date;
	private DrugFormularyMetadata drugFormularyMetadata;

	@BeforeEach
	void setupData() {
		date = generateDate();
		generateMockUserInfo();
		drugFormularyMetadata = generateDrugFormularyMetadata();
		Mockito.when(drugFormularyMetadataRepository.save(drugFormularyMetadata)).thenReturn(drugFormularyMetadata);
		Mockito.when(
				drugFormularyMetadataRepository.findByFormularyIdAndPayerIdAndIsDeleted(formularyId, payerId, false))
				.thenReturn(Optional.of(drugFormularyMetadata));
	}

	@Test
	@DisplayName("Success response")
	void successResponse() throws ParseException, AdminException {
		DrugFormularyMetaDataResponseModel response = drugFormularyService.getDrugFormularyMetadataDetails(formularyId);
		assertNotNull(response);
		assertEquals(formularyId, response.getFormularyId());
		assertEquals("Test", response.getFormularyName());
		assertEquals(payerId, response.getPayerId());
		assertEquals("Test", response.getCreatedBy());
		String expectedDateStr = getFormattedDate(null, date);
		assertEquals(expectedDateStr, getFormattedDate(response.getCreatedDate(), null));
		assertEquals(expectedDateStr, getFormattedDate(response.getUpdatedDate(), null));
	}

	@Test
	@DisplayName("Invalid response")
	void invalidResponse() throws ParseException {
		try {
			DrugFormularyMetaDataResponseModel response = drugFormularyService.getDrugFormularyMetadataDetails(3L);
			assertNull(response);
		} catch (AdminException e) {
			assertEquals("FormularyId is not found or exists.", e.getMessage());
		}
	}

	@Test
	@DisplayName("Failed response")
	void failedResponse() throws ParseException {
		DrugFormularyInvalidResponseModel response = drugFormularyService
				.populateInvalidFailedResponse(new Exception());
		assertNotNull(response);
		assertEquals("FAILED", response.getErrorCode());
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), response.getErrorDescription());
	}

	private DrugFormularyMetadata generateDrugFormularyMetadata() {
		return new DrugFormularyMetadata(formularyId, payerId, "Test", date, "Test", date, false, "NA");
	}

	public void generateMockUserInfo() {
		Map<String, Object> details = new HashMap<String, Object>();
		details.put("accId", payerId);
		details.put("accName", "accName");
		details.put("accCode", "accCode");
		details.put("username", "username");
		details.put("email", "email");
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(authentication.getPrincipal()).thenReturn(details);
	}

	private Date generateDate() {
		int year = 2023;
		int month = Calendar.AUGUST;
		int day = 9;
		int hour = 12;
		int minute = 30;
		int second = 0;
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day, hour, minute, second);
		return calendar.getTime();
	}

	private String getFormattedDate(String dateStr, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try {
			if (!StringUtils.isBlank(dateStr)) {
				Date parsedDate = sdf.parse(dateStr);
				return sdf.format(parsedDate);
			}
			return sdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
