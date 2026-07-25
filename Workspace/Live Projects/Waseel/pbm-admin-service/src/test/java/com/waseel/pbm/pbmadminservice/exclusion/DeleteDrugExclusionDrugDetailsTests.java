package com.waseel.pbm.pbmadminservice.exclusion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
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
import com.waseel.pbm.pbmadminservice.persist.businessrules.AuditLog;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionDetails;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionMetadata;
import com.waseel.pbm.pbmadminservice.repository.businessrules.AuditLogRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugExclusionDetailsRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugExclusionMetadataRepository;
import com.waseel.pbm.pbmadminservice.service.DrugExclusionService;

@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteDrugExclusionDrugDetailsTests {

	private Long payerId = 102L;
	private Long drugExclusionDetailsId = 1l;
	private DrugExclusionDetails drugExclusionDetails;
	private Date date = new Date();

	@MockBean
	private DrugExclusionMetadataRepository drugExclusionMetadataRepository;
	@MockBean
	private DrugExclusionDetailsRepository drugExclusionDetailsRepository;
	@MockBean
	private AuditLogRepository auditLogRepository;

	@Autowired
	private DrugExclusionService drugExclusionService;

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		drugExclusionDetails = generateDrugExclusionDetails();
	}

	@Test
	@DisplayName("Not exists DrugExclusionDetailsId")
	void drugExclusionDetailsIdNotExists() {
		try {
			Mockito.when(drugExclusionDetailsRepository.findByDrugExclusionDetailsIdAndIsDeleted(Mockito.anyLong(),
					Mockito.anyBoolean())).thenReturn(Optional.empty());
			drugExclusionService.deleteDrugExclusionDrugDetails(drugExclusionDetailsId);
		} catch (AdminException e) {
			assertEquals("DrugExclusionDetailsId is not found or exists.", e.getMessage());
		}
	}

	@Test
	@DisplayName("Delete successfully")
	void successDeleteDrugExclusionDetails() {
		try {
			Mockito.when(drugExclusionDetailsRepository.findByDrugExclusionDetailsIdAndIsDeleted(Mockito.anyLong(),
					Mockito.anyBoolean())).thenReturn(Optional.of(new DrugExclusionDetails()));
			Mockito.when(drugExclusionDetailsRepository.save(Mockito.any())).thenReturn(drugExclusionDetails);
			Mockito.when(drugExclusionMetadataRepository.findByExclusionIdAndPayerIdAndIsDeleted(Mockito.anyLong(),
					Mockito.anyLong(), Mockito.anyBoolean())).thenReturn(Optional.of(new DrugExclusionMetadata()));
			Mockito.when(drugExclusionMetadataRepository.save(Mockito.any())).thenReturn(new DrugExclusionMetadata());
			Mockito.when(auditLogRepository.save(Mockito.any())).thenReturn(new AuditLog());

			drugExclusionService.deleteDrugExclusionDrugDetails(drugExclusionDetailsId);
		} catch (AdminException e) {
			assertEquals("Drug details already exists.", e.getMessage());
		}
	}

	private DrugExclusionDetails generateDrugExclusionDetails() {
		return new DrugExclusionDetails(1l, 1l, "55-55-22", "Test", "Test", "Test", new BigDecimal(2), date);
	}

	public void generateMockUserInfo() {
		Map<String, Object> details = new HashMap<String, Object>();
		details.put("accId", payerId.toString());
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
