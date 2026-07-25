package com.waseel.pbmschedulerservice.service.policydetails;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbmschedulerservice.model.policydetails.BenefitCasesModel;
import com.waseel.pbmschedulerservice.model.policydetails.BenefitSubCoverageModel;
import com.waseel.pbmschedulerservice.model.policydetails.ClassBenefitsModel;
import com.waseel.pbmschedulerservice.model.policydetails.PolicyClassesModel;
import com.waseel.pbmschedulerservice.model.policydetails.PolicyEndorsementModel;
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

@Service
public class PolicyDetailsDMLService {

	private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private DateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

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
	private PolicyEndorsementRepository policyEndorsementRepository;

	public BenefitSubcoverage setAndSaveBenefitSubcoverageInformation(BenefitSubcoverage benefitSubcoverage,
			BenefitSubCoverageModel benefitSubCoverageModel, Long classBenefitId) {
		benefitSubcoverage.setClassBenefitId(classBenefitId);
		benefitSubcoverage.setSubcoverageCode(benefitSubCoverageModel.getSubCoverageCode());
		benefitSubcoverage.setSubcovAppThresholdCurrency(benefitSubCoverageModel.getSubCoverageThresholdCurrency());
		benefitSubcoverage.setSubcovAppThresholdValue(benefitSubCoverageModel.getSubCoverageThresholdValue());
		benefitSubcoverage.setSubcoverageDescription(benefitSubCoverageModel.getSubCoverageDescription());
		benefitSubcoverage.setSubcoverageLimitCurrency(benefitSubCoverageModel.getSubCoverageLimitCurrency());
		benefitSubcoverage.setSubcoverageLimitValue(benefitSubCoverageModel.getSubCoverageLimitValue());
		return benefitSubCoverageRepository.save(benefitSubcoverage);
	}

	public BenefitCases setAndSaveBenefitCasesInfomations(BenefitCases benefitCases,
			BenefitCasesModel benefitCasesModel, Long classBenefitId) {
		benefitCases.setCaseCode(benefitCasesModel.getCaseCode());
		benefitCases.setClassBenefitId(classBenefitId);
		benefitCases.setApprovalThresholdCurrency(benefitCasesModel.getApprovalThresholdCurrency());
		benefitCases.setApprovalThresholdValue(benefitCasesModel.getApprovalThresholdValue());
		benefitCases.setMaxConsultationFeeCurrency(benefitCasesModel.getMaxConsultationFeeCurrency());
		benefitCases.setMaxConsultationFeeValue(benefitCasesModel.getMaxConsultationFeeValue());
		benefitCases.setMaxPatientShareCurrency(benefitCasesModel.getMaxPatientShareCurrency());
		benefitCases.setMaxPatientShareValue(benefitCasesModel.getMaxConsultationFeeValue());
		benefitCases.setPatientShareCurrency(getPatientShareCurrency(benefitCasesModel.getPatientShareValue()));
		benefitCases.setPatientShareValue(convertAndSplitPatientShareValue(benefitCasesModel.getPatientShareValue()));
		return benefitCaseRepository.save(benefitCases);
	}

	public ClassBenefits setAndSaveClassBenefitsInformations(ClassBenefits classBenefits,
			ClassBenefitsModel classBenefitsModel, Long policyClassId) {
		classBenefits.setPolicyClassId(policyClassId);
		classBenefits.setBenefitCode(classBenefitsModel.getBenefitCode());
		classBenefits.setBenefitDescription(classBenefitsModel.getBenefitDescription());
		classBenefits.setBenefitLimitValue(classBenefitsModel.getBenefitLimitValue());
		classBenefits.setBenefitLimitCurrency(classBenefitsModel.getBenefitLimitCurrency());
		classBenefits.setPatientShareValue(convertAndSplitPatientShareValue(classBenefitsModel.getPatientShareValue()));
		classBenefits.setPatientShareCurrency(getPatientShareCurrency(classBenefitsModel.getPatientShareValue()));
		classBenefits.setMaxPatientShareValue(classBenefitsModel.getMaxPatientShareValue());
		classBenefits.setMaxPatientShareCurrency(classBenefitsModel.getMaxPatientShareCurrency());
		classBenefits.setMaxConsultationFeeValue(classBenefitsModel.getMaxConsultationFeeValue());
		classBenefits.setMaxConsultationFeeCurrency(classBenefitsModel.getMaxConsultationFeeCurrency());
		classBenefits.setApprovalThresholdValue(classBenefitsModel.getApprovalThresholdValue());
		classBenefits.setApprovalThresholdCurrency(classBenefitsModel.getApprovalThresholdCurrency());
		classBenefits.setCoverage(classBenefitsModel.getCoverage());
		classBenefits.setExclusion(classBenefitsModel.getExclusions());
		classBenefits.setComments(classBenefitsModel.getComments());
		return classBenefitsRepository.save(classBenefits);
	}

	public PolicyClasses setAndSavePolicyClassesInformations(PolicyClasses policyClasses,
			PolicyClassesModel policyClassesModel, Long policyInformationId) {
		policyClasses.setClassCode(policyClassesModel.getClassCode());
		policyClasses.setPolicyInformationId(policyInformationId);
		policyClasses.setClassLimitValue(policyClassesModel.getClassLimitValue());
		policyClasses.setClassLimitCurrency(policyClassesModel.getClassLimitCurrency());
		policyClasses.setComments(policyClassesModel.getComments());
		policyClasses.setExclusion(policyClassesModel.getExclusions());
		policyClasses.setCoverage(policyClassesModel.getCoverage());
		return policyClassesRepository.save(policyClasses);
	}

	public PolicyInformation setAndSavePolicyInformations(PolicyInformation policyInformation,
			PolicyMetadataModel policyMetadataModel, String payerId) {
		policyInformation.setPolicyNumber(policyMetadataModel.getPolicyNumber());
		policyInformation.setPayerId(payerId);
		policyInformation.setPolicyHolderName(policyMetadataModel.getPolicyHolderName());
		policyInformation.setPolicyType(policyMetadataModel.getPolicyType());
		policyInformation.setIssueDate(convertStringToDate(policyMetadataModel.getIssueDate(), dateFormat));
		policyInformation.setStartDate(convertStringToDate(policyMetadataModel.getStartDate(), dateFormat));
		policyInformation.setEndDate(convertStringToDate(policyMetadataModel.getEndDate(), dateFormat));
		policyInformation.setLastUpdateDate(
				convertStringToDate(policyMetadataModel.getLastUpdatedDateAndTime(), dateTimeFormat));
		policyInformation.setCoverage(policyMetadataModel.getCoverage());
		policyInformation.setExclusion(policyMetadataModel.getExclusions());
		policyInformation.setComments(policyMetadataModel.getComments());
		return policyInformationRepository.save(policyInformation);
	}

	public PolicyEndorsement setAndSavePolicyEndorsementInformations(PolicyEndorsement policyEndorsement,
			PolicyEndorsementModel policyEndorsementModel, Long policyInformationId) {
		policyEndorsement.setPolicyInformationId(policyInformationId);
		policyEndorsement.setEndorsementNumber(policyEndorsementModel.getEndorsementNumber());
		policyEndorsement
				.setEndorsementDate(convertStringToDate(policyEndorsementModel.getEndorsementDate(), dateFormat));
		policyEndorsement.setEndorsementType(policyEndorsementModel.getEndorsementType());
		policyEndorsement.setEndorsementMessage(policyEndorsementModel.getEndorsementMessage());
		return policyEndorsementRepository.save(policyEndorsement);
	}

	private Timestamp convertStringToDate(String dateStr, DateFormat format) {
		try {
			return new Timestamp(format.parse(dateStr).getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getPatientShareCurrency(String patientShareValue) {
		return patientShareValue.substring(patientShareValue.length() - 1);
	}

	private BigDecimal convertAndSplitPatientShareValue(String patientShareValue) {
		return new BigDecimal(patientShareValue.substring(0, patientShareValue.length() - 1));
	}
}
