package com.waseel.prescription.service.prescriptions;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.cancellation.PrescriptionCancellationResponseModel;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceResponseInfoRepository;
import com.waseel.prescription.service.validation.TechnicalValidationService;

@Service
public class CancellationPrescriptionService {

	private String msgCancelledBefore = "this request is already Cancelled before.";
	private String msgRejectedRequest = "Service code(s) already Rejected.";
	private String msgDispensedRequest = "this request is already Dispensed.";
	private String msgCancelledSuccess = "Transaction cancelled successfully.";
	private String msgInvalid = "Invalid";
	private String msgCancelled = "Cancelled";
	String msgNotAllowCancel = "Not allowed to do Cancellation because ";

	@Autowired
	ServiceResponseInfoRepository serviceResponseInfoRepository;

	@Autowired
	PrescriptionRequestRepository prescriptionRequestRepository;

	@Autowired
	private TechnicalValidationService technicalValidationService;

	public PrescriptionCancellationResponseModel managePrescriptionCancellationRequest(
			String ePrescriptionReferenceNumber, PrescriptionRequest prescriptionRequest,
			ContentCachingRequestWrapper requestWrapper,Boolean requiresOnlyMedicalCheck) throws PrescriptionException {
		PrescriptionCancellationResponseModel responseModel = new PrescriptionCancellationResponseModel();
		responseModel.setePrescriptionReferenceNumber(ePrescriptionReferenceNumber);
		responseModel.setCanCancel(false);
		responseModel.setCanFollowUp(false);
		responseModel.setStatus(msgInvalid);
		prescriptionRequest.setCanFollowUp(false);
		handleCancellationRequest(prescriptionRequest, responseModel, ePrescriptionReferenceNumber, requestWrapper,
				requiresOnlyMedicalCheck);
		responseModel.setHttpStatusCode(HttpStatus.OK.value());
		return responseModel;
	}

	private void handleCancellationRequest(PrescriptionRequest prescriptionRequest,
			PrescriptionCancellationResponseModel responseModel, String ePrescriptionReferenceNumber,
			ContentCachingRequestWrapper requestWrapper, Boolean requiresOnlyMedicalCheck) throws PrescriptionException {
		if (!prescriptionRequest.getCanCancel()) {
			// Failure
			if (prescriptionRequest.isCancelled()) {
				responseModel.setStatusDescription(msgNotAllowCancel + msgCancelledBefore);
			} else if (prescriptionRequest.getStatusCode().equals(RequestStatusType.REJECTED.value())) {
				responseModel.setStatusDescription(msgNotAllowCancel + msgRejectedRequest);
				prescriptionRequest.setCanFollowUp(true);
				responseModel.setCanFollowUp(true);
			} else if (prescriptionRequest.getStatusCode().equals(RequestStatusType.DISPENSED.value())) {
				responseModel.setStatusDescription(msgNotAllowCancel + msgDispensedRequest);
			} else {
				throw new PrescriptionException(
						technicalValidationService.populateInvalidPrescriptionResponse(requestWrapper,
								"Not allowed to do Cancellation with this request.", ePrescriptionReferenceNumber));
			}
		} else {
			// Success
			List<ServiceResponseInfo> serviceResponseInfoList = serviceResponseInfoRepository
					.findByRequestId(prescriptionRequest.getRequestId());
			serviceResponseInfoList.forEach(serviceResponseInfo -> {
				serviceResponseInfo.setStatus(ServiceStatus.REJECTED.name());
				if(Boolean.FALSE.equals(requiresOnlyMedicalCheck)) {
					serviceResponseInfo.setNet(BigDecimal.ZERO);
					serviceResponseInfo.setPatientShare(BigDecimal.ZERO);
				}
			});
			serviceResponseInfoRepository.saveAll(serviceResponseInfoList);
			prescriptionRequest.setCancelled(true);
			prescriptionRequest.setStatusCode(RequestStatusType.CANCELLED.value());
			prescriptionRequest.setStatusDescription(msgCancelledSuccess);
			responseModel.setStatusDescription(msgCancelledSuccess);
			responseModel.setStatus(msgCancelled);
		}
		prescriptionRequest.setCanCancel(false);
		prescriptionRequest.setReceivedDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		if (Boolean.FALSE.equals(requiresOnlyMedicalCheck)) {
			prescriptionRequest.setPayerShare(BigDecimal.ZERO);
			prescriptionRequest.setPatientShare(BigDecimal.ZERO);
		}
		prescriptionRequestRepository.save(prescriptionRequest);
	}
}
