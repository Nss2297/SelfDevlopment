package com.waseel.pbm.payercustomizationservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import com.waseel.pbm.payercustomizationservice.enums.CustomizationRequestMetaDataStatus;
import com.waseel.pbm.payercustomizationservice.enums.EntityNames;
import com.waseel.pbm.payercustomizationservice.model.DeleteResponseModel;
import com.waseel.pbm.payercustomizationservice.persist.CustomizationRequestMetadata;
import com.waseel.pbm.payercustomizationservice.persist.CustomizationRequestsAudit;
import com.waseel.pbm.payercustomizationservice.repository.CustomizationRequestDetailsRepository;
import com.waseel.pbm.payercustomizationservice.repository.CustomizationRequestMetadataRepository;
import com.waseel.pbm.payercustomizationservice.repository.CustomizationRequestsAuditRepository;
import com.waseel.pbm.payercustomizationservice.service.CustomizationListService;
import com.waseel.pbm.payercustomizationservice.util.UserInfoUtil;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class DeleteCustomizationRequestTests {

	@Autowired
	private CustomizationListService customizationListService;

	@MockBean
	private CustomizationRequestMetadataRepository customizationRequestMetadataRepository;

	@MockBean
	private CustomizationRequestsAuditRepository customizationRequestsAuditRepository;

	@MockBean
	private CustomizationRequestDetailsRepository customizationRequestDetailsRepository;

	private Optional<CustomizationRequestMetadata> customizationRequestMetadata = null;
	private CustomizationRequestsAudit customizationRequestsAudit = null;
	private CustomizationRequestMetadata deletedCustomizationRequestMetadata = null;
	private final Long customizationRequestId = (long) 36;
	private final Date date = new Date();
	private final String payerId = "102";
	private final String drugName = "CETRO 10MG F.C TAB";
	private final String drugCode = "1-809-14";

	@BeforeAll
	void prepareCommonData() {
		generateMockUserInfo();
		customizationRequestMetadata = populateCustomizationRequestMetadataTable();
		deletedCustomizationRequestMetadata = populateDeletedCustomizationRequestMetadataTable();
		customizationRequestsAudit = populateCustomizationRequestsAudit();
	}

	@BeforeEach
	void prepareCommonDataBeforeEachUnitTest() {
		Mockito.when(customizationRequestMetadataRepository.save(Mockito.any()))
				.thenReturn(deletedCustomizationRequestMetadata);
		Mockito.when(customizationRequestsAuditRepository.save(Mockito.any())).thenReturn(customizationRequestsAudit);
	}

	@Order(1)
	@Test
	@DisplayName("Delete customization request")
	void deletePCRequestForAge() {
		mockNewCustomizationRequest();
		DeleteResponseModel deleteResponseModel = customizationListService
				.deleteCustomizationRequest(customizationRequestId);
		assertNotNull(deleteResponseModel);
		assertNotNull(deleteResponseModel.getCustomizationRequestId());
	}

	private Optional<CustomizationRequestMetadata> populateCustomizationRequestMetadataTable() {
		return Optional.of(new CustomizationRequestMetadata(customizationRequestId, drugCode, drugName, false, date, "",
				payerId, "Medication 1-809-14 is not indicated with diagnosis code M75.3",
				CustomizationRequestMetaDataStatus.PC_PENDING_REQUEST.value()));

	}

	private CustomizationRequestMetadata populateDeletedCustomizationRequestMetadataTable() {
		return new CustomizationRequestMetadata(customizationRequestId, drugCode, drugName, true, date, "", payerId,
				"Medication 1-809-14 is not indicated with diagnosis code M75.3",
				CustomizationRequestMetaDataStatus.PC_PENDING_REQUEST.value());

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

	private CustomizationRequestsAudit populateCustomizationRequestsAudit() {
		return new CustomizationRequestsAudit(customizationRequestId,
				UserInfoUtil.getUsername(SecurityContextHolder.getContext().getAuthentication()),
				customizationRequestId, EntityNames.CUSTOMIZATION_REQUEST_METADATA.name(), "DELETE", date, "",
				UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
	}

	private void mockNewCustomizationRequest() {
		Mockito.when(customizationRequestMetadataRepository
				.findByCustomizationRequestsIdAndIsDeleted(customizationRequestId, false))
				.thenReturn(customizationRequestMetadata);
	}
}
