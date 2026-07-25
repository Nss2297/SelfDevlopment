package com.waseel.pbm.fdbvalidationservice.service.screeningservice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdb.mkfi.core.dosing.DoseModule;
import com.fdb.mkfi.core.dosing.DoseRecord;
import com.fdb.mkfi.core.dosing.DoseRecords;
import com.waseel.pbm.fdbvalidationservice.enums.FdbRejectionCodes;
import com.waseel.pbm.fdbvalidationservice.enums.ServiceStatus;
import com.waseel.pbm.fdbvalidationservice.model.DrugDose;
import com.waseel.pbm.fdbvalidationservice.model.Error;
import com.waseel.pbm.fdbvalidationservice.model.FdbDrugList;
import com.waseel.pbm.fdbvalidationservice.model.FdbDrugResult;
import com.waseel.pbm.fdbvalidationservice.model.FdbRequest;
import com.waseel.pbm.fdbvalidationservice.model.FdbResponse;
import com.waseel.pbm.fdbvalidationservice.repository.mdss.CommonRejectionReasonRepository;

@Service
public class QuantityLimitCheckService {
	@Autowired
	CommonRejectionReasonRepository commonRejectionReasonRepo;

	@SuppressWarnings("unused")
	public FdbResponse validate(FdbRequest fdbRequest) {

		FdbResponse quantityLimitCheckResponse = new FdbResponse();
		List<FdbDrugList> cumulativeDrugList = new ArrayList<>();
		List<FdbDrugList> nonCumulativeDrugList = new ArrayList<>();
		List<FdbDrugResult> quantityLimitCheckResults = new ArrayList<>();

		quantityLimitCheckResponse.setRequestId(fdbRequest.getRequestId());

		if (fdbRequest.getPatientProfile().getFdbProfile().getAge().getAgeInYears() >= 18
				&& fdbRequest.getPatientProfile().getFdbProfile().getAge().getAgeInYears() <= 64) {

			classifyRequestedDrugs(fdbRequest.getDrugList(), cumulativeDrugList, nonCumulativeDrugList);

			if (cumulativeDrugList != null && !cumulativeDrugList.isEmpty()) {
				validateQuantityLimitForCumulativeDrugList(cumulativeDrugList,
						fdbRequest.getPatientProfile().getFdbProfile().getAge().getAgeInDays(),
						quantityLimitCheckResults);
			}

			if (nonCumulativeDrugList != null && !nonCumulativeDrugList.isEmpty()) {
				validateQuantityLimitForNonCumulativeDrugList(nonCumulativeDrugList,
						fdbRequest.getPatientProfile().getFdbProfile().getAge().getAgeInDays(),
						quantityLimitCheckResults);
			}

		}

		if (!quantityLimitCheckResults.isEmpty()) {
			quantityLimitCheckResponse.setDrugResults(quantityLimitCheckResults);
			return quantityLimitCheckResponse;
		}
		return null;

	}

	private void classifyRequestedDrugs(List<FdbDrugList> drugList, List<FdbDrugList> cumulativeDrugList,
			List<FdbDrugList> nonCumulativeDrugList) {

		cumulativeDrugList.addAll(drugList.stream().filter(drug1 -> drugList.stream()
				.anyMatch(drug2 -> drug1.getDaysOfSupply() != null && !drug1.getDaysOfSupply().isBlank()
						&& !drug1.getDaysOfSupply().equalsIgnoreCase("0") && drug2.getDaysOfSupply() != null
						&& !drug2.getDaysOfSupply().isBlank() && !drug2.getDaysOfSupply().equalsIgnoreCase("0")
						&& drug1.getScientificName() != null && !drug1.getScientificName().isBlank()
						&& drug2.getScientificName() != null && !drug2.getScientificName().isBlank()
						&& drug1.getStrength() != null && !drug1.getStrength().isBlank() && drug2.getStrength() != null
						&& !drug2.getStrength().isBlank() && drug1.getDispensableGeneric() != null
						&& drug2.getDispensableGeneric() != null && drug1.getDispensableGeneric().getDoseForm() != null
						&& drug2.getDispensableGeneric().getDoseForm() != null
						&& drug1.getDispensableGeneric().getDoseForm().getDescription() != null
						&& drug2.getDispensableGeneric().getDoseForm().getDescription() != null
						&& drug1.getScientificName().equalsIgnoreCase(drug2.getScientificName())
						&& drug1.getDispensableGeneric().getDoseForm().getDescription()
								.equals(drug2.getDispensableGeneric().getDoseForm().getDescription())
						&& drug1.getDaysOfSupply().equalsIgnoreCase(drug2.getDaysOfSupply())
						&& !drug1.getStrength().contains(",") && !drug2.getStrength().contains(",")
						&& !drug1.getScientificName().contains(",") && !drug2.getScientificName().contains(",")
						&& !drug1.getDrugCode().equalsIgnoreCase(drug2.getDrugCode())))

				.collect(Collectors.toList()));

		nonCumulativeDrugList.addAll(drugList.stream().filter(element -> !cumulativeDrugList.contains(element))
				.collect(Collectors.toList()));


	}

	private void validateQuantityLimitForCumulativeDrugList(List<FdbDrugList> cumulativeDrugList, int ageInDays,
			List<FdbDrugResult> quantityLimitCheckResults) {

		List<List<FdbDrugList>> splittedCumulativeDrugLists = new ArrayList<>();
		splitComulativeDrugList(cumulativeDrugList, splittedCumulativeDrugLists);

		splittedCumulativeDrugLists.forEach(CumulativeDrugs -> {

			BigDecimal commonMaxDosePerDay, commonMaxDosePerDaysOfSupply = null;
			Boolean proceedWithQLCValidation = false;
			List<Error> cumulativeQuantityLimitCheckRejection = null;

			// get max strength for one drug of list only as its common for all drugs
			DoseRecords doseRecords = CumulativeDrugs.get(0).getDispensableGeneric().getDosing(DoseModule.MinMax);
			for (DoseRecord doseRecord : doseRecords) {
				if ((ageInDays >= doseRecord.getPatientAgeRange().getLow().getAgeInDays())
						&& (ageInDays <= (doseRecord.getPatientAgeRange().getHigh().getAgeInDays()))) {
					if (doseRecord.isExcluded() == false) {
						commonMaxDosePerDay = doseRecord.getDoseThresholdRanges().getMetric().getHigh().getAmount();
						commonMaxDosePerDaysOfSupply = commonMaxDosePerDay
								.multiply(new BigDecimal(CumulativeDrugs.get(0).getDaysOfSupply()));
						proceedWithQLCValidation = true;
					}
				}
			}

			if (proceedWithQLCValidation == true) {
				// calculate the requested dose = Quantity * strength for all drugs
				List<DrugDose> requestedDrugsDoses = new ArrayList<>();
				CumulativeDrugs.forEach(reqDrug -> {
					BigDecimal requestedDose = reqDrug.getDispensedQuantity()
							.multiply(reqDrug.getDispensableGeneric().getStrength().getAmount());
					requestedDrugsDoses.add(new DrugDose(reqDrug, requestedDose));
				});

				// get the max of requested doses
				DrugDose maxDrugDose = Collections.max(requestedDrugsDoses,
						Comparator.comparing(s -> s.getRequestedDose()));

				// get the maximum allowable packages per days of supply
				BigDecimal maxAllowablePackagesPerDaysOfSupply = (commonMaxDosePerDaysOfSupply
						.divide(maxDrugDose.getRequestedDose(), 2, RoundingMode.CEILING))
						.setScale(0, RoundingMode.CEILING);

				// get max allowable dose based on the maximum allowable packages

				BigDecimal maxAllowableDoes = maxAllowablePackagesPerDaysOfSupply
						.multiply(maxDrugDose.getRequestedDose());

				// calculate the total requested doses ..
				BigDecimal totalRequestedDose = new BigDecimal("0");
				String requestedDrugCodes = null;
				for (DrugDose reDrugDose : requestedDrugsDoses) {
					totalRequestedDose = totalRequestedDose.add(reDrugDose.getRequestedDose());
					if (requestedDrugCodes == null) {
						requestedDrugCodes = reDrugDose.getDrug().getDrugCode();
					} else {
						requestedDrugCodes = requestedDrugCodes + "," + reDrugDose.getDrug().getDrugCode();
					}
				}

				// compare the total Requested Drugs with the maximum allowed dose
				int compareResult = totalRequestedDose.compareTo(maxAllowableDoes);
				if (compareResult == 1) {
					cumulativeQuantityLimitCheckRejection = setCumulativeQuantityLimitRejectionReasons(
							requestedDrugCodes);
					if (cumulativeQuantityLimitCheckRejection != null
							&& !cumulativeQuantityLimitCheckRejection.isEmpty()) {
						for (FdbDrugList reqDrug : CumulativeDrugs) {
							FdbDrugResult drugResult = new FdbDrugResult();
							drugResult.setDrugInfo(reqDrug);
							drugResult.setStatus(ServiceStatus.REJECTED.value());
							drugResult.setRejectionReason(cumulativeQuantityLimitCheckRejection);
							quantityLimitCheckResults.add(drugResult);
						}

					}

				}

			}
		});

	}

	private void splitComulativeDrugList(List<FdbDrugList> cumulativeDrugList,
			List<List<FdbDrugList>> splittedCumulativeDrugLists) {

		List<FdbDrugList> splittedDrugList = null;

		for (FdbDrugList drug1 : cumulativeDrugList) {
			splittedDrugList = new ArrayList<>();

			// check if the drug already exist in the splittedCumulativeDrugList ..
			if (doesTheDrugExisit(drug1, splittedCumulativeDrugLists) == false) {
				splittedDrugList.add(drug1);
				for (FdbDrugList drug2 : cumulativeDrugList) {
					if (drug1.getScientificName().equalsIgnoreCase(drug2.getScientificName())
							&& drug1.getDispensableGeneric().getDoseForm().getDescription()
									.equals(drug2.getDispensableGeneric().getDoseForm().getDescription())
							&& drug1.getDaysOfSupply().equalsIgnoreCase(drug2.getDaysOfSupply())
							&& !drug1.getDrugCode().equalsIgnoreCase(drug2.getDrugCode())) {
						splittedDrugList.add(drug2);
					}
				}
			}
			if (splittedDrugList != null && !splittedDrugList.isEmpty()) {
				splittedCumulativeDrugLists.add(splittedDrugList);
			}
		}
	}

	private boolean doesTheDrugExisit(FdbDrugList drug, List<List<FdbDrugList>> splittedCumulativeDrugLists) {
		boolean doesTheDrugExisit = false;
		for (List<FdbDrugList> splittedCumulativeDrugList : splittedCumulativeDrugLists) {
			if (splittedCumulativeDrugList.contains(drug) == true) {
				doesTheDrugExisit = true;
				break;
			}
		}
		return doesTheDrugExisit;
	}

	private void validateQuantityLimitForNonCumulativeDrugList(List<FdbDrugList> nonCumulativeDrugList, int ageInDays,
			List<FdbDrugResult> quantityLimitCheckResults) {

		List<Error> nonCumulativeQuantityLimitCheckRejection = null;

		for (FdbDrugList reqDrug : nonCumulativeDrugList) {

			if ((reqDrug.getProductPackageUnit().equals("1") || reqDrug.getProductPackageUnit().equals("2")
					|| reqDrug.getProductPackageUnit().equals("3"))
					&& (reqDrug.getDaysOfSupply() != null && !reqDrug.getDaysOfSupply().isBlank())) {

				DoseRecords doseRecords = reqDrug.getDispensableGeneric().getDosing(DoseModule.MinMax);

				for (DoseRecord doseRecord : doseRecords) {
					if ((ageInDays >= doseRecord.getPatientAgeRange().getLow().getAgeInDays())
							&& (ageInDays <= (doseRecord.getPatientAgeRange().getHigh().getAgeInDays()))) {
						if (doseRecord.isExcluded() == false) {

							// 1- Calculate The Maximum Quantity Per Days of Supply
							BigDecimal maxQuantityPerDaysOfSupply;
							if (reqDrug.getDaysOfSupply().equalsIgnoreCase("0"))
								maxQuantityPerDaysOfSupply = doseRecord.getDoseThresholdRanges().getForm().getHigh()
										.getAmount();
							else
								maxQuantityPerDaysOfSupply = (doseRecord.getDoseThresholdRanges().getForm().getHigh()
										.getAmount().multiply(new BigDecimal(reqDrug.getDaysOfSupply())));

							// 2- Calculate The Maximum Allowed Package Quantity
							BigDecimal maxAllowedPacakgeQuantityPerDaysOfSupply = (maxQuantityPerDaysOfSupply
									.divide(new BigDecimal(reqDrug.getProductPackageSize()), 2, RoundingMode.CEILING))
									.setScale(0, RoundingMode.CEILING);

							// 3- Calculate The Maximum Allowed Units Quantity based on the max Allowed
							// Pacakge Quantity
							BigDecimal maxAllowedUnitsQuantityPerDaysOfSupply = maxAllowedPacakgeQuantityPerDaysOfSupply
									.multiply(new BigDecimal(reqDrug.getProductPackageSize()));

							// 4- Compare the dispensedQuantity with maxAllowedUnitsQuantity
							int compareResult = (getDispensedQuantity(reqDrug))
									.compareTo(maxAllowedUnitsQuantityPerDaysOfSupply);
							if (compareResult == 1) {
								nonCumulativeQuantityLimitCheckRejection = setNonCumulativeQuantityLimitRejectionReasons(
										reqDrug.getDispensableGeneric().toString(), reqDrug.getDrugCode());
								break;
							}
						}
					}
				}
			}

			if (nonCumulativeQuantityLimitCheckRejection != null
					&& !nonCumulativeQuantityLimitCheckRejection.isEmpty()) {
				FdbDrugResult drugResult = new FdbDrugResult();
				drugResult.setDrugInfo(reqDrug);
				drugResult.setStatus(ServiceStatus.REJECTED.value());
				drugResult.setRejectionReason(nonCumulativeQuantityLimitCheckRejection);
				quantityLimitCheckResults.add(drugResult);
			}
		}

	}

	private BigDecimal getDispensedQuantity(FdbDrugList reqDrug) {

		if (reqDrug.getProductPackageUnit().equals("1")) {
			return reqDrug.getDispensedQuantity();
		} else if (reqDrug.getProductPackageUnit().equals("2") || reqDrug.getProductPackageUnit().equals("3")) {
			return reqDrug.getDispensedQuantity().multiply(new BigDecimal(reqDrug.getProductPackageSize()));
		}
		return new BigDecimal(-1);
	}

	private List<Error> setNonCumulativeQuantityLimitRejectionReasons(String drugName, String drugCode) {
		List<Error> genderRejectons = new ArrayList<>();
		Error genderRejecton = new Error();
		genderRejecton.setCode(FdbRejectionCodes.NON_CUMULATIVE_QUANTITY_LIMIT_CHECK_REJECTIONCODE.value());
		genderRejecton.setDescription(commonRejectionReasonRepo
				.findByRejectionCode(FdbRejectionCodes.NON_CUMULATIVE_QUANTITY_LIMIT_CHECK_REJECTIONCODE.value())
				.replace("<DrugName> (<DrugCode>)", drugCode).replace("<UnitType>", "DAY"));
		genderRejectons.add(genderRejecton);
		return genderRejectons;
	}

	private List<Error> setCumulativeQuantityLimitRejectionReasons(String cumulativeDrugs) {
		List<Error> genderRejectons = new ArrayList<>();
		Error genderRejecton = new Error();
		genderRejecton.setCode(FdbRejectionCodes.CUMULATIVE_QUANTITY_LIMIT_CHECK_REJECTIONCODE.value());
		genderRejecton.setDescription(commonRejectionReasonRepo
				.findByRejectionCode(FdbRejectionCodes.CUMULATIVE_QUANTITY_LIMIT_CHECK_REJECTIONCODE.value())
				.replace("<drugs>", cumulativeDrugs).replace("<UnitType>", "DAY"));
		genderRejectons.add(genderRejecton);
		return genderRejectons;
	}
}