package com.waseel.pbm.pbmadminservice.drugformulary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Date;
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
import org.springframework.test.context.ActiveProfiles;

import com.waseel.pbm.pbmadminservice.enums.AuditUpdatedType;
import com.waseel.pbm.pbmadminservice.enums.DrugFormularyMessage;
import com.waseel.pbm.pbmadminservice.enums.EntitiesName;
import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.persist.businessrules.AuditLog;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyMetadata;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyPolicyAssociation;
import com.waseel.pbm.pbmadminservice.repository.businessrules.AuditLogRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugFormularyMetadataRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugFormularyPolicyAssociationRepository;
import com.waseel.pbm.pbmadminservice.service.DrugFormularyService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class DeleteDrugFormularyPolicyAssociationsTests {

	@Autowired
	private DrugFormularyService drugFormularyService;

	@MockBean
	private DrugFormularyPolicyAssociationRepository drugFormularyPolicyAssociationRepository;

	@MockBean
	private AuditLogRepository auditLogRepository;

	@MockBean
	private DrugFormularyMetadataRepository drugFormularyMetadataRepository;

	private String payerId = "102";
	private final Long drugFormularyAssociationId = 1L;
	private final Long formularyId = 2L;
	private final Long policyInformationId = 3L;
	private final Long policyClassId = 4L;
	private final Long memberPolicyAssociationId = 5L;
	private final Long auditLogId = 1L;
	private final String updateBy = "pbm-admin-service";
	private final Date updateDate = new Date();
	private final String entityName = EntitiesName.DRUG_FORMULARY_POLICY_ASSOCIATION.value();
	private final String updateType = AuditUpdatedType.UPDATE.name();
	private DrugFormularyPolicyAssociation drugFormularyPolicyAssociation = null;
	private DrugFormularyPolicyAssociation disabledDrugFormularyPolicyAssociation = null;
	private AuditLog auditLog = null;
	private DrugFormularyMetadata drugFormularyMetadata;

	@BeforeAll
	void commonDataForDeleteDrugFormularyPolicyAssociationsTests() {
		drugFormularyMetadata = generateDrugFormularyMetadata();
		drugFormularyPolicyAssociation = populateDrugFormularyPolicyAssociation();
		auditLog = populateAuditLog();
		disabledDrugFormularyPolicyAssociation = populateDisabledDrugFormularyPolicyAssociation();
	}

	@BeforeEach
	void commonDataBeforeEachUnitTest() {
		Mockito.when(auditLogRepository.save(Mockito.any())).thenReturn(auditLog);

	}

	@Order(1)
	@Test
	@DisplayName("Delete drug-formulary and policy-association successfully.")
	void successfullyDeleteDrugFormularyAndPolicyAssociation() throws AdminException {
		Mockito.when(
				drugFormularyMetadataRepository.findByFormularyIdAndPayerIdAndIsDeleted(formularyId, payerId, false))
				.thenReturn(Optional.of(drugFormularyMetadata));
		Mockito.when(drugFormularyPolicyAssociationRepository
				.findByDrugFormularyAssociationIdAndIsEnabled(drugFormularyAssociationId, true))
				.thenReturn(Optional.of(drugFormularyPolicyAssociation));
		Mockito.when(drugFormularyPolicyAssociationRepository
				.findByDrugFormularyAssociationIdAndIsEnabled(drugFormularyAssociationId, false))
				.thenReturn(Optional.of(disabledDrugFormularyPolicyAssociation));
		drugFormularyService.deleteDrugFormularyAndPolicyAssociation(drugFormularyAssociationId);
		verify(drugFormularyPolicyAssociationRepository, times(1)).deleteById(drugFormularyAssociationId);
	}

	@Order(2)
	@Test
	@DisplayName("Invalid drugFormularyAssociationId.")
	void invalidDrugFormularyAssociationId() {
		Mockito.when(drugFormularyPolicyAssociationRepository
				.findByDrugFormularyAssociationIdAndIsEnabled(Mockito.anyLong(), Mockito.anyBoolean()))
				.thenReturn(Optional.empty());
		try {
			drugFormularyService.deleteDrugFormularyAndPolicyAssociation(drugFormularyAssociationId);
		} catch (AdminException adminException) {
			String error = adminException.getMessage();
			assertNotNull(error);
			assertEquals(DrugFormularyMessage.INVALID_DRUG_FORMULARY_ASSOCIATION_ID.value(), error);
		}
	}

	@Order(3)
	@Test
	@DisplayName("Failed to delete drug-formulary and policy-association.")
	void failedDeleteDrugFormularyAndPolicyAssociation() {
		Mockito.when(drugFormularyPolicyAssociationRepository
				.findByDrugFormularyAssociationIdAndIsEnabled(drugFormularyAssociationId, true))
				.thenReturn(Optional.of(drugFormularyPolicyAssociation));
		Mockito.when(drugFormularyPolicyAssociationRepository
				.findByDrugFormularyAssociationIdAndIsEnabled(drugFormularyAssociationId, false))
				.thenReturn(Optional.empty());
		try {
			drugFormularyService.deleteDrugFormularyAndPolicyAssociation(drugFormularyAssociationId);
		} catch (AdminException adminException) {
			String error = adminException.getMessage();
			assertNotNull(error);
			assertEquals(DrugFormularyMessage.FAILED_TO_DELETE_DRUG_FORMULARY_ASSOCIATION.value(), error);
			verify(drugFormularyPolicyAssociationRepository, times(1)).deleteById(drugFormularyAssociationId);
		}
	}

	private DrugFormularyPolicyAssociation populateDrugFormularyPolicyAssociation() {
		return new DrugFormularyPolicyAssociation(drugFormularyAssociationId, formularyId, policyInformationId,
				policyClassId, memberPolicyAssociationId, true);
	}

	private DrugFormularyPolicyAssociation populateDisabledDrugFormularyPolicyAssociation() {
		return new DrugFormularyPolicyAssociation(drugFormularyAssociationId, formularyId, policyInformationId,
				policyClassId, memberPolicyAssociationId, false);
	}

	private AuditLog populateAuditLog() {
		return new AuditLog(auditLogId, updateBy, updateDate, drugFormularyAssociationId, entityName, updateType, null);
	}

	private DrugFormularyMetadata generateDrugFormularyMetadata() {
		return new DrugFormularyMetadata(formularyId, payerId, "Test", new Date(), "Test", new Date(), false, "NA");
	}
}
