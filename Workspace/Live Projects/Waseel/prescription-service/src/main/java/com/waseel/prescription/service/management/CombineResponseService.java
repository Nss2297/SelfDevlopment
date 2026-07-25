package com.waseel.prescription.service.management;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.waseel.prescription.model.br.SensitiveDrugResponseModel;
import com.waseel.prescription.model.dss.DssResponse;
import com.waseel.prescription.model.dss.Error;
import com.waseel.prescription.model.dss.Result;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.exclusion.DrugExclusionResponseModel;
import com.waseel.prescription.model.formulary.DrugFormularyResponseModel;

@Service
public class CombineResponseService {

	private static final Logger log = LoggerFactory.getLogger(CombineResponseService.class);

	public void combineResponseWithDssResponse(DssResponse dssResponse,
			List<DrugFormularyResponseModel> drugFormularyResponseModelList,
			DrugExclusionResponseModel drugExclusionResponseModel, SensitiveDrugResponseModel sensitiveDrugResponseModel) {
		List<String> errors = new ArrayList<>();
		if (dssResponse.getErrors() != null && !dssResponse.getErrors().isEmpty())
			errors = dssResponse.getErrors();
		List<String> finalErrors = errors;
		if (dssResponse.getResults() != null) {
			dssResponse.getResults().forEach(result -> {
				List<Error> errorList = new ArrayList<>();
				if (result.getErrors() != null) {
					errorList = result.getErrors();
				}
				List<Error> finalErrorList = errorList;
				if(drugFormularyResponseModelList != null) {
					drugFormularyResponseModelList.stream()
					.filter(model -> model.getDrugCode().equals(result.getNdcDrugCode()))
					.filter(model -> model.getStatusCode().equals(RequestStatusType.REJECTED.value())).findAny()
					.ifPresent(drugFormularyResponseModel -> {
						String e = drugFormularyResponseModel.getStatusDescription();
						finalErrorList.add(new Error(e, drugFormularyResponseModel.getDenialCode()));
						finalErrors.add(e);
					});
				}
				if (null != drugExclusionResponseModel) {
					drugExclusionResponseModel.getDrugList().stream()
							.filter(drugList -> drugList.getDrugCode().equals(result.getNdcDrugCode())
									&& drugList.getStatusCode().equals(RequestStatusType.REJECTED.value()))
							.flatMap(drugList -> drugList.getRejectionsList().stream()).forEach(rejections -> {
								String e = rejections.getStatusDescription();
								finalErrorList.add(new Error(e, rejections.getDenialCode()));
								finalErrors.add(e);
							});
				}
				if (sensitiveDrugResponseModel != null) {
					sensitiveDrugResponseModel.getDrugList().stream().filter(
							sensitiveDrug -> sensitiveDrug.getDrugStatus().equals(RequestStatusType.REJECTED.value()))
							.filter(sensitiveDrug -> sensitiveDrug.getDrugCode().equals(result.getNdcDrugCode()))
							.forEach(sensitiveDrug -> {
								finalErrorList.add(
										new Error(sensitiveDrug.getStatusDescription(), sensitiveDrug.getDenialCode()));
								finalErrors.add(sensitiveDrug.getStatusDescription());
							});
				}
				if (!finalErrorList.isEmpty()) {
					result.setErrors(finalErrorList);
					dssResponse.setErrors(finalErrors);
					result.setStatus(ServiceStatus.REJECTED.name());
				}
			});
		}
	}

	public String setRequestStatus(List<Result> drugValidationResultList) {
		List<String> servicesStatusList = drugValidationResultList.stream().map(Result::getStatus)
				.collect(Collectors.toList());
		if (servicesStatusList.stream().distinct().count() <= 1)
			return servicesStatusList.get(0);
		return RequestStatusType.PARTIAL_APPROVED.value();
	}
}
