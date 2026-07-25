package com.waseel.pbm.idfvalidationservice.service.screeningservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.idfvalidationservice.enums.ScreeningModules;
import com.waseel.pbm.idfvalidationservice.model.DrugList;
import com.waseel.pbm.idfvalidationservice.model.DssRequest;
import com.waseel.pbm.idfvalidationservice.model.DssResponse;
import com.waseel.pbm.idfvalidationservice.model.EnumTypes.RequestStatus;
import com.waseel.pbm.idfvalidationservice.model.EnumTypes.ServiceStatus;
import com.waseel.pbm.idfvalidationservice.model.Error;
import com.waseel.pbm.idfvalidationservice.model.Result;
import com.waseel.pbm.idfvalidationservice.repository.PayerModuleConfigurationRepository;
import com.waseel.pbm.idfvalidationservice.service.AuditService;

@Service
public class ScreeningService {

	@Autowired
	DrugToDiagnosisIndicationService drugToDiagnosisIndicationService;
	@Autowired
	DrugToGenderService drugToGenderService;
	@Autowired
	DrugToAgeService drugToAgeService;
	@Autowired
	ConcurrentMedicationService concurrentMedicationService;
	@Autowired
	AuditService auditService;
	@Autowired
	PayerModuleConfigurationRepository modulesConfigurationRepo;

	@Autowired
	QuantityLimitCheckSservice quantityLimitCheckSservice;

	public DssResponse controller(DssRequest dssRequest) {
		List<Integer> configuredModulesIds = modulesConfigurationRepo
				.findByPayerIdAndIsEnabled(dssRequest.getPayerId());
		List<Result> drugValidationResultList = new ArrayList<>();
		populateResultResponse(dssRequest, drugValidationResultList, configuredModulesIds);
		DssResponse dssResponse = populateValidationResponse(dssRequest, drugValidationResultList);
		auditService.saveAuditLogInMongoDbAndOracle(dssRequest, dssResponse);
		return dssResponse;
	}

	private void populateResultResponse(DssRequest dssRequest, List<Result> drugValidationResultList,
			List<Integer> configuredModulesIds) {
		List<String> ndcDrugCodeList = getNdcDrugCodeList(dssRequest.getDrugList());
		for (DrugList drug : dssRequest.getDrugList()) {
			List<Error> errors = new ArrayList<>();

			if (configuredModulesIds.contains(ScreeningModules.IDF_DRUG_TO_DISEASE_INTERACTION.value()))
				drugToDiagnosisIndicationService.validate(dssRequest, drug, errors);

			if (configuredModulesIds.contains(ScreeningModules.IDF_DRUG_TO_GENDER_INTERACTION.value()))
				drugToGenderService.validate(dssRequest, drug, errors);

			if (configuredModulesIds.contains(ScreeningModules.IDF_DRUG_TO_AGE_INTERACTION.value()))
				drugToAgeService.validate(dssRequest, drug, errors);

			if (configuredModulesIds.contains(ScreeningModules.IDF_QUANTITY_LIMIT_CHECK.value()))
				quantityLimitCheckSservice.validate(dssRequest, drug, errors);

			if (configuredModulesIds.contains(ScreeningModules.IDF_CONCURRENT_MEDICATION.value()))
				concurrentMedicationService.validate(drug, errors, ndcDrugCodeList);

			Result result = getResultData(errors, drug);
			drugValidationResultList.add(result);
		}
	}

	private List<String> getNdcDrugCodeList(List<DrugList> drugList) {
		return drugList.stream().map(DrugList::getNdcDrugCode).collect(Collectors.toList());
	}

	private Result getResultData(List<Error> errors, DrugList drug) {
		Result result = new Result();
		if (errors != null && !errors.isEmpty()) {
			result.setErrors(errors);
			result.setStatus(ServiceStatus.REJECTED.value());
		} else {
			result.setStatus(ServiceStatus.APPROVED.value());
		}
		result.setNdcDrugCode(drug.getNdcDrugCode());
		result.setDispensedQuantity(drug.getDispensedQuantity());
		result.setAmount(drug.getAmount());
		result.setDaysOfSupply(drug.getDaysOfSupply());
		return result;
	}

	private DssResponse populateValidationResponse(DssRequest dssRequest, List<Result> drugValidationResultList) {
		DssResponse response = new DssResponse();
		response.setRequestId(dssRequest.getRequestId());
		response.setHttpStatusCode(200);
		response.setResults(drugValidationResultList);
		response.setStatus(setRequestStatus(drugValidationResultList));
		return response;
	}

	private String setRequestStatus(List<Result> drugValidationResultList) {
		List<String> servicesStatusList = drugValidationResultList.stream().map(Result::getStatus)
				.collect(Collectors.toList());
		if (servicesStatusList.stream().distinct().count() <= 1)
			return servicesStatusList.get(0);
		return RequestStatus.PARTIAL_APPROVED.value();
	}
}
