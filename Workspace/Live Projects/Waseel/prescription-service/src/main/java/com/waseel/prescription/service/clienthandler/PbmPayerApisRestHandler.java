package com.waseel.prescription.service.clienthandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.waseel.prescription.clients.PBMPayerApisServiceClient;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionRequestModel;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionResponseModel;
import com.waseel.prescription.model.pbmpayerapis.MemberDemographicDataResponseModel;
import com.waseel.prescription.service.management.ConnectionFailedService;
import com.waseel.prescription.service.management.DataPopulationService;
import com.waseel.prescription.service.mapper.MapperService;

import feign.FeignException;

@Service
public class PbmPayerApisRestHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(PbmPayerApisRestHandler.class);

	@Autowired
	private PBMPayerApisServiceClient pbmPayerApisServiceClient;

	@Autowired
	private DataPopulationService dataPopulationService;

	@Autowired
	private MapperService mapperService;

	@Autowired
	private ConnectionFailedService connectionFailedService;

	public ResponseEntity<MemberDemographicDataResponseModel> sendRequestToGetMemberDemographicData(Long idNumber) {
		try {
			LOGGER.info("Send IdNumber: {} to PbmPayerApis Service", idNumber);
			return pbmPayerApisServiceClient.getMemberDemographicData(idNumber);
		} catch (FeignException e) {
			LOGGER.error("FeignException Has Been Thrown While Reading The Response From PbmPayerApisService "
					+ "service For IdNumber : {}, failed with status [{}]", idNumber, e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value() || e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()
					|| e.status() == HttpStatus.SERVICE_UNAVAILABLE.value() || e.status() == -1) {
				int status = e.status() == -1 ? HttpStatus.SERVICE_UNAVAILABLE.value() : e.status();
				return ResponseEntity.status(status).body(dataPopulationService.createInvalidResponse(e));
			}
		} catch (Exception e) {
			LOGGER.error("Exception Has Been Thrown While Reading The Response From PbmPayerApisService"
					+ " service For IdNumber :{} Error: {}", idNumber, e);
		}
		return null;
	}

	public EPrescriptionResponseModel sendRequestToGetEPrescriptionApproval(
			EPrescriptionRequestModel eprescriptionRequestModel) {
		String ePrescriptionReferenceNumber = eprescriptionRequestModel.getePrescriptionReferenceNumber();
		try {
			LOGGER.info("Send ePrescriptionReferenceNumber: {} to PbmPayerApis Service", ePrescriptionReferenceNumber);
			return pbmPayerApisServiceClient.getEPrescriptionApproval(eprescriptionRequestModel).getBody();
		} catch (FeignException e) {
			LOGGER.error(
					"FeignException Has Been Thrown While Reading The Response From PbmPayerApisService "
							+ "service For ePrescriptionReferenceNumber : {}, failed with status [{}]",
					ePrescriptionReferenceNumber, e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value() || e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()
					|| e.status() == HttpStatus.SERVICE_UNAVAILABLE.value()) {
				EPrescriptionResponseModel ePrescriptionResponseModel = mapperService
						.mapEPrescriptionResponseModel(e.contentUTF8());
				LOGGER.error("Status Description:- {}", ePrescriptionResponseModel.getStatusDescription());
				ePrescriptionResponseModel.setStatusDescription("Please contact customer care.");
				return ePrescriptionResponseModel;
			}
			if (e.status() == -1) {
				return connectionFailedService.ePrescriptionResponseModelForConnectionFailure();
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Has Been Thrown While Reading The Response From PbmPayerApisService"
							+ " service For ePrescriptionReferenceNumber :{} Error: {}",
					ePrescriptionReferenceNumber, e);
		}
		return null;
	}
}
