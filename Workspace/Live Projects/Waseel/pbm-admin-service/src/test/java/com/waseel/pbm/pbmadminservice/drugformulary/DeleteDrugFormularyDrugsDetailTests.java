package com.waseel.pbm.pbmadminservice.drugformulary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyDetails;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyMetadata;
import com.waseel.pbm.pbmadminservice.repository.businessrules.AuditLogRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugFormularyDetailsRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugFormularyMetadataRepository;
import com.waseel.pbm.pbmadminservice.service.DrugFormularyService;

@SpringBootTest
@ActiveProfiles({ "test" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeleteDrugFormularyDrugsDetailTests {
	@Autowired
	private DrugFormularyService drugFormularyService;

	@MockBean
	private DrugFormularyDetailsRepository drugFormularyDetailsRepository;

	@MockBean
	private DrugFormularyMetadataRepository drugFormularyMetadataRepository;

	@MockBean
	private AuditLogRepository auditLogRepository;

	private Long drugFormularyDetailsId = 1L;
	private String payerId = "102";
	private Date date = new Date();
	private Long formularyId = 1L;
	private DrugFormularyDetails drugFormularyDetails;
	private DrugFormularyMetadata drugFormularyMetadata;

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
	}

	@BeforeEach
	public void setupData() {
		drugFormularyDetails = generateDrugFormularyDetails();
		drugFormularyMetadata = generateDrugFormularyMetadata();
		Mockito.when(
				drugFormularyMetadataRepository.findByFormularyIdAndPayerIdAndIsDeleted(formularyId, payerId, false))
				.thenReturn(Optional.of(drugFormularyMetadata));
		Mockito.when(drugFormularyDetailsRepository.save(drugFormularyDetails)).thenReturn(drugFormularyDetails);
		Mockito.when(
				drugFormularyDetailsRepository.findByDrugFormularyDetailsIdAndIsDeleted(drugFormularyDetailsId, false))
				.thenReturn(Optional.of(drugFormularyDetails));
	}

	@Test
	@DisplayName("DrugFormularyDetailsId Not Exists")
	void drugFormularyDetailsIdNotExists() {
		try {
			drugFormularyService.deleteDrugFormularyDrugDetails(2L);
		} catch (AdminException e) {
			assertEquals("DrugFormularyDetailsId is not found or exists.", e.getMessage());
		}
	}

	@Test
	@DisplayName("Success response")
	void successResponse() throws ParseException, AdminException {
		drugFormularyService.deleteDrugFormularyDrugDetails(drugFormularyDetailsId);
		assertEquals("TEST", drugFormularyDetails.getDeletedBy());
		assertThat(drugFormularyDetails.getIsDeleted()).isTrue();
	}

	private DrugFormularyMetadata generateDrugFormularyMetadata() {
		return new DrugFormularyMetadata(formularyId, payerId, "Test", date, "Test", date, false, "NA");
	}

	private DrugFormularyDetails generateDrugFormularyDetails() {
		return new DrugFormularyDetails(drugFormularyDetailsId, 1L, 10001L, "129-334-10", "test", "test", "test",
				new BigDecimal(0), date);
	}

	public void generateMockUserInfo() {
		Map<String, Object> details = new HashMap<String, Object>();
		details.put("accId", payerId);
		details.put("accName", "TEST");
		details.put("accCode", "accCode");
		details.put("username", "userName");
		details.put("email", "email");
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(authentication.getPrincipal()).thenReturn(details);
	}
}
