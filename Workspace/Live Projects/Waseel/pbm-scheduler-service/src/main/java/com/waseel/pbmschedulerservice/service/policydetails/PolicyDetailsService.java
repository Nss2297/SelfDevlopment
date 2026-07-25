package com.waseel.pbmschedulerservice.service.policydetails;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.waseel.pbmschedulerservice.model.enums.AuditUpdatedType;
import com.waseel.pbmschedulerservice.model.enums.EntitiesName;
import com.waseel.pbmschedulerservice.model.policydetails.BenefitCasesResponseModel;
import com.waseel.pbmschedulerservice.model.policydetails.BenefitSubCoverageResponseModel;
import com.waseel.pbmschedulerservice.model.policydetails.ClassBenefitsResponseModel;
import com.waseel.pbmschedulerservice.model.policydetails.PolicyClassesResponseModel;
import com.waseel.pbmschedulerservice.model.policydetails.PolicyEndorsementModel;
import com.waseel.pbmschedulerservice.model.policydetails.PolicyEndorsementResponseModel;
import com.waseel.pbmschedulerservice.model.policydetails.PolicyMetaDataResponseModel;
import com.waseel.pbmschedulerservice.model.policydetails.PolicyMetadataModel;
import com.waseel.pbmschedulerservice.persist.businessrules.BenefitCases;
import com.waseel.pbmschedulerservice.persist.businessrules.BenefitSubcoverage;
import com.waseel.pbmschedulerservice.persist.businessrules.ClassBenefits;
import com.waseel.pbmschedulerservice.persist.businessrules.PolicyClasses;
import com.waseel.pbmschedulerservice.persist.businessrules.PolicyEndorsement;
import com.waseel.pbmschedulerservice.persist.businessrules.PolicyInformation;
import com.waseel.pbmschedulerservice.repository.businessrules.BenefitCaseRepository;
import com.waseel.pbmschedulerservice.repository.businessrules.BenefitSubCoverageRepository;
import com.waseel.pbmschedulerservice.repository.businessrules.ClassBenefitsRepository;
import com.waseel.pbmschedulerservice.repository.businessrules.PolicyClassesRepository;
import com.waseel.pbmschedulerservice.repository.businessrules.PolicyEndorsementRepository;
import com.waseel.pbmschedulerservice.repository.businessrules.PolicyInformationRepository;
import com.waseel.pbmschedulerservice.service.AuditLogService;
import com.waseel.pbmschedulerservice.service.ClientHandlerService;

@Service
public class PolicyDetailsService {

	@Value("${receiver.code}")
	private String payerId;

	@Autowired
	private ClientHandlerService clientHandlerService;

	@Autowired
	private PolicyDetailsDMLService policyDetailsDMLService;

	@Autowired
	private PolicyInformationRepository policyInformationRepository;

	@Autowired
	private PolicyClassesRepository policyClassesRepository;

	@Autowired
	private ClassBenefitsRepository classBenefitsRepository;

	@Autowired
	private BenefitCaseRepository benefitCaseRepository;

	@Autowired
	private BenefitSubCoverageRepository benefitSubCoverageRepository;

	@Autowired
	private AuditLogService auditLogService;

	@Autowired
	private PolicyEndorsementRepository policyEndorsementRepository;

	@Transactional
	public void policyDetailsImplementation() {
		boolean isLastPage = true;
		int currentPage = 0;
		do {
			ResponseEntity<PolicyMetaDataResponseModel> response = clientHandlerService
					.apiCallToGetPolicyMetadataDetails(100, currentPage++);
			if (response != null && response.getStatusCode() == HttpStatus.OK) {
				PolicyMetaDataResponseModel policyMetaDataResponse = response.getBody();
				if (policyMetaDataResponse != null) {
					isLastPage = policyMetaDataResponse.isLastPage();
					policyMetaDataResponse.getPolicyMetadata().forEach(this::policyMetaDataImplementation);
				}
			}
		} while (!isLastPage);
	}

	private void policyMetaDataImplementation(PolicyMetadataModel policyMetadataModel) {
		Optional<PolicyInformation> policyInformationOpt = policyInformationRepository
				.findByPayerIdAndPolicyNumber(payerId, policyMetadataModel.getPolicyNumber());
		PolicyInformation policyInformation = policyInformationOpt.isPresent() ? policyInformationOpt.get()
				: new PolicyInformation();
		PolicyInformation savedPolicyInformation = policyDetailsDMLService
				.setAndSavePolicyInformations(policyInformation, policyMetadataModel, payerId);
		auditLogService.addDataInAuditLog(
				policyInformationOpt.isPresent() ? AuditUpdatedType.UPDATE : AuditUpdatedType.INSERT,
				savedPolicyInformation.getPolicyInformationId(), EntitiesName.POLICY_INFORMATION,
				savedPolicyInformation);
		policyEndorsementImplementation(policyInformation);
		policyClassesImplementation(savedPolicyInformation);
	}

	private void policyClassesImplementation(PolicyInformation policyInformation) {
		ResponseEntity<PolicyClassesResponseModel> response = clientHandlerService
				.apicallToGetPolicyClassesDetails(policyInformation.getPolicyNumber());
		if (response != null && response.getStatusCode() == HttpStatus.OK) {
			PolicyClassesResponseModel policyClassesResponseModel = response.getBody();
			if (policyClassesResponseModel != null && policyClassesResponseModel.getPolicyClasses() != null) {
				policyClassesResponseModel.getPolicyClasses().forEach(policyClassesModel -> {
					Optional<PolicyClasses> policyClassesOpt = policyClassesRepository
							.findByPolicyInformationIdAndClassCode(policyInformation.getPolicyInformationId(),
									policyClassesModel.getClassCode());
					PolicyClasses policyClasses = policyClassesOpt.isPresent() ? policyClassesOpt.get()
							: new PolicyClasses();
					PolicyClasses savedPolicyClasses = policyDetailsDMLService.setAndSavePolicyClassesInformations(
							policyClasses, policyClassesModel, policyInformation.getPolicyInformationId());
					auditLogService.addDataInAuditLog(
							policyClassesOpt.isPresent() ? AuditUpdatedType.UPDATE : AuditUpdatedType.INSERT,
							savedPolicyClasses.getPolicyClassId(), EntitiesName.POLICY_CLASSES, savedPolicyClasses);
					classBenefitsImplementation(savedPolicyClasses, policyInformation.getPolicyNumber());
				});
			}
		}
	}

	private void classBenefitsImplementation(PolicyClasses policyClasses, String policyNumber) {
		ResponseEntity<ClassBenefitsResponseModel> response = clientHandlerService
				.apicallToGetClassBenefitsDetails(policyNumber, policyClasses.getClassCode());
		if (response != null && response.getStatusCode() == HttpStatus.OK) {
			ClassBenefitsResponseModel classBenefitsResponseModel = response.getBody();
			if (classBenefitsResponseModel != null && classBenefitsResponseModel.getClassBenefits() != null) {
				classBenefitsResponseModel.getClassBenefits().forEach(classBenefitsModel -> {
					Optional<ClassBenefits> classBenefitsOpt = classBenefitsRepository
							.findByPolicyClassIdAndBenefitCode(policyClasses.getPolicyClassId(),
									classBenefitsModel.getBenefitCode());
					ClassBenefits classBenefits = classBenefitsOpt.isPresent() ? classBenefitsOpt.get()
							: new ClassBenefits();
					ClassBenefits savedClassBenefits = policyDetailsDMLService.setAndSaveClassBenefitsInformations(
							classBenefits, classBenefitsModel, policyClasses.getPolicyClassId());
					auditLogService.addDataInAuditLog(
							classBenefitsOpt.isPresent() ? AuditUpdatedType.UPDATE : AuditUpdatedType.INSERT,
							savedClassBenefits.getClassBenefitId(), EntitiesName.CLASS_BENEFITS, savedClassBenefits);
					benefitCasesImplementation(savedClassBenefits, policyNumber, policyClasses.getClassCode());
				});
			}
		}
	}

	private void benefitCasesImplementation(ClassBenefits classBenefits, String policyNumber, String classCode) {
		ResponseEntity<BenefitCasesResponseModel> response = clientHandlerService
				.apicallToGetBenefitCasesDetails(policyNumber, classCode, classBenefits.getBenefitCode());
		if (response != null && response.getStatusCode() == HttpStatus.OK) {
			BenefitCasesResponseModel benefitCasesResponseModel = response.getBody();
			if (benefitCasesResponseModel != null && benefitCasesResponseModel.getBenefitCases() != null) {
				benefitCasesResponseModel.getBenefitCases().forEach(benefitCasesModel -> {
					Optional<BenefitCases> benefitCasesOpt = benefitCaseRepository.findByClassBenefitIdAndCaseCode(
							classBenefits.getClassBenefitId(), benefitCasesModel.getCaseCode());
					BenefitCases benefitCases = benefitCasesOpt.isPresent() ? benefitCasesOpt.get()
							: new BenefitCases();
					BenefitCases savedBenefitCases = policyDetailsDMLService.setAndSaveBenefitCasesInfomations(
							benefitCases, benefitCasesModel, classBenefits.getClassBenefitId());
					auditLogService.addDataInAuditLog(
							benefitCasesOpt.isPresent() ? AuditUpdatedType.UPDATE : AuditUpdatedType.INSERT,
							savedBenefitCases.getBenefitCaseId(), EntitiesName.BENEFIT_CASES, savedBenefitCases);
					benefitSubCoverageImplementation(savedBenefitCases, policyNumber, classCode,
							classBenefits.getBenefitCode());
				});
			}
		}
	}

	private void benefitSubCoverageImplementation(BenefitCases benefitCases, String policyNumber, String classCode,
			String benefitCode) {
		boolean isLastPage = true;
		int currentPage = 0;
		do {
			ResponseEntity<BenefitSubCoverageResponseModel> response = clientHandlerService
					.apicallToGetBenefitSubCoverageDetails(policyNumber, classCode, benefitCode, 100, currentPage++);
			if (response != null && response.getStatusCode() == HttpStatus.OK) {
				BenefitSubCoverageResponseModel benefitSubCoverageResponseModel = response.getBody();
				if (benefitSubCoverageResponseModel != null) {
					isLastPage = benefitSubCoverageResponseModel.isLastPage();
					manageBenefitSubCoverageImplementation(benefitSubCoverageResponseModel,
							benefitCases.getClassBenefitId());
				}
			}
		} while (!isLastPage);
	}

	private void manageBenefitSubCoverageImplementation(BenefitSubCoverageResponseModel benefitSubCoverageResponseModel,
			Long classBenefitId) {
		if (benefitSubCoverageResponseModel.getBenefitSubCoverage() != null) {
			benefitSubCoverageResponseModel.getBenefitSubCoverage().forEach(benefitSubCoverageModel -> {
				Optional<BenefitSubcoverage> benefitSubcoverageOpt = benefitSubCoverageRepository
						.findByClassBenefitIdAndSubcoverageCode(classBenefitId,
								benefitSubCoverageModel.getSubCoverageCode());
				BenefitSubcoverage benefitSubcoverage = benefitSubcoverageOpt.isPresent() ? benefitSubcoverageOpt.get()
						: new BenefitSubcoverage();
				BenefitSubcoverage savedBenefitSubcoverage = policyDetailsDMLService
						.setAndSaveBenefitSubcoverageInformation(benefitSubcoverage, benefitSubCoverageModel,
								classBenefitId);
				auditLogService.addDataInAuditLog(
						benefitSubcoverageOpt.isPresent() ? AuditUpdatedType.UPDATE : AuditUpdatedType.INSERT,
						savedBenefitSubcoverage.getBenefitSubcoverageId(), EntitiesName.BENEFIT_SUBCOVERAGE,
						savedBenefitSubcoverage);
			});
		}
	}

	private void policyEndorsementImplementation(PolicyInformation policyInformation) {
		ResponseEntity<PolicyEndorsementResponseModel> response = clientHandlerService
				.apicallToGetPolicyEndorsementsDetails(policyInformation.getPolicyNumber());
		if (response != null && response.getStatusCode() == HttpStatus.OK) {
			PolicyEndorsementResponseModel policyEndorsementResponseModel = response.getBody();
			if (policyEndorsementResponseModel != null
					&& policyEndorsementResponseModel.getPolicyEndorsement() != null) {
				PolicyEndorsementModel policyEndorsementModel = policyEndorsementResponseModel.getPolicyEndorsement();
				if (policyEndorsementModel != null) {
					Optional<PolicyEndorsement> policyEndorsementOpt = policyEndorsementRepository
							.findByPolicyInformationIdAndEndorsementNumber(policyInformation.getPolicyInformationId(),
									policyEndorsementModel.getEndorsementNumber());
					PolicyEndorsement policyEndorsement = policyEndorsementOpt.isPresent() ? policyEndorsementOpt.get()
							: new PolicyEndorsement();
					PolicyEndorsement savedPolicyEndorsement = policyDetailsDMLService
							.setAndSavePolicyEndorsementInformations(policyEndorsement, policyEndorsementModel,
									policyInformation.getPolicyInformationId());
					auditLogService.addDataInAuditLog(
							policyEndorsementOpt.isPresent() ? AuditUpdatedType.UPDATE : AuditUpdatedType.INSERT,
							savedPolicyEndorsement.getPolicyEndorsementId(), EntitiesName.POLICY_ENDORSEMENT,
							savedPolicyEndorsement);
				}
			}
		}
	}

	private void policyMetaDataByPolicyNumberImplementation(String policyNumber) {
		// Will call this implementation later on...
		ResponseEntity<PolicyMetadataModel> policyMetadata = clientHandlerService
				.apiCallToGetPolicyMetadataDetailsByPolicyNumber(policyNumber);
		if (policyMetadata.getStatusCode() == HttpStatus.OK && policyMetadata.getBody() != null) {
			PolicyMetadataModel policyMetadataModel = policyMetadata.getBody();
			Optional<PolicyInformation> policyInformationOpt = policyInformationRepository
					.findByPayerIdAndPolicyNumber(payerId, policyNumber);
			PolicyInformation policyInformation = policyInformationOpt.isPresent() ? policyInformationOpt.get()
					: new PolicyInformation();
			PolicyInformation savedPolicyInformation = policyDetailsDMLService
					.setAndSavePolicyInformations(policyInformation, policyMetadataModel, payerId);
			auditLogService.addDataInAuditLog(
					policyInformationOpt.isPresent() ? AuditUpdatedType.UPDATE : AuditUpdatedType.INSERT,
					savedPolicyInformation.getPolicyInformationId(), EntitiesName.POLICY_INFORMATION,
					savedPolicyInformation);
		}
	}
}
