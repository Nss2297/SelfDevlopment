package com.waseel.pbm.fdbvalidationservice.service.manpulationservice;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdb.mkfi.core.DispensableGeneric;
import com.fdb.mkfi.core.UnitOfMeasure;
import com.fdb.mkfi.screening.FDBProfile;
import com.fdb.mkfi.screening.PatientAge;
import com.fdb.mkfi.screening.PatientWeight;
import com.fdb.mkfi.screening.ScreenDrug;
import com.waseel.pbm.fdbvalidationservice.dto.FdbDrugInfo;
import com.waseel.pbm.fdbvalidationservice.enums.ValidationMessages;
import com.waseel.pbm.fdbvalidationservice.exceptions.FdbException;
import com.waseel.pbm.fdbvalidationservice.model.DrugList;
import com.waseel.pbm.fdbvalidationservice.model.DssRequest;
import com.waseel.pbm.fdbvalidationservice.model.FdbDrugList;
import com.waseel.pbm.fdbvalidationservice.model.FdbRequest;
import com.waseel.pbm.fdbvalidationservice.model.PatientProfile;
import com.waseel.pbm.fdbvalidationservice.persist.mdss.DrugService;
import com.waseel.pbm.fdbvalidationservice.persist.mdss.DrugServiceMetaData;
import com.waseel.pbm.fdbvalidationservice.persist.mdss.FDBPediatricAgeSeverityLevel;
import com.waseel.pbm.fdbvalidationservice.persist.mdss.ServiceCodeGCNSeqNoMapping;
import com.waseel.pbm.fdbvalidationservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.pbm.fdbvalidationservice.repository.mdss.DrugServiceRepository;
import com.waseel.pbm.fdbvalidationservice.repository.mdss.FDBPediatricSeverityLevelRepository;
import com.waseel.pbm.fdbvalidationservice.repository.mdss.ServiceCodeGCNSeqNoMappingRepository;
import com.waseel.pbm.fdbvalidationservice.repository.medk_fdb.Rfmlinm1IcdDescRepository;
import com.waseel.pbm.fdbvalidationservice.repository.medk_fdb.Ripdat0ProductAttributeRepository;
import com.waseel.pbm.fdbvalidationservice.repository.medk_fdb.Ripdpp0ProductMasterRepository;

@Service
public class FdbRequestComposer {

	@Autowired
	Ripdat0ProductAttributeRepository productAttributeRepo;
	@Autowired
	Ripdpp0ProductMasterRepository productMasterRepo;
	@Autowired
	Rfmlinm1IcdDescRepository icdCodeRepo;

	@Autowired
	DataPopulationService validationService;

	@Autowired
	private ServiceCodeGCNSeqNoMappingRepository mappingRepo;

	@Autowired
	FDBPediatricSeverityLevelRepository fdbPediatricSeverityLevelRepository;

	@Autowired
	DrugServiceMetaDataRepository drugServiceMetaDataRepo;

	@Autowired
	DrugServiceRepository drugServiceRepo;

	public FdbRequest compose(DssRequest dssRequest) throws FdbException {
		FdbRequest fdbRequest = new FdbRequest();
		List<FdbDrugList> mappedDrugs = new ArrayList<>();
		fdbRequest.setRequestId(dssRequest.getRequestId());
		fdbRequest.setPatientProfile(
				new PatientProfile(initializeFdbProfile(dssRequest, mappedDrugs, fdbRequest, dssRequest.getPayerId()),
						dssRequest.getMemberGender()));
		fdbRequest.setDrugList(mappedDrugs);
		fdbRequest.setDiagnosisCodes(dssRequest.getIcdCodes());
		return fdbRequest;
	}

	private FDBProfile initializeFdbProfile(DssRequest dssRequest, List<FdbDrugList> mappedDrugs, FdbRequest fdbRequest,
			String payerId) throws FdbException {
		FDBProfile patientProfile = new FDBProfile();
		FDBProfile precautionProfile = new FDBProfile();
		FDBProfile nonPrecautionProfile = new FDBProfile();
		PatientAge patientAge = setPatientAge(dssRequest.getRequestId(), dssRequest.getDateOfBirth());
		patientProfile.setAge(patientAge);
		precautionProfile.setAge(patientAge);
		nonPrecautionProfile.setAge(patientAge);

		if (dssRequest.getMemberWeight() != null) {
			PatientWeight patientWeight = new PatientWeight(dssRequest.getMemberWeight(), UnitOfMeasure.KILOGRAM);
			patientProfile.setWeight(patientWeight);
			precautionProfile.setWeight(patientWeight);
			nonPrecautionProfile.setWeight(patientWeight);
		}
		patientProfile.addDrug(mapDrugList(dssRequest.getDrugList(), mappedDrugs, payerId, dssRequest.getRequestId(),
				dssRequest.getDateOfService(), precautionProfile, nonPrecautionProfile));
		fdbRequest.setPrecautionProfile(precautionProfile);
		fdbRequest.setNonPrecautionProfile(nonPrecautionProfile);
		return patientProfile;
	}

	private ScreenDrug[] mapDrugList(List<DrugList> dssReqDrugList, List<FdbDrugList> mappedDrugs, String payerId,
			String requestId, String serviceDate, FDBProfile precautionProfile, FDBProfile nonPrecautionProfile) {
		List<ScreenDrug> screenDrugsList = new ArrayList<>();
		ScreenDrug[] screenDrugs = null;
		Boolean isDrugCodeMappedFromScientificCode = false;

		for (DrugList reqDrug : dssReqDrugList) {

			if (reqDrug.getNdcDrugCode() == null && reqDrug.getScientificCode() != null
					&& !reqDrug.getScientificCode().isEmpty()) {
				reqDrug.setNdcDrugCode((mapScientificCodeToSfdaCode(reqDrug.getScientificCode(), serviceDate)));
				isDrugCodeMappedFromScientificCode = true;
			} else if (reqDrug.getNdcDrugCode().isEmpty() && reqDrug.getScientificCode() != null
					&& !reqDrug.getScientificCode().isEmpty()) {
				reqDrug.setNdcDrugCode((mapScientificCodeToSfdaCode(reqDrug.getScientificCode(), serviceDate)));
				isDrugCodeMappedFromScientificCode = true;
			} else {
				isDrugCodeMappedFromScientificCode = false;
			}

			FdbDrugInfo fdbProductInfo = getDrugInfo(reqDrug.getNdcDrugCode().trim());
			if (fdbProductInfo == null) {
				fdbProductInfo = getMappingDrugInfo(reqDrug.getNdcDrugCode().trim());
			}

			if (fdbProductInfo != null) {
				DispensableGeneric dispensableDrug = DispensableGeneric.getInstance(fdbProductInfo.getGcnSeqNo());

				ScreenDrug screenDrug = dispensableDrug.toScreenDrug();
				Optional<FDBPediatricAgeSeverityLevel> fdbPediatricSeverityLevelOptional = fdbPediatricSeverityLevelRepository
						.findByServiceCodeAndPayerIdAndSeverityLevel(payerId, reqDrug.getNdcDrugCode().trim());
				if (fdbPediatricSeverityLevelOptional.isPresent()) {
					precautionProfile.addDrug(screenDrug);
				} else {
					nonPrecautionProfile.addDrug(screenDrug);
				}
				screenDrugsList.add(screenDrug);
				DrugService drugInfo = getDrugInfo(reqDrug.getNdcDrugCode(), serviceDate);
				mappedDrugs.add(new FdbDrugList(reqDrug.getNdcDrugCode(), reqDrug.getScientificCode(),
						drugInfo.getIngredients(), drugInfo.getDisplay(), fdbProductInfo.getGcnSeqNo(),
						fdbProductInfo.getProductPackageUnit(), fdbProductInfo.getProductPackageSize(),
						drugInfo.getDosageForm(), drugInfo.getStrength(), drugInfo.getStrengthUnit(), dispensableDrug,
						reqDrug.getDispensedQuantity(), reqDrug.getAmount(), reqDrug.getDaysOfSupply(),
						isDrugCodeMappedFromScientificCode));

			}

		}

		if (!screenDrugsList.isEmpty()) {
			screenDrugs = new ScreenDrug[screenDrugsList.size()];
			screenDrugs = screenDrugsList.toArray(screenDrugs);
		}
		return screenDrugs;
	}

	private PatientAge setPatientAge(String requestId, String dateOfBirth) throws FdbException {
		PatientAge patientAge = null;
		try {
			patientAge = new PatientAge(convertStringToCalendar(dateOfBirth));
		} catch (Exception e) {
			List<String> invalidAgeRange = new ArrayList<>();
			invalidAgeRange.add(ValidationMessages.INVALID_AGE_RANGE.value().replace("<DOB> ", dateOfBirth.trim()));
			throw new FdbException(validationService.populateInvalidResponse(requestId, invalidAgeRange));
		}
		return patientAge;
	}

	private Calendar convertStringToCalendar(String dateOfBirth) throws ParseException {
		DateFormat formatter;
		Date date = null;
		formatter = new SimpleDateFormat("dd/MM/yyyy");
		date = formatter.parse(dateOfBirth);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	private FdbDrugInfo getDrugInfo(String drugCode) {
		Integer productId = null;
		FdbDrugInfo fdbDrugInfo = null;
		// 1- get product id
		productId = productAttributeRepo.findByProductProductAttributeValue(drugCode);
		// 2- get gcnsecno
		if (productId != null) {
			fdbDrugInfo = productMasterRepo.findGcnSeqNoAndProductPackageUnitByProductId(productId);
			if (fdbDrugInfo != null) {
				return fdbDrugInfo;
			}
		}
		return null;
	}

	private FdbDrugInfo getMappingDrugInfo(String drugCode) {

		ServiceCodeGCNSeqNoMapping scGcnSeqMapping = mappingRepo.findByserviceCode(drugCode.trim());
		if (scGcnSeqMapping != null) {
			return new FdbDrugInfo(scGcnSeqMapping.getGcnSeqNo(), scGcnSeqMapping.getProductPackageUnit(),
					scGcnSeqMapping.getProductPackageSize());
		}
		return null;
	}

	private String mapScientificCodeToSfdaCode(String scientificCode, String dateOfService) {
		List<DrugService> mappedDrugInfo = drugServiceRepo.findByScientificCodeAndDrugListId(scientificCode,
				getActiveSFDAList(dateOfService).get().getDrugListId());
		return mappedDrugInfo.get(0).getOtherCodesValue();
	}

	private DrugService getDrugInfo(String ndcDrugCode, String dateOfService) {		
		return drugServiceRepo.findByDrugCodeAndDrugListId(ndcDrugCode,
				getActiveSFDAList(dateOfService).get().getDrugListId()).get(0);
	}

	private Optional<DrugServiceMetaData> getActiveSFDAList(String dateOfService) {
		return drugServiceMetaDataRepo.findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(
				convertStringToDate(dateOfService));
	}

	private Date convertStringToDate(String dateOfService) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date effectiveDate = null;
		try {
			effectiveDate = formatter.parse(dateOfService);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return effectiveDate;
	}

}