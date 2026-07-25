package com.waseel.prescription.service.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryResponseModel;
import com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryResponseModel.PrescriptionSummary;
import com.waseel.prescription.persist.prescriptionservice.InvalidPrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;

@Service
public class SummaryInquiryMapperService {

	private static final String NoDataFound = "No data found.";
	private static final String DataFound = "Data fetched successfully.";

	public PrescriptionSummaryResponseModel mapSummryInquiryResponse(List<MemberInfo> memberInfoList,
			List<InvalidPrescriptionRequest> invalidRequestList, String requestId) {
		PrescriptionSummaryResponseModel prescriptionSummaryResponseModel = new PrescriptionSummaryResponseModel();
		List<PrescriptionSummary> prescriptionSummary = new ArrayList<PrescriptionSummary>();
		prescriptionSummaryResponseModel.setRequestId(requestId);
		prescriptionSummaryResponseModel.setRequestStatus(DataFound);
		if (memberInfoList != null && memberInfoList.size() > 0) {
			mapMemberInfoToSummeryInqRes(memberInfoList, prescriptionSummary);
		}
		if (invalidRequestList != null && invalidRequestList.size() > 0) {
			mapInvalidInfoToSummaryInqRes(invalidRequestList, prescriptionSummary);
		}
		prescriptionSummaryResponseModel.setPrescriptionSummary(prescriptionSummary);
		return prescriptionSummaryResponseModel;
	}

	private void mapInvalidInfoToSummaryInqRes(List<InvalidPrescriptionRequest> invalidRequestList,
			List<PrescriptionSummary> prescriptionSummaryList) {
		invalidRequestList.forEach(m -> {
			PrescriptionSummaryResponseModel.PrescriptionSummary prescriptionSummary = new PrescriptionSummaryResponseModel.PrescriptionSummary();
			prescriptionSummary.setSubmissionDate(m.getReceivedDateTime());
			prescriptionSummary.setStatus(m.getStatus());
			prescriptionSummary.setStatusDescription(m.getStatusDescription());
			prescriptionSummary.setePrescriptionReferenceNumber(m.getePrescriptionReferenceNumber());
			prescriptionSummaryList.add(prescriptionSummary);
		});
	}

	private void mapMemberInfoToSummeryInqRes(List<MemberInfo> memberInfoList,
			List<PrescriptionSummary> prescriptionSummaryList) {
		for (MemberInfo memberInfo : memberInfoList) {
			PrescriptionSummaryResponseModel.PrescriptionSummary prescriptionSummary = new PrescriptionSummaryResponseModel.PrescriptionSummary();
			if (memberInfo.getPrescriptionRequest() != null && memberInfo.getPrescriptionRequest().size() > 0) {
				for (PrescriptionRequest prescriptionRequest : memberInfo.getPrescriptionRequest()) {
					prescriptionSummary.setSubmissionDate(prescriptionRequest.getReceivedDateTime());
					prescriptionSummary.setStatus(prescriptionRequest.getStatusCode());
					prescriptionSummary.setStatusDescription(prescriptionRequest.getStatusDescription());
					prescriptionSummary
							.setePrescriptionReferenceNumber(prescriptionRequest.getePrescriptionReferenceNumber());
				}
			}
			prescriptionSummaryList.add(prescriptionSummary);
		}
	}

	public PrescriptionSummaryResponseModel mapNoDatafoundResponse(String requestId) {
		PrescriptionSummaryResponseModel prescriptionSummaryResponseModel = new PrescriptionSummaryResponseModel();
		prescriptionSummaryResponseModel.setRequestId(requestId);
		prescriptionSummaryResponseModel.setRequestStatus(NoDataFound);
		return prescriptionSummaryResponseModel;
	}

}
