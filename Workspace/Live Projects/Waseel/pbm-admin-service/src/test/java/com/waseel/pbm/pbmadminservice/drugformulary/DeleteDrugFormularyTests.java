package com.waseel.pbm.pbmadminservice.drugformulary;

import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.persist.businessrules.AuditLog;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyDetails;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyMetadata;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyPolicyAssociation;
import com.waseel.pbm.pbmadminservice.repository.businessrules.AuditLogRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugFormularyDetailsRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugFormularyMetadataRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugFormularyPolicyAssociationRepository;
import com.waseel.pbm.pbmadminservice.service.DrugFormularyService;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles({ "test" })
public class DeleteDrugFormularyTests {

    private String payerId = "102";
    private Long formularyId = 1L;
    private List<DrugFormularyDetails> drugFormularyDetails;
    private List<DrugFormularyPolicyAssociation> drugFormularyPolicyAssociations;

    @Autowired
    private DrugFormularyService drugFormularyService;

    @MockBean
    private DrugFormularyMetadataRepository drugFormularyMetadataRepository;
    @MockBean
    private DrugFormularyDetailsRepository drugFormularyDetailsRepository;
    @MockBean
    private DrugFormularyPolicyAssociationRepository drugFormularyPolicyAssociationRepository;
    @MockBean
    private AuditLogRepository auditLogRepository;

    @BeforeAll
    public void setUpCommonData() {
        generateMockUserInfo();
    }

    @BeforeEach
    public void setUpData() {
        payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    @DisplayName("Formulary Id Not Exists")
    void formularyIdNotExists() {
        try {
            Mockito.when(drugFormularyMetadataRepository.findByFormularyIdAndPayerIdAndIsDeleted(
                    formularyId, payerId, false)).thenReturn(Optional.empty());
            drugFormularyService.deleteDrugFormulary(formularyId);
        } catch (AdminException e) {
            assertEquals(e.getMessage(), "FormularyId is not found or exists.");
        }
    }

    @Test
    @DisplayName("Success delete drug formulary")
    void successDeleteFormulary() {
        try {
            Mockito.when(drugFormularyMetadataRepository.findByFormularyIdAndPayerIdAndIsDeleted(
                    formularyId, payerId, false))
                    .thenReturn(Optional.of(new DrugFormularyMetadata(1l, "102", "formularyName", new Date(),
                            "createdBy", new Date(), false, null)));
            Mockito.when(drugFormularyDetailsRepository.findByFormularyIdAndIsDeleted(formularyId, false))
                    .thenReturn(drugFormularyDetails);
            Mockito.when(drugFormularyPolicyAssociationRepository.findByFormularyId(formularyId))
                    .thenReturn(drugFormularyPolicyAssociations);
            Mockito.when(drugFormularyDetailsRepository.saveAll(Mockito.any()))
                    .thenReturn(new ArrayList<>());
            Mockito.when(drugFormularyPolicyAssociationRepository.saveAll(Mockito.any()))
                    .thenReturn(new ArrayList<>());
            Mockito.when(drugFormularyMetadataRepository.save(Mockito.any()))
                    .thenReturn(new DrugFormularyMetadata());
            Mockito.when(auditLogRepository.save(Mockito.any())).thenReturn(new AuditLog());
            drugFormularyService.deleteDrugFormulary(formularyId);
        } catch (AdminException e) {
            assertEquals(e.getMessage(), "FormularyId is not found or exists.");
        }
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
}
