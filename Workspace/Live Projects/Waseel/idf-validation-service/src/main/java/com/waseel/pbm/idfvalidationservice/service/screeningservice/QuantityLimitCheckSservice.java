package com.waseel.pbm.idfvalidationservice.service.screeningservice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.idfvalidationservice.enums.PackageUnits;
import com.waseel.pbm.idfvalidationservice.model.DrugList;
import com.waseel.pbm.idfvalidationservice.model.DssRequest;
import com.waseel.pbm.idfvalidationservice.model.EnumTypes.IdfRejectionCode;
import com.waseel.pbm.idfvalidationservice.model.Error;
import com.waseel.pbm.idfvalidationservice.persist.ChronicDzDrugAssoc;
import com.waseel.pbm.idfvalidationservice.persist.IDFQuantityLimitCheck;
import com.waseel.pbm.idfvalidationservice.repository.ChronicDzDrugAssocRepository;
import com.waseel.pbm.idfvalidationservice.repository.CommonMedicalConfigRepository;
import com.waseel.pbm.idfvalidationservice.repository.CommonRejectionReasonRepository;
import com.waseel.pbm.idfvalidationservice.repository.IDFQuantityLimitCheckRepository;
import com.waseel.pbm.idfvalidationservice.repository.MemberChronicDzAssocRepository;
import com.waseel.pbm.idfvalidationservice.service.PatientAgeConverterService;

@Service
public class QuantityLimitCheckSservice {

	@Autowired
	private IDFQuantityLimitCheckRepository quantityLimitCheckRepository;

	@Autowired
	private PatientAgeConverterService ageConverterService;

	@Autowired
	private CommonRejectionReasonRepository commonRejectionReasonRepo;

	@Autowired
	private MemberChronicDzAssocRepository memberChronicDzAssocRepo;

	@Autowired
	private ChronicDzDrugAssocRepository chronicDzDrugAssocRepo;

	@Autowired
	private CommonMedicalConfigRepository commonMedicalConfigRepo;

	public void validate(DssRequest request, DrugList drug, List<Error> errorList) {
		List<Error> errors = validateQuantityLimitCheck(request, drug);
		if (!errors.isEmpty())
			errorList.addAll(errors);
	}

	private List<Error> validateQuantityLimitCheck(DssRequest request, DrugList drug) {
		List<Error> quantityLimitErrors = new ArrayList<>();
		List<Error> errorList = new ArrayList<>();
		if (drug.getDaysOfSupply() != null && !StringUtils.isBlank(drug.getDaysOfSupply())) {
			IDFQuantityLimitCheck idfQL = validateAgeInDays(request.getDateOfBirth(), drug.getNdcDrugCode());
			if (idfQL != null) {

				BigDecimal dispensedQuantityPerDay;

				if (!drug.getDaysOfSupply().equalsIgnoreCase("0")) {
					dispensedQuantityPerDay = drug.getDispensedQuantity().divide(new BigDecimal(drug.getDaysOfSupply()),
							2, RoundingMode.HALF_UP);

				} else {
					dispensedQuantityPerDay = drug.getDispensedQuantity();
				}

				int quantityCheckCompareResult = dispensedQuantityPerDay
						.compareTo(new BigDecimal(idfQL.getMaxQuantityLimitInDays()));
				if (quantityCheckCompareResult == 1)
					errorList.addAll(
							populateServiceErrorsList(drug.getNdcDrugCode(), IdfRejectionCode.QUANTITY_LIMIT_CHECK));

				validateDaysOfSupply(request, idfQL, drug, errorList);

			}
		}
		if (!errorList.isEmpty())
			quantityLimitErrors.addAll(errorList);
		return quantityLimitErrors;
	}

	private void validateDaysOfSupply(DssRequest request, IDFQuantityLimitCheck idfQL, DrugList drug,
			List<Error> errorList) {
		// check if the patient flagged as chronic based
		List<Integer> chronicDzIds = memberChronicDzAssocRepo.findByMemberId(request.getMemberId());
		if (chronicDzIds != null && !chronicDzIds.isEmpty()) {
			List<ChronicDzDrugAssoc> ChronicDzAssociatedDrugResults = chronicDzDrugAssocRepo
					.findByChronicDzInformationAndServiceCode(chronicDzIds, drug.getNdcDrugCode());
			if (ChronicDzAssociatedDrugResults != null && !ChronicDzAssociatedDrugResults.isEmpty()) {
				if (Double.parseDouble(drug.getDaysOfSupply()) > Double.parseDouble(
						commonMedicalConfigRepo.findById("MAX_DAYS_OF_SUPPLY_CHRONIC_DISEASE").get().getValue())) {
					errorList.addAll(populateServiceErrorsList(drug.getNdcDrugCode(), IdfRejectionCode.DAYS_OF_SUPPLY));
				}
			} else {
				if ((idfQL.getMaxDurationInDays() != null && idfQL.getMaxDurationInDays() != 0)
						&& (Double.parseDouble(drug.getDaysOfSupply()) > idfQL.getMaxDurationInDays())) {
					errorList.addAll(populateServiceErrorsList(drug.getNdcDrugCode(), IdfRejectionCode.DAYS_OF_SUPPLY));
				}
			}

		} else {
			if ((idfQL.getMaxDurationInDays() != null && idfQL.getMaxDurationInDays() != 0)
					&& (Double.parseDouble(drug.getDaysOfSupply()) > idfQL.getMaxDurationInDays())) {
				errorList.addAll(populateServiceErrorsList(drug.getNdcDrugCode(), IdfRejectionCode.DAYS_OF_SUPPLY));
			}
		}
	}

	private IDFQuantityLimitCheck validateAgeInDays(String birthDate, String drugCode) {
		List<IDFQuantityLimitCheck> idfQuantityLimitCheck = quantityLimitCheckRepository
				.findByIdServiceCodeAndProductPackageUnitEquals(drugCode, PackageUnits.SOLID.value());
		Integer ageInDays = ageConverterService.patientAgeConverter(birthDate);
		if (ageInDays != null) {
			return idfQuantityLimitCheck.stream().filter(age -> ageInDays >= age.getId().getFromAgeDurationInDays()
					&& ageInDays <= age.getId().getToAgeDurationInDays()).findFirst().orElse(null);
		}

		return null;
	}

	public List<Error> populateServiceErrorsList(String serviceCode, IdfRejectionCode rejectionCode) {
		List<Error> serviceErrorList = new ArrayList<>();
		Error serviceError = new Error();
		if (rejectionCode.value().equals(IdfRejectionCode.QUANTITY_LIMIT_CHECK.value())) {
			serviceError.setCode(IdfRejectionCode.QUANTITY_LIMIT_CHECK.value());
			serviceError.setDescription(prepareRejectionDescForQuantityLimit(serviceCode));

		} else if (rejectionCode.value().equals(IdfRejectionCode.DAYS_OF_SUPPLY.value())) {
			serviceError.setCode(IdfRejectionCode.DAYS_OF_SUPPLY.value());
			serviceError.setDescription(prepareRejectionDescForDaysOfSupply(serviceCode));
		}
		serviceErrorList.add(serviceError);
		return serviceErrorList;
	}

	private String prepareRejectionDescForQuantityLimit(String serviceCode) {
		String rejectionDescription = commonRejectionReasonRepo
				.findByRejectionCode(IdfRejectionCode.QUANTITY_LIMIT_CHECK.value());
		return rejectionDescription.replace("<DrugName> (<DrugCode>)", serviceCode).replace("<UnitType>", "day");
	}

	private String prepareRejectionDescForDaysOfSupply(String serviceCode) {
		String rejectionDescription = commonRejectionReasonRepo
				.findByRejectionCode(IdfRejectionCode.DAYS_OF_SUPPLY.value());
		return rejectionDescription.replace("<DrugName> (<DrugCode>)", serviceCode);
	}
}
