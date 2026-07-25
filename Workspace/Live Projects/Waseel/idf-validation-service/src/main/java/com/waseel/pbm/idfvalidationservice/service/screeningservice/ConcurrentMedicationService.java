package com.waseel.pbm.idfvalidationservice.service.screeningservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.idfvalidationservice.enums.Severity;
import com.waseel.pbm.idfvalidationservice.model.DrugList;
import com.waseel.pbm.idfvalidationservice.model.EnumTypes.IdfRejectionCode;
import com.waseel.pbm.idfvalidationservice.model.Error;
import com.waseel.pbm.idfvalidationservice.persist.IDFConcurrentMedication;
import com.waseel.pbm.idfvalidationservice.repository.CommonRejectionReasonRepository;
import com.waseel.pbm.idfvalidationservice.repository.ConcurrentMedicationRepository;

@Service
public class ConcurrentMedicationService {

	@Autowired
	CommonRejectionReasonRepository commonRejectionReasonRepo;
	@Autowired
	ConcurrentMedicationRepository concurrentMedicationRepository;

	public void validate(DrugList drug, List<Error> errorList, List<String> drugList) {
		List<Error> errors = validateConcurrentMedication(drug, drugList);
		if (!errors.isEmpty())
			errorList.addAll(errors);
	}

	private List<Error> validateConcurrentMedication(DrugList drug, List<String> drugList) {
		List<Error> drugConcurrencyErrors = new ArrayList<>();
		Optional<List<IDFConcurrentMedication>> concurrentDrugs = concurrentMedicationRepository
				.findByServiceCode(drug.getNdcDrugCode());
		if (concurrentDrugs.isPresent()) {
			concurrentDrugs.get().forEach(concurrentDrug -> {
				if (drugList.contains(concurrentDrug.getId().getCuServiceCode()) && concurrentDrug.getSeverity() != null
						&& (concurrentDrug.getSeverity().equalsIgnoreCase(Severity.CONTRAINDICATION.value())
								|| concurrentDrug.getSeverity().equalsIgnoreCase(Severity.SEVERE.value()))) {
					List<Error> errorList = populateServiceErrorsList(drug.getNdcDrugCode(),
							concurrentDrug.getId().getCuServiceCode());
					drugConcurrencyErrors.addAll(errorList);
				}
			});
		}
		return drugConcurrencyErrors;
	}

	public List<Error> populateServiceErrorsList(String drug, String concurrentDrug) {
		List<Error> serviceErrorList = new ArrayList<>();
		Error serviceError = new Error();
		serviceError.setCode(IdfRejectionCode.CONCURRENT_DRUG.value());
		serviceError.setDescription(prepareRejectionDescription(drug, concurrentDrug));
		serviceErrorList.add(serviceError);
		return serviceErrorList;
	}

	private String prepareRejectionDescription(String drug, String concurrentDrug) {
		String rejectionDescription = commonRejectionReasonRepo
				.findByRejectionCode(IdfRejectionCode.CONCURRENT_DRUG.value());
		return rejectionDescription.replace("<DrugName> (<DrugCode>)", drug)
				.replace("<InteractedDrugName> (<InteractedDrugCode>)", concurrentDrug);
	}
}
