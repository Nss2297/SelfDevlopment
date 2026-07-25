package com.waseel.pbm.payercustomizationservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.payercustomizationservice.enums.ScreeningModules;
import com.waseel.pbm.payercustomizationservice.model.DssResponse;
import com.waseel.pbm.payercustomizationservice.model.Error;
import com.waseel.pbm.payercustomizationservice.model.PCRequest;
import com.waseel.pbm.payercustomizationservice.model.Result;
import com.waseel.pbm.payercustomizationservice.repository.PayerModuleConfigurationRepository;
import com.waseel.pbm.payercustomizationservice.specification.CustomizationRequestSpecification;

@Service
public class PayerCustomizationService {

	@Autowired
	private DrugToDiagnosisService diagnosisService;

	@Autowired
	private QuantityLimitCheckService quantityLimitCheckService;

	@Autowired
	private GenderService genderService;

	@Autowired
	private DssResponseAdapter responseAdapter;

	@Autowired
	private PayerModuleConfigurationRepository payerModuleConfigurationRepo;

	@Autowired
	private AuditService auditService;

	@Autowired
	private DrugToAgeService ageService;

	@Autowired
	private DrugToDrugService drugToDrugService;

	@Autowired
	DuplicateTherapyService duplicateTherapyService;

	@Autowired
	CustomizationRequestSpecification customizationRequestSpecification;

	public DssResponse manageCustomization(PCRequest pcRequest) {
		DssResponse drugToDiagnosisResponse = null;
		DssResponse qlcResponse = null;
		DssResponse ageResponse = null;
		DssResponse genderResponse = null;
		DssResponse duplicateTherapyResponse = null;
		DssResponse drugToDrugResponse = null;
		DssResponse response;
		List<Integer> configuredModuleIds = payerModuleConfigurationRepo
				.findByPayerIdAndIsEnabled(pcRequest.getDssRequest().getPayerId());
		List<Integer> enabledConfiguredModulesIds = new ArrayList<>();
		if (configuredModuleIds.contains(ScreeningModules.PC_DRUGTODIAGNOSIS.value())) {
			enabledConfiguredModulesIds.add(ScreeningModules.PC_DRUGTODIAGNOSIS.value());
			drugToDiagnosisResponse = diagnosisService.manageCustomizationDrugToDiagnosis(setActualRequest(pcRequest));
		}
		if (configuredModuleIds.contains(ScreeningModules.PC_QUANTITY_LIMIT_CHECK.value())) {
			enabledConfiguredModulesIds.add(ScreeningModules.PC_QUANTITY_LIMIT_CHECK.value());
			qlcResponse = quantityLimitCheckService.manageCustomizationQuantityLimitCheck(setActualRequest(pcRequest));
		}
		if (configuredModuleIds.contains(ScreeningModules.PC_AGE.value())) {
			enabledConfiguredModulesIds.add(ScreeningModules.PC_AGE.value());
			ageResponse = ageService.manageCustomizationAge(setActualRequest(pcRequest));
		}
		if (configuredModuleIds.contains(ScreeningModules.PC_GENDER.value())) {
			enabledConfiguredModulesIds.add(ScreeningModules.PC_GENDER.value());
			genderResponse = genderService.manageCustomizationGender(setActualRequest(pcRequest));
		}

		if (configuredModuleIds.contains(ScreeningModules.PC_DRUG_TO_DRUG.value())) {
			enabledConfiguredModulesIds.add(ScreeningModules.PC_DRUG_TO_DRUG.value());
			drugToDrugResponse = drugToDrugService.manageCustomizationDrugToDrug(setActualRequest(pcRequest));
		}

		if (configuredModuleIds.contains(ScreeningModules.PC_DUPLICATE_THERAPY.value())) {
			enabledConfiguredModulesIds.add(ScreeningModules.PC_DUPLICATE_THERAPY.value());
			duplicateTherapyResponse = duplicateTherapyService
					.manageCustomizationDuplicateTherapy(setActualRequest(pcRequest));
		}
		if (drugToDiagnosisResponse != null || qlcResponse != null || ageResponse != null || genderResponse != null
				|| drugToDrugResponse != null || duplicateTherapyResponse != null) {
			response = responseAdapter.combineResponse(drugToDiagnosisResponse, qlcResponse, ageResponse,
					genderResponse, drugToDrugResponse, duplicateTherapyResponse, pcRequest.getDssRequest());
		} else {
			response = pcRequest.getDssResponse();
		}
		auditService.saveAuditLogInMongoDbAndOracle(pcRequest, response, enabledConfiguredModulesIds);
		return response;
	}

	private PCRequest setActualRequest(PCRequest pcRequest) {
		PCRequest newRequest = new PCRequest();
		List<Result> resultList = new ArrayList<>();
		pcRequest.getDssResponse().getResults().forEach(r -> {
			List<Error> errorList = new ArrayList<>();
			List<Error> errors = r.getErrors();
			if (errors != null) {
				errors.forEach(e -> {
					Error error = new Error();
					error.setCode(e.getCode());
					error.setDescription(e.getDescription());
					errorList.add(error);
				});
			}
			resultList.add(new Result(r.getNdcDrugCode(), r.getScientificCode(), r.getDispensedQuantity(),
					r.getAmount(), r.getDaysOfSupply(), r.getStatus(), errorList));
		});
		newRequest.setDssResponse(new DssResponse(pcRequest.getDssResponse().getRequestId(),
				pcRequest.getDssResponse().getStatus(), pcRequest.getDssResponse().getErrors(), resultList,
				pcRequest.getDssResponse().getHttpStatusCode(), pcRequest.getDssResponse().getHttpStatusDescription(),
				pcRequest.getDssResponse().getTransactionLogId()));
		newRequest.setDssRequest(pcRequest.getDssRequest());
		return newRequest;
	}
}
