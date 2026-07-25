package com.waseel.pbm.payercustomizationservice.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.waseel.pbm.payercustomizationservice.enums.ScreeningModules;
import com.waseel.pbm.payercustomizationservice.model.CustomizationRequestModel;
import com.waseel.pbm.payercustomizationservice.model.CustomizationResponseModel;
import com.waseel.pbm.payercustomizationservice.model.DssResponse;
import com.waseel.pbm.payercustomizationservice.model.PCRequest;
import com.waseel.pbm.payercustomizationservice.persist.ScreeningModuleAuditTrail;
import com.waseel.pbm.payercustomizationservice.persist.mongodb.CustomizationRequestAuditTrail;
import com.waseel.pbm.payercustomizationservice.persist.mongodb.PCSAuditTrail;
import com.waseel.pbm.payercustomizationservice.repository.ScreeningModuleAuditTrailRepository;
import com.waseel.pbm.payercustomizationservice.repository.mongodb.CustomizationRequestAuditTrailRepository;
import com.waseel.pbm.payercustomizationservice.repository.mongodb.PCSAuditTrailRepository;

@Service
public class AuditService {

	@Autowired
	private PCSAuditTrailRepository pcsAuditTrailRepository;
	@Autowired
	ScreeningModuleAuditTrailRepository screeningModuleAuditTrailRepository;
	@Autowired
	private MapperService mapperService;
	@Autowired
	private CustomizationRequestAuditTrailRepository customizationRequestAuditTrailRepository;

	public void saveAuditLogInMongoDbAndOracle(PCRequest pcRequest, DssResponse dssResponse,
			List<Integer> enabledModuleIds) {
		CompletableFuture.runAsync(() -> {
			PCSAuditTrail pcsAuditTrail = saveAllModulesMetaDataInMongoDb(pcRequest, dssResponse);
			saveAllModulesMetaDataInOracle(pcsAuditTrail, pcRequest.getDssResponse().getTransactionLogId(),
					enabledModuleIds);
		});
	}

	public void saveInvalidRequestsToMongoDb(ContentCachingRequestWrapper request, DssResponse dssResponse) {
		CompletableFuture
				.runAsync(() -> saveAllModulesMetaDataInMongoDb(mapperService.mapPCSRequest(request), dssResponse));
	}

	private PCSAuditTrail saveAllModulesMetaDataInMongoDb(PCRequest pcRequest, DssResponse dssResponse) {
		PCSAuditTrail audit = new PCSAuditTrail();
		if (pcRequest != null) {
			audit.setPayerId(pcRequest.getDssRequest().getPayerId());
			audit.setRequestId(pcRequest.getDssResponse().getRequestId());
			audit.setPcsRequest(pcRequest);
		}
		audit.setSubmissionDateTime(new Date());
		audit.setPcsResponse(dssResponse);
		return pcsAuditTrailRepository.save(audit);
	}

	private void saveAllModulesMetaDataInOracle(PCSAuditTrail pcsAuditTrail, Long transactionLogId,
			List<Integer> enabledModuleIds) {
		ScreeningModuleAuditTrail entity = new ScreeningModuleAuditTrail();
		if (pcsAuditTrail != null) {
			entity.setMongodbUniqueId(pcsAuditTrail.getDocumentId());
			entity.setPayerId(pcsAuditTrail.getPayerId());
			entity.setRequestId(pcsAuditTrail.getRequestId());
		}
		entity.setModuleId(combineModulesId(enabledModuleIds));
		entity.setTransactionLogId(transactionLogId);
		entity.setModuleType(ScreeningModules.PAYER_CUSTOMIZATION.name());
		screeningModuleAuditTrailRepository.save(entity);
	}

	private String combineModulesId(List<Integer> enabledModuleIds) {
		return enabledModuleIds.toString().replace("[", "").replace("]", "");
	}

	public void saveUpdateCustomizationRequestData(CustomizationRequestModel request, Long customizationRequestId) {
		CompletableFuture.runAsync(() -> saveUpdateCustomizationRequestDataInMongoDb(request, customizationRequestId));
	}

	private void saveUpdateCustomizationRequestDataInMongoDb(CustomizationRequestModel requestModel,
			Long customizationRequestId) {
		CustomizationRequestAuditTrail audit = new CustomizationRequestAuditTrail();
		audit.setCustomizationRequestModel(requestModel);
		audit.setCustomizationRequestId(customizationRequestId);
		customizationRequestAuditTrailRepository.save(audit);
	}

	public void manageCustomizationRequestsAuditTrails(ContentCachingRequestWrapper contentCachingRequestWrapper,
			ContentCachingResponseWrapper contentCachingResponseWrapper) {
		CustomizationRequestModel customizationRequestModel = mapperService
				.mapCustomizationRequestModel(contentCachingRequestWrapper);
		CustomizationResponseModel customizationResponseModel = mapperService
				.mapCustomizationResponseModel(contentCachingResponseWrapper);
		CompletableFuture.runAsync(() -> manageCustomizationRequestsAuditsInMongoDb(customizationRequestModel,
				customizationResponseModel));
	}

	private void manageCustomizationRequestsAuditsInMongoDb(CustomizationRequestModel customizationRequestModel,
			CustomizationResponseModel customizationResponseModel) {
		CustomizationRequestAuditTrail customizationRequestAuditTrail = new CustomizationRequestAuditTrail();
		customizationRequestAuditTrail.setCustomizationRequestModel(customizationRequestModel);
		customizationRequestAuditTrail.setCustomizationResponseModel(customizationResponseModel);
		customizationRequestAuditTrail.setDateTime(new Date());
		customizationRequestAuditTrailRepository.save(customizationRequestAuditTrail);
	}
}
