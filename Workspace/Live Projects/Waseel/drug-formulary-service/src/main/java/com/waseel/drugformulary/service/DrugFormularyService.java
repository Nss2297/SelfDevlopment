package com.waseel.drugformulary.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.drugformulary.clients.PBMPayerApisServiceClient;
import com.waseel.drugformulary.model.DrugFormularyDetailsModel;
import com.waseel.drugformulary.model.DrugFormularyRequestModel;
import com.waseel.drugformulary.model.DrugFormularyResponseModel;
import com.waseel.drugformulary.model.DrugServiceModel;
import com.waseel.drugformulary.model.MemberPolicyAssociationProjection;
import com.waseel.drugformulary.model.enums.DenialCode;
import com.waseel.drugformulary.model.enums.RequestType;
import com.waseel.drugformulary.model.enums.ServiceStatus;
import com.waseel.drugformulary.model.pbmpayerapis.MemberDemographicDataResponseModel;
import com.waseel.drugformulary.persist.businessrules.CommonDenials;
import com.waseel.drugformulary.persist.businessrules.DrugFormularyDetails;
import com.waseel.drugformulary.persist.businessrules.DrugFormularyMetadata;
import com.waseel.drugformulary.persist.businessrules.DrugFormularyPolicyAssociation;
import com.waseel.drugformulary.persist.businessrules.MemberPolicyAssociation;
import com.waseel.drugformulary.persist.businessrules.TransactionLog;
import com.waseel.drugformulary.persist.mdss.DrugServiceMetaData;
import com.waseel.drugformulary.repository.businessrules.CommonDenialsRepository;
import com.waseel.drugformulary.repository.businessrules.DrugFormularyDetailsRepository;
import com.waseel.drugformulary.repository.businessrules.DrugFormularyMetadataRepository;
import com.waseel.drugformulary.repository.businessrules.DrugFormularyPolicyAssociationRepository;
import com.waseel.drugformulary.repository.businessrules.MemberPolicyAssociationRepository;
import com.waseel.drugformulary.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.drugformulary.repository.mdss.DrugServiceRepository;

@Service
public class DrugFormularyService {

	@Autowired
	private DrugFormularyMetadataRepository drugFormularyMetadataRepository;

	@Autowired
	private MemberPolicyAssociationRepository memberPolicyAssociationRepository;

	@Autowired
	private DrugFormularyPolicyAssociationRepository drugFormularyPolicyAssociationRepository;

	@Autowired
	private DrugFormularyDetailsRepository drugFormularyDetailsRepository;

	@Autowired
	private DrugServiceRepository drugServiceRepository;

	@Autowired
	private CommonDenialsRepository commonDenialsRepository;

	@Autowired
	private TransactionLogService transactionLogService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	@Autowired
	private PBMPayerApisServiceClient payerApisServiceClient;

	public List<DrugFormularyResponseModel> createDrugFormulary(String payerId, Long idNumber,
			DrugFormularyRequestModel drugFormularyRequestModel, ContentCachingRequestWrapper requestWrapper) {
		addInTransactionLog(drugFormularyRequestModel.getRequestId(), payerId, requestWrapper);
		List<DrugFormularyResponseModel> response = new ArrayList<>();
		List<String> approvableDrugs = new ArrayList<>();
		List<String> drugList = drugFormularyRequestModel.getDrugList();
		Long formularyId = processTogetFormularyId(idNumber, payerId);
		if (formularyId != null) {
			List<DrugFormularyDetails> drugFormularyDetails = getDrugFormularyDetails(formularyId);
			if (drugFormularyDetails != null) {
				List<DrugServiceModel> drugServiceModel = getDrugServiceModelList(drugList);
				if (drugServiceModel != null && !drugServiceModel.isEmpty()) {
					drugServiceModel.forEach(drugService -> {
						if (drugList.stream().anyMatch(drug -> drug.equals(drugService.getDrugCode()))
								&& drugFormularyDetails.stream()
										.anyMatch(drugFormularyDetail -> Objects.equals(
												drugFormularyDetail.getRegistrationNumber(),
												drugService.getDrugCode()))) {
							approvableDrugs.add(drugService.getDrugCode());
						}
					});
					if (!approvableDrugs.isEmpty()) {
						prepareResponseForRejectedOrApprovedDrug(drugList, approvableDrugs, response);
						return getSortedResponseList(drugFormularyRequestModel, response);
					}
				}
			}
		}
		prepareResponseForRejectedDrug(drugList, response);
		return getSortedResponseList(drugFormularyRequestModel, response);
	}

	private void prepareResponseForRejectedOrApprovedDrug(List<String> drugList,
			List<String> approvableDrugs, List<DrugFormularyResponseModel> response) {
		List<String> rejectableDrugs = drugList.stream()
				.filter(drugCode -> approvableDrugs.stream().noneMatch(approvedDrug -> approvedDrug.equals(drugCode)))
				.collect(Collectors.toList());
		approvableDrugs
				.forEach(drug -> response.add(new DrugFormularyResponseModel(ServiceStatus.APPROVED.value(), drug)));
		prepareResponseForRejectedDrug(rejectableDrugs, response);
	}

	private void prepareResponseForRejectedDrug(List<String> rejectableDrugs,
			List<DrugFormularyResponseModel> response) {
		Optional<CommonDenials> commonDenials = getCommonDenialsForDrugFormulary();
		rejectableDrugs.forEach(drug -> response.add(new DrugFormularyResponseModel(ServiceStatus.REJECTED.value(),
				DenialCode.DRUG_FORMULARY.value(), getDenialDescriptionMsg(drug, commonDenials), drug)));
	}

	private List<DrugServiceModel> getDrugServiceModelList(List<String> drugCodes) {
		return drugServiceRepository.findByDrugCodes(drugCodes, getDrugListId());
	}

	private Long processTogetFormularyId(Long idNumber, String payerId) {
		MemberPolicyAssociationProjection memberPolicyAss = getMemberPolicyAssociationInformation(idNumber, payerId);
		if (memberPolicyAss != null) {
			return memberPolicyAss.getFormularyId();
		}
		return null;
	}

	public DrugFormularyDetailsModel getDrugFormularyDetailsModel(Long idNumber, String payerId) {
		Long formularyId = processTogetFormularyId(idNumber, payerId);
		if (formularyId != null) {
			return new DrugFormularyDetailsModel(formularyId);
		}
		return null;
	}

	private List<DrugFormularyDetails> getDrugFormularyDetails(Long formularyId) {
		return drugFormularyDetailsRepository.findByFormularyIdAndIsDeleted(formularyId, false);
	}

	private DrugFormularyPolicyAssociation getDrugFormularyPolicyAssociation(List<Long> formularyId,
			Long policyInformationId, Long policyClassId, Long memberPolicyAssociationId) {
		Optional<DrugFormularyPolicyAssociation> drugFormularyPolicyAss = drugFormularyPolicyAssociationRepository
				.getDrugFormularyPolicyAssociationDetail(formularyId, policyInformationId, policyClassId,
						memberPolicyAssociationId);
		return drugFormularyPolicyAss.isPresent() ? drugFormularyPolicyAss.get() : null;
	}

	private MemberPolicyAssociationProjection getMemberPolicyAssociationInformation(Long idNumber, String payerId) {
		ResponseEntity<MemberDemographicDataResponseModel> memberDemographicDataResponse = payerApisServiceClient
				.getMemberDemographicData(idNumber/*, payerId */);
		MemberDemographicDataResponseModel memberDemographicData = memberDemographicDataResponse.getBody();
		Optional<MemberPolicyAssociationProjection> memberPolicyAssociation = Optional.empty();
		if (memberDemographicData != null && memberDemographicData.getPolicyInformation() != null
				&& !memberDemographicData.getPolicyInformation().isEmpty()) {
			memberPolicyAssociation = memberPolicyAssociationRepository
					.findByPolicyNumberAndClassCodeAndMemberId(
							memberDemographicData.getPolicyInformation().get(0).getPolicyNumber(),
							memberDemographicData.getPolicyInformation().get(0).getClassCode(),
							memberDemographicData.getPolicyInformation().get(0).getMemberId());
		}
		return memberPolicyAssociation.isPresent() ? memberPolicyAssociation.get() : null;
	}

	private List<DrugFormularyMetadata> getFormularyIdsBasedOnPayer(String payerId) {
		return drugFormularyMetadataRepository.findByPayerId(payerId);
	}

	private Optional<CommonDenials> getCommonDenialsForDrugFormulary() {
		return commonDenialsRepository.findByDenialCode(DenialCode.DRUG_FORMULARY.value());
	}

	private String getDenialDescriptionMsg(String drugCode, Optional<CommonDenials> commonDenialsOpt) {
		if (commonDenialsOpt.isPresent()) {
			return commonDenialsOpt.get().getDenialDescription().replace("<drugcode> <DrugName>", drugCode);
		}
		return null;
	}

	private List<DrugFormularyResponseModel> getSortedResponseList(DrugFormularyRequestModel req,
			List<DrugFormularyResponseModel> res) {
		// Use to add drugList in same order in response that defined in the request
		return res.stream().sorted((response1, response2) -> req.getDrugList().indexOf(response1.getDrugCode())
				- req.getDrugList().indexOf(response2.getDrugCode())).collect(Collectors.toList());
	}

	private void addInTransactionLog(String requestId, String payerId, ContentCachingRequestWrapper requestWrapper) {
		TransactionLog transactionLog = transactionLogService.addDataInTransactionLog(RequestType.DRUG_FORMULARY,
				requestId, payerId);
		if (transactionLog != null) {
			sessionService.setTransactionLogIdInSession(requestWrapper, transactionLog.getTransactionLogId());
		}
	}

	private Long getDrugListId() {
		return drugServiceMetaDataRepository
				.findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(new Date())
				.map(DrugServiceMetaData::getDrugListId).orElse(0L);
	}
}
