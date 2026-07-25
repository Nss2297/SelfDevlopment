package com.waseel.prescription.service.prescriptions;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.prescription.model.enums.CommonWords;
import com.waseel.prescription.model.enums.PbmRequestType;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionDrugList;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionRequestModel;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionResponseModel;
import com.waseel.prescription.model.prescription.PrescriptionRequestModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.persist.mdss.DrugService;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionApprovalAssc;
import com.waseel.prescription.repository.mdss.DrugServiceRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionApprovalAsscRepository;
import com.waseel.prescription.service.clienthandler.PbmPayerApisRestHandler;
import com.waseel.prescription.service.mapper.MapperService;

@Service
public class EPrescriptionApprovalService {

	@Autowired
	private PbmPayerApisRestHandler pbmPayerApisRestHandler;

	@Autowired
	private PrescriptionApprovalAsscRepository prescriptionApprovalAsscRepository;

	@Autowired
	private MapperService mapperService;

	@Autowired
	private DrugServiceRepository drugServiceRepository;

	public EPrescriptionResponseModel checkEPrescriptionApproval(EPrescriptionRequestModel requestModel,
			PbmRequestType requestType) {
		setDrugDescriptionAndScientificNameValues(requestModel.getDrugList());
		EPrescriptionResponseModel responseModel = pbmPayerApisRestHandler
				.sendRequestToGetEPrescriptionApproval(requestModel);
		// Will cover Failed scenario in upcoming story for DB
		if (responseModel != null && !StringUtils.isBlank(responseModel.getApprovalReferenceNumber())) {
			// Success
			PrescriptionApprovalAssc prescriptionApprovalAssc = new PrescriptionApprovalAssc(
					responseModel.getApprovalReferenceNumber(), new Date(),
					requestModel.getePrescriptionReferenceNumber(), requestType.value());
			prescriptionApprovalAsscRepository.save(prescriptionApprovalAssc);
		}
		return responseModel;
	}

	public PrescriptionResponseModel manageEPrescriptionApprovalForNewOrFollowUp(
			PrescriptionRequestModel prescriptionRequestModel, String ePrescriptionReferenceNumber, String requestId,
			PrescriptionResponseModel prescriptionResponseModel, PbmRequestType requestType) {
		if (prescriptionResponseModel.getResults() != null) {
			EPrescriptionRequestModel eprescriptionRequestModel = mapperService
					.createEPrescriptionRequestModelForNewOrFollowup(prescriptionRequestModel, requestType,
							ePrescriptionReferenceNumber, prescriptionResponseModel);
			EPrescriptionResponseModel ePrescriptionResponseModel = checkEPrescriptionApproval(
					eprescriptionRequestModel, requestType);
			if (ePrescriptionResponseModel != null
					&& StringUtils.isBlank(ePrescriptionResponseModel.getApprovalReferenceNumber())) {
				return mapperService.createPrescriptionResponseFromEPrescriptionResponseModel(
						ePrescriptionResponseModel, ePrescriptionReferenceNumber, requestId);
			}
		}
		return prescriptionResponseModel;
	}

	private void setDrugDescriptionAndScientificNameValues(List<EPrescriptionDrugList> drugList) {
		drugList.forEach(drug -> {
			if (!StringUtils.isBlank(drug.getDrugCode())
					&& !drug.getDrugCode().equalsIgnoreCase(CommonWords.UNDEFINED.value())) {
				setDrugDetails(drug, drugServiceRepository.findByScientificCodeOrOtherCodesValue(drug.getDrugCode()));
			} else if (!StringUtils.isBlank(drug.getScientificCode())) {
				setDrugDetails(drug,
						drugServiceRepository.findByScientificCodeOrOtherCodesValue(drug.getScientificCode()));
			}
		});
	}

	private void setDrugDetails(EPrescriptionDrugList drug, Optional<DrugService> drugServiceOpt) {
		drugServiceOpt.ifPresent(drugDetails -> {
			drug.setDrugDescription(drugDetails.getDisplay());
			drug.setScientificName(drugDetails.getIngredients());
		});
	}
}
