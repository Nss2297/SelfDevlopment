package com.waseel.prescription.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.waseel.prescription.model.dss.DssResponse;
import com.waseel.prescription.model.dss.Error;
import com.waseel.prescription.model.dss.Result;
import com.waseel.prescription.model.eligibility.EligibilityResponseModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;
import com.waseel.prescription.model.prescription.MedicalValidations;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.model.prescription.ServiceResponse;

@Mapper
public interface MapResponseModel {

	MapResponseModel INSTANCE = Mappers.getMapper(MapResponseModel.class);
	
	PrescriptionResponseModel mapDssResToPrescriptionRes(DssResponse dssResponse);
	
	@Mapping(source = "ndcDrugCode",target = "drugCode")
	@Mapping(source = "dispensedQuantity",target = "quantity")
	@Mapping(source = "amount",target = "requestedAmount")
	ServiceResponse mapDssResultToPrescriptionResult(Result result);
	
	@Mapping(source = "description",target = "rejectionReason")
	@Mapping(source = "code",target = "denialCode")
	MedicalValidations mapDssErrorsToPrescriptionErrors(Error error);
	
	PrescriptionResponseModel mapEligibilityResToPrescriptionRes(EligibilityResponseModel eligibilityResponseModel);
	
	PrescriptionResponseModel mapPolicyResToPrescriptionRes(PolicyResponseModel policyResponseModel);
}
