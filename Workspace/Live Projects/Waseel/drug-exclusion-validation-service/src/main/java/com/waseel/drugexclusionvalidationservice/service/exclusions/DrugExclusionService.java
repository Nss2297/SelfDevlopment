package com.waseel.drugexclusionvalidationservice.service.exclusions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.drugexclusionvalidationservice.model.DrugExclusionModel;
import com.waseel.drugexclusionvalidationservice.model.DrugExclusionRequestModel;
import com.waseel.drugexclusionvalidationservice.model.DrugExclusionResponseModel;
import com.waseel.drugexclusionvalidationservice.model.enums.ModuleConfiguration;
import com.waseel.drugexclusionvalidationservice.model.enums.RequestType;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.TransactionLog;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.ModuleConfigurationRepository;
import com.waseel.drugexclusionvalidationservice.service.SessionService;
import com.waseel.drugexclusionvalidationservice.service.TransactionLogService;

@Service
public class DrugExclusionService {

	@Autowired
	private SpecialityExclusionService specialityExclusionService;
	@Autowired
	private TransactionLogService transactionLogService;
	@Autowired
	private SessionService sessionService;
	@Autowired
	private CombineResponseService combineResponseService;
	@Autowired
	NetworkExclusionService networkExclusionService;
	@Autowired
	private ModuleConfigurationRepository moduleConfigurationRepository;

	@Autowired
	private HighCostDrugsExclusionService highCostDrugsExclusionService;

	@Autowired
	private ProviderExclusionService providerExclusionService;

	public DrugExclusionResponseModel checkDrugExclusion(DrugExclusionRequestModel requestModel,
			ContentCachingRequestWrapper requestWrapper) {
		addInTransactionLog(requestModel, requestWrapper);
		List<Long> moduleConfigurations = moduleConfigurationRepository.findByProviderIdAndPayerIdAndIsEnabled(
				Long.valueOf(requestModel.getPayerId()), Long.valueOf(requestModel.getProviderId()));
		DrugExclusionResponseModel drugExclusionResponseModel = null;
		if (moduleConfigurations.contains(ModuleConfiguration.EXCLUSION_VALIDATION.value())) {
			drugExclusionResponseModel = new DrugExclusionResponseModel();
			String requestId = requestModel.getRequestId();
			drugExclusionResponseModel.setRequestId(requestId);
			List<DrugExclusionModel> specialityResponseList = null;
			List<DrugExclusionModel> providerExclusionCheckResposne = null;
			List<DrugExclusionModel> highCostDrugsResponseList = null;
			List<DrugExclusionModel> networkResponseList = null;
			if (moduleConfigurations.contains(ModuleConfiguration.SPECIALTY_EXCLUSION.value())) {
				specialityResponseList = specialityExclusionService.checkSpecialityExclusion(requestModel);
			}
			if (moduleConfigurations.contains(ModuleConfiguration.PROVIDERS_EXCLUSION.value())) {
				providerExclusionCheckResposne = providerExclusionService.providerExclusionCheckForDrugs(requestModel);
			}
			if (moduleConfigurations.contains(ModuleConfiguration.HIGH_COST_DRUGS_EXCLUSION.value())) {
				highCostDrugsResponseList = highCostDrugsExclusionService.checkHighCostDrugsExclusion(requestModel);
			}
			if (moduleConfigurations.contains(ModuleConfiguration.NETWORKS_EXCLUSION.value())) {
				networkResponseList = networkExclusionService.checkNetworkExclusion(requestModel);
			}
			combineResponseService.combineResponse(drugExclusionResponseModel, requestModel.getDrugList(),
					specialityResponseList, highCostDrugsResponseList, providerExclusionCheckResposne,
					networkResponseList);
		}
		return drugExclusionResponseModel;
	}

	private void addInTransactionLog(DrugExclusionRequestModel requestModel,
			ContentCachingRequestWrapper requestWrapper) {
		TransactionLog transactionLog = transactionLogService.addDataInTransactionLog(RequestType.DRUG_EXCLUSION,
				requestModel.getRequestId(), requestModel.getPayerId(), requestModel.getProviderId());
		if (transactionLog != null) {
			sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
		}
	}
}
