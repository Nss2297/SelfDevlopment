package com.waseel.prescription.persist.mongodb;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Id;

import com.waseel.prescription.model.inquiry.eprescription.EPrescriptionInquiryRequestModel;
import com.waseel.prescription.model.prescription.PayerMemberPhysicianInfoModel;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.waseel.prescription.model.cancellation.PrescriptionCancellationRequestModel;
import com.waseel.prescription.model.cancellation.PrescriptionCancellationResponseModel;
import com.waseel.prescription.model.dispense.DispenseDrugsRequestModel;
import com.waseel.prescription.model.dispense.PrescriptionDispenseRequestModel;
import com.waseel.prescription.model.dispense.PrescriptionDispenseResponseModel;
import com.waseel.prescription.model.inquiry.InquiryInvalidResponseModel;
import com.waseel.prescription.model.inquiry.detail.PrescriptionDetailInquiryRequestModel;
import com.waseel.prescription.model.inquiry.detail.PrescriptionDetailInquiryResponseModel;
import com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryRequestModel;
import com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryResponseModel;
import com.waseel.prescription.model.modifydecision.ModifyDecisionRequestModel;
import com.waseel.prescription.model.modifydecision.ModifyDecisionResponseModel;
import com.waseel.prescription.model.prescription.PrescriptionRequestModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;

@Document(value = "PrescriptionAuditTrail")
public class PrescriptionAuditTrail {

	@Id
	@Field(name = "DocumentId")
	private String documentId = UUID.randomUUID().toString();

	@Field(name = "RequestId")
	public String requestId;

	@Field(name = "PrescriptionTransactionLogId")
	public Long prescriptionTransactionLogId;

	@Field(name = "DateTime")
	public Date dateTime;

	@Field(name = "PrescriptionRequest")
	public PrescriptionRequestModel prescriptionRequest;

	@Field(name = "PrescriptionResponse")
	public PrescriptionResponseModel prescriptionResponse;

	@Field(name = "PrescriptionCancellationRequest")
	public PrescriptionCancellationRequestModel cancellationRequestModel;

	@Field(name = "PrescriptionCancellationResponse")
	public PrescriptionCancellationResponseModel cancellationResponseModel;

	@Field(name = "RequestType")
	public String requestType;

	@Field(name = "InvalidPrescriptionRequest")
	public Object invalidPrescriptionRequest;

	@Field(name = "PrescriptionDetailInquiryRequest")
	private PrescriptionDetailInquiryRequestModel detailInquiryRequestModel;

	@Field(name = "PrescriptionDetailInquiryResponse")
	private PrescriptionDetailInquiryResponseModel detailInquiryResponseModel;

	@Field(name = "PrescriptionInquiryInvalidResponse")
	private InquiryInvalidResponseModel inquiryInvalidResponseModel;

	@Field(name = "PrescriptionDispenseRequest")
	private PrescriptionDispenseRequestModel dispensedRequestModel;

	@Field(name = "PrescriptionDispenseResponse")
	private PrescriptionDispenseResponseModel dispensedResponseModel;

	@Field(name = "PrescriptionSummaryInquiryRequest")
	private PrescriptionSummaryRequestModel summaryInquiryRequestModel;

	@Field(name = "PrescriptionSummaryInquiryResponse")
	private PrescriptionSummaryResponseModel summaryInquiryResponseModel;

	@Field(name = "PayerMemberPhysicianInfoResponse")
	private PayerMemberPhysicianInfoModel payerMemberPhysicianInfoModel;

	@Field(name = "EPrescriptionInquiryRequestModel")
	private EPrescriptionInquiryRequestModel ePrescriptionInquiryRequestModel;

	@Field(name = "ModifyDecisionRequestModel")
	private ModifyDecisionRequestModel modifyDecisionRequestModel;

	@Field(name = "ModifyDecisionResponseModel")
	private ModifyDecisionResponseModel modifyDecisionResponseModel;
	
	@Field(name = "EPrescriptionReferenceNumber")
	private String ePrescriptionReferenceNumber;
	
	@Field(name = "DispenseDrugsRequestModel")
	private DispenseDrugsRequestModel dispenseDrugsRequestModel;
	
	public DispenseDrugsRequestModel getDispenseDrugsRequestModel() {
		return dispenseDrugsRequestModel;
	}

	public void setDispenseDrugsRequestModel(DispenseDrugsRequestModel dispenseDrugsRequestModel) {
		this.dispenseDrugsRequestModel = dispenseDrugsRequestModel;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public ModifyDecisionRequestModel getModifyDecisionRequestModel() {
		return modifyDecisionRequestModel;
	}

	public void setModifyDecisionRequestModel(ModifyDecisionRequestModel modifyDecisionRequestModel) {
		this.modifyDecisionRequestModel = modifyDecisionRequestModel;
	}

	public ModifyDecisionResponseModel getModifyDecisionResponseModel() {
		return modifyDecisionResponseModel;
	}

	public void setModifyDecisionResponseModel(ModifyDecisionResponseModel modifyDecisionResponseModel) {
		this.modifyDecisionResponseModel = modifyDecisionResponseModel;
	}

	public EPrescriptionInquiryRequestModel getePrescriptionInquiryRequestModel() {
		return ePrescriptionInquiryRequestModel;
	}

	public void setePrescriptionInquiryRequestModel(EPrescriptionInquiryRequestModel ePrescriptionInquiryRequestModel) {
		this.ePrescriptionInquiryRequestModel = ePrescriptionInquiryRequestModel;
	}

	public PayerMemberPhysicianInfoModel getPayerMemberPhysicianInfoModel() {
		return payerMemberPhysicianInfoModel;
	}

	public void setPayerMemberPhysicianInfoModel(PayerMemberPhysicianInfoModel payerMemberPhysicianInfoModel) {
		this.payerMemberPhysicianInfoModel = payerMemberPhysicianInfoModel;
	}

	public PrescriptionSummaryRequestModel getSummaryInquiryRequestModel() {
		return summaryInquiryRequestModel;
	}

	public void setSummaryInquiryRequestModel(PrescriptionSummaryRequestModel summaryInquiryRequestModel) {
		this.summaryInquiryRequestModel = summaryInquiryRequestModel;
	}

	public PrescriptionSummaryResponseModel getSummaryInquiryResponseModel() {
		return summaryInquiryResponseModel;
	}

	public void setSummaryInquiryResponseModel(PrescriptionSummaryResponseModel summaryInquiryResponseModel) {
		this.summaryInquiryResponseModel = summaryInquiryResponseModel;
	}

	public PrescriptionDispenseRequestModel getDispensedRequestModel() {
		return dispensedRequestModel;
	}

	public void setDispensedRequestModel(PrescriptionDispenseRequestModel dispensedRequestModel) {
		this.dispensedRequestModel = dispensedRequestModel;
	}

	public PrescriptionDispenseResponseModel getDispensedResponseModel() {
		return dispensedResponseModel;
	}

	public void setDispensedResponseModel(PrescriptionDispenseResponseModel dispensedResponseModel) {
		this.dispensedResponseModel = dispensedResponseModel;
	}

	public PrescriptionDetailInquiryRequestModel getDetailInquiryRequestModel() {
		return detailInquiryRequestModel;
	}

	public void setDetailInquiryRequestModel(PrescriptionDetailInquiryRequestModel detailInquiryRequestModel) {
		this.detailInquiryRequestModel = detailInquiryRequestModel;
	}

	public PrescriptionDetailInquiryResponseModel getDetailInquiryResponseModel() {
		return detailInquiryResponseModel;
	}

	public void setDetailInquiryResponseModel(PrescriptionDetailInquiryResponseModel detailInquiryResponseModel) {
		this.detailInquiryResponseModel = detailInquiryResponseModel;
	}

	public InquiryInvalidResponseModel getInquiryInvalidResponseModel() {
		return inquiryInvalidResponseModel;
	}

	public void setInquiryInvalidResponseModel(InquiryInvalidResponseModel inquiryInvalidResponseModel) {
		this.inquiryInvalidResponseModel = inquiryInvalidResponseModel;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Long getPrescriptionTransactionLogId() {
		return prescriptionTransactionLogId;
	}

	public void setPrescriptionTransactionLogId(Long prescriptionTransactionLogId) {
		this.prescriptionTransactionLogId = prescriptionTransactionLogId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public PrescriptionRequestModel getPrescriptionRequest() {
		return prescriptionRequest;
	}

	public void setPrescriptionRequest(PrescriptionRequestModel prescriptionRequest) {
		this.prescriptionRequest = prescriptionRequest;
	}

	public PrescriptionResponseModel getPrescriptionResponse() {
		return prescriptionResponse;
	}

	public void setPrescriptionResponse(PrescriptionResponseModel prescriptionResponse) {
		this.prescriptionResponse = prescriptionResponse;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public PrescriptionCancellationRequestModel getCancellationRequestModel() {
		return cancellationRequestModel;
	}

	public void setCancellationRequestModel(PrescriptionCancellationRequestModel cancellationRequestModel) {
		this.cancellationRequestModel = cancellationRequestModel;
	}

	public PrescriptionCancellationResponseModel getCancellationResponseModel() {
		return cancellationResponseModel;
	}

	public void setCancellationResponseModel(PrescriptionCancellationResponseModel cancellationResponseModel) {
		this.cancellationResponseModel = cancellationResponseModel;
	}

	public Object getInvalidPrescriptionRequest() {
		return invalidPrescriptionRequest;
	}

	public void setInvalidPrescriptionRequest(Object invalidPrescriptionRequest) {
		this.invalidPrescriptionRequest = invalidPrescriptionRequest;
	}

}
