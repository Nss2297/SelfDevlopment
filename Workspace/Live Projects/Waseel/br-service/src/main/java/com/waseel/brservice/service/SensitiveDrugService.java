package com.waseel.brservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.brservice.model.DrugList;
import com.waseel.brservice.model.SensitiveDrugRequestModel;
import com.waseel.brservice.model.SensitiveDrugResponseModel;
import com.waseel.brservice.model.enums.ModuleConfiguration;
import com.waseel.brservice.model.enums.RequestType;
import com.waseel.brservice.model.enums.ServiceStatus;
import com.waseel.brservice.model.enums.commonDenials;
import com.waseel.brservice.persist.businessrules.CommonDenials;
import com.waseel.brservice.persist.businessrules.SensitiveDrugDetails;
import com.waseel.brservice.persist.businessrules.TransactionLog;
import com.waseel.brservice.repository.businessrules.CommonDenialsRepository;
import com.waseel.brservice.repository.businessrules.ModuleConfigurationRepository;
import com.waseel.brservice.repository.businessrules.SensitiveDrugDetailsRepository;

@Service
public class SensitiveDrugService {

	@Autowired
	private SensitiveDrugDetailsRepository sensitiveDrugDetailsRepository;

	@Autowired
	private CommonDenialsRepository commonDenialsRepository;

	@Autowired
	private ModuleConfigurationRepository moduleConfigurationRepository;

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private SessionService sessionService;

	public SensitiveDrugResponseModel checkSensitiveDrug(SensitiveDrugRequestModel sdReqModel,
			ContentCachingRequestWrapper requestWrapper) {
		addInTransactionLog(sdReqModel.getRequestId(), sdReqModel.getPayerId(), sdReqModel.getProviderId(),
				requestWrapper);
		SensitiveDrugResponseModel response = null;
		List<Long> moduleConfigurations = moduleConfigurationRepository.findByProviderIdAndPayerIdAndIsEnabled(
				Long.valueOf(sdReqModel.getPayerId()), Long.valueOf(sdReqModel.getProviderId()));
		if (moduleConfigurations.contains(ModuleConfiguration.SENSITIVE_DRUG_VALIDATION.value())) {
			response = new SensitiveDrugResponseModel();
			response.setRequestId(sdReqModel.getRequestId());
			List<String> requestedDrugList = sdReqModel.getDrugList();
			List<SensitiveDrugDetails> sensitiveDrugDetailsList = sensitiveDrugDetailsRepository
					.findByRegistrationNumberInAndIsDeleted(requestedDrugList,false);
			Optional<CommonDenials> commonDenialsOpt = getCommonDenials();
			List<DrugList> responseDrugList = requestedDrugList.stream()
					.map(drugCode -> createDrugListItem(drugCode, sensitiveDrugDetailsList, commonDenialsOpt))
					.collect(Collectors.toList());
			response.setDrugList(responseDrugList);
		}
		return response;
	}

	private void addInTransactionLog(String requestId, String payerId, String providerId,
			ContentCachingRequestWrapper requestWrapper) {
		String transactionLogId = sessionService.getTransactionLogIdFromSession(requestWrapper);
		if (StringUtils.isBlank(transactionLogId)) {
			TransactionLog transactionLog = transactionLogService.addDataInTransactionLog(RequestType.SENSITIVE_DRUG,
					requestId, payerId, providerId);
			if (transactionLog != null) {
				sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
			}
		}
	}

	private DrugList createDrugListItem(String drugCode, List<SensitiveDrugDetails> sensitiveDrugDetailsList,
			Optional<CommonDenials> commonDenialsOpt) {
		DrugList drugListItem = new DrugList();
		drugListItem.setDrugCode(drugCode);
		boolean isDrugApproved = sensitiveDrugDetailsList != null && sensitiveDrugDetailsList.stream()
				.anyMatch(drugDetails -> drugDetails.getRegistrationNumber().equals(drugCode));
		drugListItem.setDrugStatus(isDrugApproved ? ServiceStatus.APPROVED.value() : ServiceStatus.REJECTED.value());
		drugListItem.setDenialCode(isDrugApproved ? null : commonDenials.SENSITIVE_DRUG.value());
		drugListItem.setStatusDescription(isDrugApproved ? null : getDenialDescriptionMsg(drugCode, commonDenialsOpt));
		return drugListItem;
	}

	private Optional<CommonDenials> getCommonDenials() {
		return commonDenialsRepository.findByDenialCode(commonDenials.SENSITIVE_DRUG.value());
	}

	private String getDenialDescriptionMsg(String drugCode, Optional<CommonDenials> commonDenialsOpt) {
		return commonDenialsOpt
				.map(commonDenials -> commonDenials.getDenialDescription().replace("<DrugCode> <DrugName>", drugCode))
				.orElse(null);
	}
}
