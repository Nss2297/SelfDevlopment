package com.waseel.pbm.fdbvalidationservice.service.screeningservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.fdbvalidationservice.enums.ScreeningModules;
import com.waseel.pbm.fdbvalidationservice.model.DssRequest;
import com.waseel.pbm.fdbvalidationservice.model.DssResponse;
import com.waseel.pbm.fdbvalidationservice.model.FdbRequest;
import com.waseel.pbm.fdbvalidationservice.model.FdbResponse;
import com.waseel.pbm.fdbvalidationservice.repository.mdss.PayerModuleConfigurationRepository;
import com.waseel.pbm.fdbvalidationservice.service.manpulationservice.DMLService;
import com.waseel.pbm.fdbvalidationservice.service.manpulationservice.DssResponseAdapter;
import com.waseel.pbm.fdbvalidationservice.service.manpulationservice.FdbRequestComposer;

@Service
public class ScreeningService {

	@Autowired
	PayerModuleConfigurationRepository modulesConfigurationRepo;
	@Autowired
	DrugToDrugInteractionService drugToDrugInteractionService;
	@Autowired
	DrugToDiseaseService drugToDiseaseService;
	@Autowired
	DrugToGenderInteractionService drugToGenderInteractionService;
	@Autowired
	DrugToAgeInteractionService drugToAgeInteractionService;
	@Autowired
	QuantityLimitCheckService quantityLimitCheckService;
	@Autowired
	FdbRequestComposer composer;
	@Autowired
	DssResponseAdapter adapter;
	@Autowired
	DuplicateTherapyService duplicatedTherapyService;

	@Autowired
	private DMLService dmlService;

	public DssResponse controlScreeningProcess(DssRequest dssRequest) throws Exception {

		FdbResponse drugToDrugInteractionResponse = null;
		FdbResponse drugToDiseaseResponse = null;
		FdbResponse drugToGenderInteractionResponse = null;
		FdbResponse drugToAgeInteractionResponse = null;
		FdbResponse quantityLimitCheckResponse = null;
		FdbResponse duplicatedTherapyResponse = null;
		DssResponse response = null;

		List<Integer> configuredModulesIds = modulesConfigurationRepo
				.findByPayerIdAndIsEnabled(dssRequest.getPayerId());
		List<Integer> enabledConfiguredModulesIds = new ArrayList<>();

		if (!configuredModulesIds.isEmpty()) {

			FdbRequest request = composer.compose(dssRequest);

			if (request.getDrugList() != null && !request.getDrugList().isEmpty()) {

				if (configuredModulesIds.contains(ScreeningModules.FDB_DRUG_TO_DRUG_INTERACTION.value())) {
					enabledConfiguredModulesIds.add(ScreeningModules.FDB_DRUG_TO_DRUG_INTERACTION.value());
					drugToDrugInteractionResponse = drugToDrugInteractionService.validate(request);
				}

				if (configuredModulesIds.contains(ScreeningModules.FDB_DRUG_TO_DISEASE_INTERACTION.value())) {
					enabledConfiguredModulesIds.add(ScreeningModules.FDB_DRUG_TO_DISEASE_INTERACTION.value());
					drugToDiseaseResponse = drugToDiseaseService.validate(request);
				}

				if (configuredModulesIds.contains(ScreeningModules.FDB_DRUG_TO_GENDER_INTERACTION.value())) {
					enabledConfiguredModulesIds.add(ScreeningModules.FDB_DRUG_TO_GENDER_INTERACTION.value());
					drugToGenderInteractionResponse = drugToGenderInteractionService.validate(request);
				}
				if (configuredModulesIds.contains(ScreeningModules.FDB_DRUG_TO_AGE_INTERACTION.value())) {
					enabledConfiguredModulesIds.add(ScreeningModules.FDB_DRUG_TO_AGE_INTERACTION.value());
					drugToAgeInteractionResponse = drugToAgeInteractionService.validate(request);
				}
				if (configuredModulesIds.contains(ScreeningModules.FDB_DUPLICATE_THERAPY.value())) {
					enabledConfiguredModulesIds.add(ScreeningModules.FDB_DUPLICATE_THERAPY.value());
					duplicatedTherapyResponse = duplicatedTherapyService.validate(request);
				}
				if (configuredModulesIds.contains(ScreeningModules.FDB_QUANTITY_LIMIT_CHECK.value())) {
					enabledConfiguredModulesIds.add(ScreeningModules.FDB_QUANTITY_LIMIT_CHECK.value());
					quantityLimitCheckResponse = quantityLimitCheckService.validate(request);

				}
			}

			response = adapter.combine(dssRequest, request, drugToDrugInteractionResponse, drugToDiseaseResponse,
					drugToGenderInteractionResponse, drugToAgeInteractionResponse, duplicatedTherapyResponse,
					quantityLimitCheckResponse);
		}
		dmlService.saveAuditLogInMongoDbAndOracle(dssRequest, response, enabledConfiguredModulesIds);
		return response;
	}
}
