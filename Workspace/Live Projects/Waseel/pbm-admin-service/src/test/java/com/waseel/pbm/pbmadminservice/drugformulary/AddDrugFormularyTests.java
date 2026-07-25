package com.waseel.pbm.pbmadminservice.drugformulary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyDrugDetailsModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.PolicyMetaDataModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyMetadata;
import com.waseel.pbm.pbmadminservice.persist.businessrules.PolicyInformation;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugService;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugFormularyDetailsRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugFormularyMetadataRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugFormularyPolicyAssociationRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.PolicyInformationRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceRepository;
import com.waseel.pbm.pbmadminservice.service.DrugFormularyService;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles({ "test" })
public class AddDrugFormularyTests {

	private String payerId = "102";
	private DrugFormularyRequestModel requestModel;

	@Autowired
	private DrugFormularyService drugFormularyService;

	@MockBean
	private DrugFormularyMetadataRepository drugFormularyMetadataRepository;
	@MockBean
	private DrugServiceRepository drugServiceRepository;
	@MockBean
	private DrugFormularyDetailsRepository drugFormularyDetailsRepository;
	@MockBean
	private PolicyInformationRepository policyInformationRepository;
	@MockBean
	private DrugFormularyPolicyAssociationRepository drugFormularyPolicyAssociationRepository;

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
	}

	@BeforeEach
	public void setUpData() {
		payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		requestModel = new DrugFormularyRequestModel();
		List<DrugFormularyDrugDetailsModel> modelList = new ArrayList<>();
		modelList.add(new DrugFormularyDrugDetailsModel());
		requestModel.setDrugDetails(modelList);
		List<PolicyMetaDataModel> detailsModels = new ArrayList<>();
		detailsModels.add(new PolicyMetaDataModel());
		requestModel.setPolicyDetails(detailsModels);
	}

	@Test
	@DisplayName("Success Add DrugFormulary Details")
	void successAddDrugFormularyDetails() {
		Mockito.when(drugFormularyMetadataRepository.save(Mockito.any())).thenReturn(new DrugFormularyMetadata());
		Mockito.when(drugServiceRepository.findByOtherCodesValueAndDrugListId(Mockito.any(), Mockito.anyLong()))
				.thenReturn(Optional.of(new DrugService()));
		Mockito.when(drugFormularyDetailsRepository.saveAll(Mockito.any())).thenReturn(new ArrayList<>());
		Mockito.when(policyInformationRepository.findByPolicyNumber(Mockito.any()))
				.thenReturn(Optional.of(new PolicyInformation()));
		Mockito.when(drugFormularyPolicyAssociationRepository.saveAll(Mockito.any())).thenReturn(new ArrayList<>());
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
