package com.waseel.prescription.model.inquiry.summary;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrescriptionSummaryResponseModel {

	private String requestId;

	private String requestStatus;

	private List<PrescriptionSummary> prescriptionSummary;

	public static class PrescriptionSummary {

		@JsonFormat(pattern = "dd-MM-yyyy")
		private Date submissionDate;

		@JsonProperty("ePrescriptionReferenceNumber")
		private String ePrescriptionReferenceNumber;

		private String status;

		private String statusDescription;

		public PrescriptionSummary() {

		}

		public PrescriptionSummary(Date submissionDate, String ePrescriptionReferenceNumber, String status,
				String statusDescription) {
			super();
			this.submissionDate = submissionDate;
			this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
			this.status = status;
			this.statusDescription = statusDescription;
		}

		public Date getSubmissionDate() {
			return submissionDate;
		}

		public void setSubmissionDate(Date submissionDate) {
			this.submissionDate = submissionDate;
		}

		public String getePrescriptionReferenceNumber() {
			return ePrescriptionReferenceNumber;
		}

		public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
			this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getStatusDescription() {
			return statusDescription;
		}

		public void setStatusDescription(String statusDescription) {
			this.statusDescription = statusDescription;
		}
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public List<PrescriptionSummary> getPrescriptionSummary() {
		return prescriptionSummary;
	}

	public void setPrescriptionSummary(List<PrescriptionSummary> prescriptionSummary) {
		this.prescriptionSummary = prescriptionSummary;
	}
}
