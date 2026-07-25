package com.waseel.prescription.service.clienthandler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.prescription.clients.BrServiceClient;
import com.waseel.prescription.clients.DrugExclusionServiceClient;
import com.waseel.prescription.clients.DrugFormularyServiceClient;
import com.waseel.prescription.clients.DssServiceClient;
import com.waseel.prescription.model.br.SensitiveDrugRequestModel;
import com.waseel.prescription.model.br.SensitiveDrugResponseModel;
import com.waseel.prescription.model.dss.DssCancellationRequest;
import com.waseel.prescription.model.dss.DssCancellationResponse;
import com.waseel.prescription.model.dss.DssRequest;
import com.waseel.prescription.model.dss.DssResponse;
import com.waseel.prescription.model.enums.ServiceName;
import com.waseel.prescription.model.exclusion.DrugExclusionRequestModel;
import com.waseel.prescription.model.exclusion.DrugExclusionResponseModel;
import com.waseel.prescription.model.formulary.DrugFormularyDetailsModel;
import com.waseel.prescription.model.formulary.DrugFormularyRequestModel;
import com.waseel.prescription.model.formulary.DrugFormularyResponseModel;
import com.waseel.prescription.service.mapper.MapperService;
import com.waseel.prescription.service.validation.TechnicalValidationService;

import feign.FeignException;

@Service
public class RestHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestHandler.class);

	@Autowired
	private DssServiceClient dssServiceClient;

	@Autowired
	private MapperService mapperService;

	@Autowired
	private TechnicalValidationService technicalValidationService;

	@Autowired
	private DrugFormularyServiceClient drugFormularyServiceClient;

	@Autowired
	private DrugExclusionServiceClient drugExclusionServiceClient;
	
	@Autowired
	private BrServiceClient brServiceClient;

	public DssResponse handlePrescriptionRequest(DssRequest dssRequest) {
		return sendPrescriptionRequestToDssService(dssRequest);
	}

	public DssResponse handleFollowupPrescriptionRequest(DssRequest dssRequest) {
		return sendFollowupPrescriptionRequestToDssService(dssRequest);
	}

	public List<DrugFormularyResponseModel> handleDrugFormularyRequest(String payerId, String idNumber,
			List<String> drugCodeList, String requestId) {
		return sendPrescriptionRequestToDrugFormularyService(payerId, idNumber, drugCodeList, requestId);
	}

	public DrugExclusionResponseModel handleDrugExclusion(List<String> drugCodeList, String requestId,
			String licenseNumber, String speciality, String payerId, String providerId) {
		return sendPrescriptionRequestToDrugExclusion(drugCodeList, requestId, licenseNumber, speciality, payerId,
				providerId);
	}

	public DssResponse handleCancelPrescriptionRequest(DssCancellationRequest dssRequest,
			ContentCachingRequestWrapper requestWrapper, String ePrescriptionReferenceNumber) {
		return sendCancelPrescriptionRequestToDssService(dssRequest, requestWrapper, ePrescriptionReferenceNumber);
	}

	public List<DrugFormularyResponseModel> sendPrescriptionRequestToDrugFormularyService(String payerId,
			String idNumber, List<String> drugCodeList, String requestId) {
		try {
			LOGGER.info("Send member IdNumber: {} to DrugFormulary Service", idNumber);
			ResponseEntity<List<DrugFormularyResponseModel>> response = drugFormularyServiceClient
					.sendDrugFormularyRequest(idNumber, payerId,
							new DrugFormularyRequestModel(drugCodeList, requestId));
			return response.getBody();
		} catch (FeignException e) {
			List<DrugFormularyResponseModel> modelList = new ArrayList<>();
			LOGGER.error("FeignException Has Been Thrown While Reading The Response From DrugFormulary "
					+ "service For IdNumber : {}, failed with status [{}]", idNumber, e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				DrugFormularyResponseModel model = mapperService.mapDrugFormularyResponse(e.contentUTF8());
				modelList.add(model);
				return modelList;
			}
			if (e.status() == -1) {
				DrugFormularyResponseModel model = (DrugFormularyResponseModel) technicalValidationService
						.populateInvalidResForConnectionIssue("Not able to call DrugFormulary-Service", idNumber,
								ServiceName.DRUG_FORMULARY_SERVICE.getValue());
				modelList.add(model);
				return modelList;
			}
		} catch (Exception e) {
			LOGGER.error("Exception Has Been Thrown While Reading The Response From DrugFormulary"
					+ " service For IdNumber :{} Error: {}", idNumber, e);
		}
		return null;
	}

	public DrugExclusionResponseModel sendPrescriptionRequestToDrugExclusion(List<String> drugCodeList,
			String requestId, String licenseNumber, String speciality, String payerId, String providerId) {
		try {
			ResponseEntity<DrugExclusionResponseModel> response = drugExclusionServiceClient
					.sendDrugExclusionRequest(new DrugExclusionRequestModel(requestId, licenseNumber, drugCodeList,
							speciality, payerId, providerId));
			return response.getBody();
		} catch (FeignException e) {
			LOGGER.error("FeignException Has Been Thrown While Reading The Response From DrugExclusion "
					+ "service, failed with status [{}]", e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapDrugExclusionResponse(e.contentUTF8());
			}
			if (e.status() == -1) {
				return (DrugExclusionResponseModel) technicalValidationService.populateInvalidResForConnectionIssue(
						"Not able to call DrugExclusion-Service", requestId,
						ServiceName.DRUG_EXCLUSION_SERVICE.getValue());
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Has Been Thrown While Reading The Response From DrugExclusion" + " service Error: {}",
					e);
		}
		return null;
	}

	private DssResponse sendFollowupPrescriptionRequestToDssService(DssRequest dssRequest) {
		try {
			LOGGER.info("Send Followup Request: {} to Dss Service", dssRequest.getRequestId());
			ResponseEntity<DssResponse> response = dssServiceClient
					.sendPrescriptionFollowUpToDssFollowupApi(dssRequest);
			return response.getBody();
		} catch (FeignException e) {
			LOGGER.error(
					"FeignException Has Been Thrown While Reading The Response From Dss service For Followup Request : {}, failed with status [{}]",
					dssRequest.getRequestId(), e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapDssResponse(e.contentUTF8());
			}
			if (e.status() == -1) {
				return (DssResponse) technicalValidationService.populateInvalidResForConnectionIssue(
						"Not able to call DSS-Service", dssRequest.getRequestId(), ServiceName.DSS_SERVICE.getValue());
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Has Been Thrown While Reading The Response From Dss service For Request :{} Error: {}",
					dssRequest.getRequestId(), e);
		}
		return null;
	}

	public DssResponse sendPrescriptionRequestToDssService(DssRequest request) {
		try {
			LOGGER.info("Send Request: {} to Dss Service", request.getRequestId());
			ResponseEntity<DssResponse> response = dssServiceClient.sendNewPrescriptionToDssServiceNewApi(request);
			return response.getBody();
		} catch (FeignException e) {
			LOGGER.error(
					"FeignException Has Been Thrown While Reading The Response From Dss service For Request : {}, failed with status [{}]",
					request.getRequestId(), e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapDssResponse(e.contentUTF8());
			}
			if (e.status() == -1) {
				return (DssResponse) technicalValidationService.populateInvalidResForConnectionIssue(
						"Not able to call DSS-Service", request.getRequestId(), ServiceName.DSS_SERVICE.getValue());
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Has Been Thrown While Reading The Response From Dss service For Request :{} Error: {}",
					request.getRequestId(), e);
		}
		return null;
	}

	public DrugFormularyDetailsModel getDrugFormularyDetails(String payerId, String idNumber) {
		try {
			LOGGER.info("Send member IdNumber: {} to DrugFormulary Service", idNumber);
			ResponseEntity<DrugFormularyDetailsModel> response = drugFormularyServiceClient
					.getDrugFormularyDetails(idNumber, payerId);
			return response.getBody();
		} catch (Exception e) {
			LOGGER.error("Exception Has Been Thrown While Reading The Response From DrugFormulary"
					+ " service For IdNumber :{} Error: {}", idNumber, e);
		}
		return null;
	}

	public DssResponse sendCancelPrescriptionRequestToDssService(DssCancellationRequest dssRequest,
			ContentCachingRequestWrapper requestWrapper, String ePrescriptionReferenceNumber) {
		try {
			LOGGER.info("Send Cancellation Request: {} to Dss Service", dssRequest.getRequestId());
			ResponseEntity<DssCancellationResponse> response = dssServiceClient
					.sendPrescriptionForCancellationToDss(dssRequest);
			DssCancellationResponse overrideResponse = response.getBody();
			return new DssResponse(overrideResponse.getCode(), overrideResponse.getMessage());
		} catch (FeignException e) {
			LOGGER.error(
					"FeignException Has Been Thrown While Reading The Response From Dss service For Followup Request : {}, failed with status [{}]",
					dssRequest.getRequestId(), e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapDssResponse(e.contentUTF8());
			}
			if (e.status() == -1) {
				return (DssResponse) technicalValidationService.populateInvalidResForConnectionIssue(
						"Not able to call DSS-Service", dssRequest.getRequestId(), ServiceName.DSS_SERVICE.getValue());
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Has Been Thrown While Reading The Response From Dss service For Request :{} Error: {}",
					dssRequest.getRequestId(), e);
		}
		return null;
	}

	public SensitiveDrugResponseModel sendPrescriptionToBrServiceForSensitiveDrug(
			SensitiveDrugRequestModel sensitiveDrugRequestModel) {
		try {
			LOGGER.info("Send NEW or FOLLOW-UP Prescription Request: {} to BR Service [SensitiveDrug]",
					sensitiveDrugRequestModel.getRequestId());
			ResponseEntity<SensitiveDrugResponseModel> response = brServiceClient
					.sendPresecriptionToBrService(sensitiveDrugRequestModel);
			return response.getBody();
		} catch (FeignException e) {
			LOGGER.error(
					"FeignException Has Been Thrown While Reading The Response From BR service For "
							+ "NEW or FOLLOW-UP Prescription Request : {}, failed with status [{}]",
					sensitiveDrugRequestModel.getRequestId(), e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapBRResponse(e.contentUTF8());
			}
			if (e.status() == -1) {
				return (SensitiveDrugResponseModel) technicalValidationService.populateInvalidResForConnectionIssue(
						"Not able to call BR Service", sensitiveDrugRequestModel.getRequestId(),
						ServiceName.BR_SERVICE.getValue());
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Has Been Thrown While Reading The Response From BR service For Request :{} Error: {}",
					sensitiveDrugRequestModel.getRequestId(), e);
		}
		return null;
	}
}
