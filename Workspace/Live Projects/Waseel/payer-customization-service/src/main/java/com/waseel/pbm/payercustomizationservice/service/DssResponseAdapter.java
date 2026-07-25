package com.waseel.pbm.payercustomizationservice.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.waseel.pbm.payercustomizationservice.enums.RejectionCode;
import com.waseel.pbm.payercustomizationservice.enums.RequestStatus;
import com.waseel.pbm.payercustomizationservice.enums.ServiceStatus;
import com.waseel.pbm.payercustomizationservice.model.DssRequest;
import com.waseel.pbm.payercustomizationservice.model.DssResponse;
import com.waseel.pbm.payercustomizationservice.model.Error;
import com.waseel.pbm.payercustomizationservice.model.Result;

@Service
public class DssResponseAdapter {

	int rejectedDrugsCount;

	public DssResponse combineResponse(DssResponse drugToDiagnosisResponse, DssResponse qlcResponse,
			DssResponse ageResponse, DssResponse genderResponse, DssResponse drugToDrugResponse,
			DssResponse duplicateTherapyResponse, DssRequest dssRequest) {
		rejectedDrugsCount = 0;
		DssResponse dssResponse = new DssResponse();
		List<Result> drugResults = new ArrayList<>();
		dssResponse.setRequestId(dssRequest.getRequestId());
		dssRequest.getDrugList().forEach(drug -> {
			Result drugResult = new Result();
			List<Error> rejectionReasons = new ArrayList<>();
			drugResult.setNdcDrugCode(drug.getNdcDrugCode());
			drugResult.setDispensedQuantity(drug.getDispensedQuantity());
			drugResult.setAmount(drug.getAmount());
			drugResult.setScientificCode(drug.getScientificCode());
			if (!StringUtils.isBlank(drug.getDaysOfSupply()))
				drugResult.setDaysOfSupply(drug.getDaysOfSupply());
			
			validateRejectionReason(drugToDiagnosisResponse, qlcResponse, ageResponse, genderResponse,
					drugToDrugResponse, duplicateTherapyResponse, drugResult.getNdcDrugCode(), rejectionReasons,
					drugResult.getScientificCode());
			
			if (!rejectionReasons.isEmpty()) {
				drugResult.setStatus(ServiceStatus.REJECTED.toString());
				Set<Error> uniqueErrorSet = rejectionReasons.stream()
						.collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Error::getDescription)
								.thenComparing(Comparator.comparing(Error::getCode)))));
				rejectionReasons = uniqueErrorSet.stream().collect(Collectors.toList());
				drugResult.setErrors(rejectionReasons);
				rejectedDrugsCount++;
			} else {
				drugResult.setStatus(ServiceStatus.APPROVED.toString());
			}
			drugResults.add(drugResult);

		});

		dssResponse.setResults(drugResults);
		if (rejectedDrugsCount < 1) {
			dssResponse.setStatus(RequestStatus.APPROVED.value());
		} else if (rejectedDrugsCount == dssRequest.getDrugList().size()) {
			dssResponse.setStatus(RequestStatus.REJECTED.value());
		} else {
			dssResponse.setStatus(RequestStatus.PARTIAL_APPROVED.value());
		}
		dssResponse.setErrors(!setDssResponseErrors(dssResponse).isEmpty() ? setDssResponseErrors(dssResponse) : null);
		dssResponse.setHttpStatusCode(200);
		return dssResponse;
	}
	
	private void validateRejectionReason(DssResponse drugToDiagnosisResponse, DssResponse qlcResponse,
			DssResponse ageResponse, DssResponse genderResponse, DssResponse drugToDrugResponse,
			DssResponse duplicateTherapyResponse,String serviceCode,List<Error> rejectionReasons,String scientificCode) {
		List<Error> removeErrorList = new ArrayList<>();
		if (drugToDiagnosisResponse != null) {
			checkResponse(null, qlcResponse, ageResponse, genderResponse, drugToDrugResponse,
					duplicateTherapyResponse, removeErrorList);
			addRejectionReason(drugToDiagnosisResponse, serviceCode, rejectionReasons,scientificCode);
		}
		if (qlcResponse != null) {
			checkResponse(drugToDiagnosisResponse, null, ageResponse, genderResponse, drugToDrugResponse,
					duplicateTherapyResponse, removeErrorList);
			addRejectionReason(qlcResponse, serviceCode, rejectionReasons,scientificCode);
		}
		if (ageResponse != null) {
			checkResponse(drugToDiagnosisResponse, qlcResponse, null, genderResponse, drugToDrugResponse,
					duplicateTherapyResponse, removeErrorList);
			addRejectionReason(ageResponse, serviceCode, rejectionReasons,scientificCode);
		}
		if (genderResponse != null) {
			checkResponse(drugToDiagnosisResponse, qlcResponse, ageResponse, null, drugToDrugResponse,
					duplicateTherapyResponse, removeErrorList);
			addRejectionReason(genderResponse, serviceCode, rejectionReasons,scientificCode);
		}

		if (drugToDrugResponse != null) {
			checkResponse(drugToDiagnosisResponse, qlcResponse, ageResponse, genderResponse, null,
					duplicateTherapyResponse, removeErrorList);
			addRejectionReason(drugToDrugResponse, serviceCode, rejectionReasons,scientificCode);
		}

		if (duplicateTherapyResponse != null) {
			checkResponse(drugToDiagnosisResponse, qlcResponse, ageResponse, genderResponse, drugToDrugResponse,
					null, removeErrorList);
			addRejectionReason(duplicateTherapyResponse, serviceCode, rejectionReasons,scientificCode);
		}
		
		if (!removeErrorList.isEmpty()) {
			rejectionReasons.removeAll(removeErrorList);
		}
	}

	private void checkResponse(DssResponse drugToDiagnosisResponse, DssResponse qlcResponse, DssResponse ageResponse,
			DssResponse genderResponse, DssResponse drugToDrugResponse, DssResponse duplicateTherapyResponse,
			List<Error> removeErrorList) {
		List<String> modules = new ArrayList<>();
		addModules(drugToDiagnosisResponse, qlcResponse, ageResponse, genderResponse, drugToDrugResponse,
				duplicateTherapyResponse, modules);
		if (drugToDiagnosisResponse != null) {
			validateErrors(drugToDiagnosisResponse, removeErrorList, modules);
		}
		if (qlcResponse != null) {
			validateErrors(qlcResponse, removeErrorList, modules);
		}
		if (ageResponse != null) {
			validateErrors(ageResponse, removeErrorList, modules);
		}
		if (genderResponse != null) {
			validateErrors(genderResponse, removeErrorList, modules);
		}
		if (duplicateTherapyResponse != null) {
			validateErrors(duplicateTherapyResponse, removeErrorList, modules);
		}
		if (drugToDrugResponse != null) {
			validateErrors(drugToDrugResponse, removeErrorList, modules);
		}
	}

	private void addModules(DssResponse drugToDiagnosisResponse, DssResponse qlcResponse, DssResponse ageResponse,
			DssResponse genderResponse, DssResponse drugToDrugResponse, DssResponse duplicateTherapyResponse,
			List<String> modules) {
		if (drugToDiagnosisResponse == null) {
			modules.add(RejectionCode.IDF_CONTRAINDICATION.code());
			modules.add(RejectionCode.FDB_CONTRAINDICATION.code());
			modules.add(RejectionCode.IDF_INDICATION.code());
			modules.add(RejectionCode.FDB_INDICATION.code());
		}
		if (qlcResponse == null) {
			modules.add(RejectionCode.FDB_QUANTITY_LIMIT_CHECK.code());
			modules.add(RejectionCode.IDF_QUANTITY_LIMIT_CHECK.code());
		}
		if (ageResponse == null) {
			modules.add(RejectionCode.FDB_AGE.code());
			modules.add(RejectionCode.IDF_AGE.code());
		}
		if (genderResponse == null) {
			modules.add(RejectionCode.FDB_GENDER.code());
			modules.add(RejectionCode.IDF_GENDER.code());
		}
		if (duplicateTherapyResponse == null) {
			modules.add(RejectionCode.FDB_DUPLICATE_THERAPY.code());
			modules.add(RejectionCode.IDF_DUPLICATE_THERAPY.code());
		}
		if (drugToDrugResponse == null) {
			modules.add(RejectionCode.FDB_DRUG_TO_DRUG.code());
			modules.add(RejectionCode.IDF_DRUG_TO_DRUG.code());
		}
	}

	private void validateErrors(DssResponse dssResponse, List<Error> removeErrorList, List<String> modules) {
		if (dssResponse != null) {
			dssResponse.getResults().forEach(res -> {
				if (res.getStatus().equals(ServiceStatus.REJECTED.value())) {
					res.getErrors().forEach(e -> {
						if (modules.contains(e.getCode())) {
							removeErrorList.add(e);
						}
					});
				}
			});
		}
	}

	private void addRejectionReason(DssResponse response, String drugResultServiceCode, List<Error> rejectionReasons,
			String scientificCode) {
		for (Result result : response.getResults()) {
			if (result.getErrors() != null && (matchesCode(result.getNdcDrugCode(), drugResultServiceCode)
					|| matchesCode(result.getScientificCode(), scientificCode))) {
				rejectionReasons.addAll(result.getErrors());
				break;
			}
		}
	}

	private List<String> setDssResponseErrors(DssResponse dssResponse) {
		List<String> responseErrors = new ArrayList<>();
		for (Result drugResult : dssResponse.getResults()) {
			if (drugResult.getStatus().equals(ServiceStatus.REJECTED.toString())) {
				for (Error rejectionReason : drugResult.getErrors()) {
					responseErrors.add(rejectionReason.getDescription());
				}
			}
		}
		return responseErrors;
	}
	
	private boolean matchesCode(String code, String targetCode) {
	    return StringUtils.isNotBlank(code) && Objects.equals(code, targetCode);
	}
}
