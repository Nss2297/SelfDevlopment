package com.waseel.pbm.dssservice.service.managementservice;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.dssservice.enums.PayerFeatures;
import com.waseel.pbm.dssservice.model.DssRequest;
import com.waseel.pbm.dssservice.persist.mdss.ChronicDzDiagnosisAssoc;
import com.waseel.pbm.dssservice.persist.mdss.ChronicDzInformation;
import com.waseel.pbm.dssservice.persist.mdss.MemberChronicDiagnosisAssoc;
import com.waseel.pbm.dssservice.persist.mdss.MemberChronicDiagnosisAssocId;
import com.waseel.pbm.dssservice.persist.mdss.MemberChronicDzAssoc;
import com.waseel.pbm.dssservice.persist.mdss.PayerFeaturesConfiguration;
import com.waseel.pbm.dssservice.persist.mdss.PayerFeaturesConfigurationId;
import com.waseel.pbm.dssservice.repository.mdss.ChronicDzDiagnosisAssocRepository;
import com.waseel.pbm.dssservice.repository.mdss.ChronicDzDrugAssocRepository;
import com.waseel.pbm.dssservice.repository.mdss.MemberChronicDiagnosisAssocRepository;
import com.waseel.pbm.dssservice.repository.mdss.MemberChronicDzAssocRepository;
import com.waseel.pbm.dssservice.repository.mdss.PayerFeaturesConfigurationRepository;

@Service
public class ChronicDzManagementService {

	@Autowired
	ChronicDzDiagnosisAssocRepository chronicDzDiagnosisAssocRepository;
	@Autowired
	MemberChronicDzAssocRepository memberChronicDzAssocRepository;
	@Autowired
	PayerFeaturesConfigurationRepository payerFeaturesConfigurationRepo;
	@Autowired
	ChronicDzDrugAssocRepository chronicDzDrugAssocRepo;
	@Autowired
	ChronicDzDiagnosisAssocRepository chronicDzDiagnosisAssocRepo;
	@Autowired
	MemberChronicDiagnosisAssocRepository memberChronicDiagnosisAssocRepo;

	public DssRequest manageChronicDzValidation(DssRequest dssRequest) {

		validateChronicMember(dssRequest);

		Optional<PayerFeaturesConfiguration> chronicHistoryConfiguration = payerFeaturesConfigurationRepo
				.findById(new PayerFeaturesConfigurationId(dssRequest.getPayerId(),
						PayerFeatures.CHECK_MEMBER_CHRONIC_DZ_HISTORY.value()));

		if (chronicHistoryConfiguration != null && !chronicHistoryConfiguration.isEmpty()
				&& chronicHistoryConfiguration.get().getIsEnabled().equalsIgnoreCase("1")) {
			return extendChronicDiagnosisList(dssRequest);

		}
		return dssRequest;

	}

	public void validateChronicMember(DssRequest dssRequest) {

		List<ChronicDzDiagnosisAssoc> chronicDzDiagnosisList = chronicDzDiagnosisAssocRepository
				.findByIsEnabledAndDiagnosisCodeIn("1", dssRequest.getIcdCodes());
		if (chronicDzDiagnosisList != null && !chronicDzDiagnosisList.isEmpty()) {
			chronicDzDiagnosisList.forEach(chronicDzDiagnosis -> {

				// save member chronic
				MemberChronicDzAssoc memberFlagedAsChronicPatient = memberChronicDzAssocRepository
						.findByChronicDzInformationAndMemberId(chronicDzDiagnosis.getChronicDzInformation(),
								dssRequest.getMemberId());

				if (memberFlagedAsChronicPatient != null) {
					if (memberFlagedAsChronicPatient.getIsEnabled().equalsIgnoreCase("0")) {
						memberFlagedAsChronicPatient.setIsEnabled("1");
						memberChronicDzAssocRepository.save(memberFlagedAsChronicPatient);
					}
					// save related diagnosis
					saveMemberChronicDiganosis(memberFlagedAsChronicPatient.getMemberChronicDzAssocId(),
							chronicDzDiagnosis.getDiagnosisCode());
				} else if (memberFlagedAsChronicPatient == null) {
					MemberChronicDzAssoc memberNewlyFlagedAsChronicPatient = flagMemberAsChronicPatient(
							chronicDzDiagnosis.getChronicDzInformation(), dssRequest.getMemberId(),
							dssRequest.getPayerId());
					if (memberFlagedAsChronicPatient != null) {
						saveMemberChronicDiganosis(memberNewlyFlagedAsChronicPatient.getMemberChronicDzAssocId(),
								chronicDzDiagnosis.getDiagnosisCode());
					}

				}

			});
		}

	}

	public MemberChronicDzAssoc flagMemberAsChronicPatient(ChronicDzInformation chronicDzInfo, String memberId,
			String payerId) {

		MemberChronicDzAssoc memberChronicDzAssoc = new MemberChronicDzAssoc();
		memberChronicDzAssoc.setChronicDzInformation(chronicDzInfo);
		memberChronicDzAssoc.setMemberId(memberId);
		memberChronicDzAssoc.setIsEnabled("1");
		memberChronicDzAssoc.setLastUpdateDateAndTime(new Timestamp(System.currentTimeMillis()));
		memberChronicDzAssoc.setPayerId(payerId);
		try {
			return memberChronicDzAssocRepository.save(memberChronicDzAssoc);
		} catch (Exception e) {
			return null;
		}

	}

	private void saveMemberChronicDiganosis(Integer memberChronicAssoId, String diagnosisCode) {
		MemberChronicDiagnosisAssoc memberChronicDiagnosisAssoc = new MemberChronicDiagnosisAssoc();
		memberChronicDiagnosisAssoc.setId(new MemberChronicDiagnosisAssocId(memberChronicAssoId, diagnosisCode));
		memberChronicDiagnosisAssoc.setIsEnabled("1");
		memberChronicDiagnosisAssoc.setLastUpdateDateAndTime(new Timestamp(System.currentTimeMillis()));
		memberChronicDiagnosisAssocRepo.save(memberChronicDiagnosisAssoc);
	}

	private DssRequest extendChronicDiagnosisList(DssRequest dssRequest) {
		List<String> chronicDiagnosisList = new ArrayList<>();
		DssRequest dssRequestCopy = new DssRequest(dssRequest);

		List<Integer> memberChronicIds = memberChronicDzAssocRepository
				.findChronicDiseasesIdByMemberId(dssRequest.getMemberId());

		if (memberChronicIds != null && !memberChronicIds.isEmpty()) {

			dssRequestCopy.getDrugList().forEach(reqDrug -> {
				List<Integer> drugChronicDzIds = chronicDzDrugAssocRepo
						.findByChronicDzIdsAndServiceCode(memberChronicIds, reqDrug.getNdcDrugCode());

				if (drugChronicDzIds != null && !drugChronicDzIds.isEmpty()) {
					List<String> associatedDiagnosis = memberChronicDiagnosisAssocRepo
							.findDiganosisByMemberIdAndChronicDz(drugChronicDzIds, dssRequest.getMemberId());
					if (associatedDiagnosis != null && !associatedDiagnosis.isEmpty()) {
						chronicDiagnosisList.addAll(associatedDiagnosis);
					}
				}
			});

			chronicDiagnosisList.addAll(dssRequest.getIcdCodes());
			List<String> distinctChronicDiagnosisList = chronicDiagnosisList.stream().distinct()
					.collect(Collectors.toList());
			dssRequestCopy.setIcdCodes(distinctChronicDiagnosisList);

			return dssRequestCopy;

		}

		return dssRequest;

	}

}
