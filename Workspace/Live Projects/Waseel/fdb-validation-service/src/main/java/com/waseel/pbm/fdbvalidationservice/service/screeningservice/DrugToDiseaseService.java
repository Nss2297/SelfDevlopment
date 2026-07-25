package com.waseel.pbm.fdbvalidationservice.service.screeningservice;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.fdbvalidationservice.controller.ScreeningController;
import com.waseel.pbm.fdbvalidationservice.dto.ContraIndication;
import com.waseel.pbm.fdbvalidationservice.dto.Indications;
import com.waseel.pbm.fdbvalidationservice.enums.FdbRejectionCodes;
import com.waseel.pbm.fdbvalidationservice.enums.ServiceStatus;
import com.waseel.pbm.fdbvalidationservice.model.Error;
import com.waseel.pbm.fdbvalidationservice.model.FdbDrugList;
import com.waseel.pbm.fdbvalidationservice.model.FdbDrugResult;
import com.waseel.pbm.fdbvalidationservice.model.FdbRequest;
import com.waseel.pbm.fdbvalidationservice.model.FdbResponse;
import com.waseel.pbm.fdbvalidationservice.persist.mdss.FdbNotExistDiagnosis;
import com.waseel.pbm.fdbvalidationservice.persist.mdss.FdbNotExistDiagnosisId;
import com.waseel.pbm.fdbvalidationservice.persist.mdss.FdbdiagnosisIndicationConfig;
import com.waseel.pbm.fdbvalidationservice.repository.mdss.CommonRejectionReasonRepository;
import com.waseel.pbm.fdbvalidationservice.repository.mdss.FDBDiagnosisIndicationConfigRepository;
import com.waseel.pbm.fdbvalidationservice.repository.mdss.FdbNotExistDiagnosisRepository;
import com.waseel.pbm.fdbvalidationservice.repository.medk_fdb.Rfmldx0DxidRepository;
import com.waseel.pbm.fdbvalidationservice.repository.medk_fdb.Rfmlinm1IcdDescRepository;

@Service
public class DrugToDiseaseService {

	private final Logger log = LoggerFactory.getLogger(DrugToDiseaseService.class);

	@Autowired
	Rfmldx0DxidRepository drugToDiseaseRepo;
	@Autowired
	CommonRejectionReasonRepository commonRejectionReasonRepo;
	@Autowired
	FDBDiagnosisIndicationConfigRepository fdbDiagnosisIndicationRepository;
	@Autowired
	Rfmldx0DxidRepository rfmldx0DxidRepository;
	@Autowired
	Rfmlinm1IcdDescRepository icdRepo;
	@Autowired
	FdbNotExistDiagnosisRepository fdbNotExistDiagnosisRepo;

	public FdbResponse validate(FdbRequest fdbRequest) {
		FdbResponse drugDiseaseValidationResponse = new FdbResponse();
		drugDiseaseValidationResponse.setRequestId(fdbRequest.getRequestId());

		List<FdbDrugResult> drugDiseaseValidationResults = new ArrayList<>();
		List<String> wildCardDiagnosis = new ArrayList<>();
		List<String> exactMatchDiagnosis = new ArrayList<>();
		List<String> existDiagnosisInFDB = new ArrayList<>();

		checkIcdCodeExistence(fdbRequest.getRequestId(), fdbRequest.getDiagnosisCodes(), wildCardDiagnosis,
				exactMatchDiagnosis, existDiagnosisInFDB);
		if (!existDiagnosisInFDB.isEmpty()) {
			populateDrugDiseaseValidationResults(fdbRequest.getDrugList(), fdbRequest.getDiagnosisCodes(),
					wildCardDiagnosis, exactMatchDiagnosis, existDiagnosisInFDB, drugDiseaseValidationResults);
			if (!drugDiseaseValidationResults.isEmpty()) {
				drugDiseaseValidationResponse.setDrugResults(drugDiseaseValidationResults);
				return drugDiseaseValidationResponse;
			}
		}
		return null;
	}

	private void populateDrugDiseaseValidationResults(List<FdbDrugList> drugList, List<String> diagnosisCodes,
			List<String> wildCardDiagnosis, List<String> exactMatchDiagnosis, List<String> existDiagnosisInFDB,
			List<FdbDrugResult> drugDiseaseValidationResults) {
		drugList.forEach(reqDrug -> {
			List<Error> indicationRejections = validateDrugToDiseaseIndications(reqDrug, diagnosisCodes,
					wildCardDiagnosis, exactMatchDiagnosis);
			List<Error> contraIndicationRejections = validateDrugToDiseaseContraIndications(existDiagnosisInFDB,
					reqDrug);
			if ((indicationRejections != null && !indicationRejections.isEmpty())
					|| (contraIndicationRejections != null && !contraIndicationRejections.isEmpty())) {
				FdbDrugResult drugResult = new FdbDrugResult();
				drugResult.setDrugInfo(reqDrug);
				drugResult.setStatus(ServiceStatus.REJECTED.value());
				List<Error> rejectionReasons = new ArrayList<>();
				if (indicationRejections != null && !indicationRejections.isEmpty())
					rejectionReasons.addAll(indicationRejections);
				if ((contraIndicationRejections != null && !contraIndicationRejections.isEmpty()))
					rejectionReasons.addAll(contraIndicationRejections);
				drugResult.setRejectionReason(rejectionReasons);
				drugDiseaseValidationResults.add(drugResult);
			}
		});
	}

	private void checkIcdCodeExistence(String requestId, List<String> diagnosisCodes, List<String> wildCardDiagnosis,
			List<String> exactMatchDiagnosis, List<String> existDiagnosisInFDB) {

		diagnosisCodes.forEach(diagnosis -> {
			List<String> isIcdOrSubChapterExistInFDB = icdRepo.findIcdCodeV10AM(diagnosis);
			if (!isIcdOrSubChapterExistInFDB.isEmpty()) {
				existDiagnosisInFDB.add(diagnosis);
				// Split Diagnosis to two list to specify type of indications Match ..
				FdbdiagnosisIndicationConfig isSubChapterValidationEnabled = fdbDiagnosisIndicationRepository
						.findByICDCode(diagnosis);
				if (isSubChapterValidationEnabled != null
						&& isSubChapterValidationEnabled.getValidateSubChapters().equals("0")) {
					exactMatchDiagnosis.add(diagnosis);
				} else {
					wildCardDiagnosis.add(diagnosis);
				}

			} else {
				// Save the non existing diagnosis into oracle for recording purpose and
				// enhancing the system
				try {
					fdbNotExistDiagnosisRepo
							.save(new FdbNotExistDiagnosis(new FdbNotExistDiagnosisId(diagnosis, requestId)));
				} catch (Exception e) {
					log.warn("The ICD CODE "+diagnosis +" And Request ID "+ requestId +" Already Exist in FDB_NOT_EXIST_DIGNOSIS Tabel");
				}
			}
		});
	}

	private List<Error> validateDrugToDiseaseIndications(FdbDrugList reqDrug, List<String> diagnosisCodes,
			List<String> wildCardDiagnosis, List<String> exactMatchDiagnosis) {

		// Drug Should Be indicated To at least one Diagnosis ( Disease ) to be APPROVED
		List<Error> indicationRejections = new ArrayList<>();
		List<Indications> exactMatchIndictions = null;
		List<Indications> wildCardIndications = null;
		if (wildCardDiagnosis != null && !wildCardDiagnosis.isEmpty()) {
			wildCardIndications = validateDrugToDiseaseIndicationsUsingWildCard(wildCardDiagnosis, reqDrug);
		}
		if (exactMatchDiagnosis != null && !exactMatchDiagnosis.isEmpty()) {
			exactMatchIndictions = validateDrugToDiseaseIndicationsUsingExactMatch(exactMatchDiagnosis, reqDrug);
		}
		if ((exactMatchIndictions == null || exactMatchIndictions.isEmpty())
				&& (wildCardIndications == null || wildCardIndications.isEmpty())) {

			diagnosisCodes.forEach(diagnosis -> {
				Error indiRejection = new Error();
				indiRejection.setCode(FdbRejectionCodes.DRUG_TO_DISEASE_INDICATIONS_REJECTIONCODE.value());
				indiRejection.setDescription(commonRejectionReasonRepo
						.findByRejectionCode(FdbRejectionCodes.DRUG_TO_DISEASE_INDICATIONS_REJECTIONCODE.value())
						.replace("<DrugName> (<DrugCode>)",
								reqDrug.getIsDrugCodeMappedFromScientificCode() == true ? reqDrug.getScientificCode()
										: reqDrug.getDrugCode())
						.replace("<ICD>", diagnosis));
				indicationRejections.add(indiRejection);
			});
		}
		return indicationRejections;
	}

	private List<Indications> validateDrugToDiseaseIndicationsUsingExactMatch(List<String> exactMatchDiagnosis,
			FdbDrugList reqDrug) {
		return drugToDiseaseRepo.findIndicationUsingExactMatch(reqDrug.getGcnSeqNo(), exactMatchDiagnosis);
	}

	private List<Indications> validateDrugToDiseaseIndicationsUsingWildCard(List<String> wildCardDiagnosis,
			FdbDrugList reqDrug) {
		List<Indications> wildCardIndictions = new ArrayList<>();
		// Check Indication for each ICD one by one ..
		wildCardDiagnosis.forEach(diagnosis -> {
			List<Indications> isIndicatedDrug = drugToDiseaseRepo
					.findIndicationUsingWildcardMatch(reqDrug.getGcnSeqNo(), diagnosis);
			if (!isIndicatedDrug.isEmpty()) {
				wildCardIndictions.addAll(isIndicatedDrug);
			}
		});
		return wildCardIndictions;
	}

	private List<Error> validateDrugToDiseaseContraIndications(List<String> diagnosisCodes, FdbDrugList reqDrug) {
		List<Error> contraindicationRejecton = new ArrayList<>();
		List<ContraIndication> contraIndications = drugToDiseaseRepo
				.findContraIndicationByGcnSeqNoAndDiagnosisCodes(reqDrug.getGcnSeqNo(), diagnosisCodes);
		if (contraIndications != null && !contraIndications.isEmpty()) {
			contraIndications.forEach(contraIndication -> {
				Error conIndiRejection = new Error();
				conIndiRejection.setCode(FdbRejectionCodes.DRUG_TO_DISEASE_CONTRAINDICATIONS_REJECTIONCODE.value());
				conIndiRejection.setDescription(commonRejectionReasonRepo
						.findByRejectionCode(FdbRejectionCodes.DRUG_TO_DISEASE_CONTRAINDICATIONS_REJECTIONCODE.value())
						.replace("<DrugName> (<DrugCode>) ",
								reqDrug.getIsDrugCodeMappedFromScientificCode() == true ? reqDrug.getScientificCode()
										: reqDrug.getDrugCode())
						.replace("<ICD>", contraIndication.geticdCd()));
				contraindicationRejecton.add(conIndiRejection);
			});
		}
		return contraindicationRejecton;
	}
}