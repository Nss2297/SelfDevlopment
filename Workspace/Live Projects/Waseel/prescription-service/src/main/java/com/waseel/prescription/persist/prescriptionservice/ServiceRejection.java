package com.waseel.prescription.persist.prescriptionservice;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ServiceRejection", schema = "PRESCRIPTION_SERVICE")
public class ServiceRejection implements Serializable {

    private static final long serialVersionUID = -7714563749016927796L;

    @Id
    @GeneratedValue(generator = "PsServiceRejectionSeq")
    @SequenceGenerator(name = "PsServiceRejectionSeq", sequenceName = "PS_ServiceRejection_SEQ", allocationSize = 0, initialValue = 1)
    @Column(name = "ID", unique = true, nullable = false, precision = 0)
    private Long id;

    @Column(name = "DrugCode", length = 50)
    private String drugCode;

    @Column(name = "DenialCode", length = 30)
    private String denialCode;

    @Column(name = "RejectionReason", length = 2500)
    private String rejectionReason;

    @Column(name = "RequestID", length = 100, nullable = false, updatable = false)
    private String requestId;

    @Column(name = "ServiceResponseID", nullable = false, updatable = false)
    private long serviceResponseId;

    @Column(name = "EligibilityReferenceNumber", nullable = true)
    private String eligibilityReferenceNumber;

    @Column(name = "IS_MODIFIED_BY_PAYER", columnDefinition = "CHAR(1) default ('0')")
    private boolean isModifiedByPayer = false;

    @Column(name = "SCIENTIFIC_CODE", length = 64)
    private String scientificCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getDenialCode() {
        return denialCode;
    }

    public void setDenialCode(String denialCode) {
        this.denialCode = denialCode;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public long getServiceResponseId() {
        return serviceResponseId;
    }

    public void setServiceResponseId(long serviceResponseId) {
        this.serviceResponseId = serviceResponseId;
    }

    public String getEligibilityReferenceNumber() {
        return eligibilityReferenceNumber;
    }

    public void setEligibilityReferenceNumber(String eligibilityReferenceNumber) {
        this.eligibilityReferenceNumber = eligibilityReferenceNumber;
    }

    public boolean isModifiedByPayer() {
        return isModifiedByPayer;
    }

    public void setModifiedByPayer(boolean isModifiedByPayer) {
        this.isModifiedByPayer = isModifiedByPayer;
    }

    public ServiceRejection() {
        super();
    }

    public String getScientificCode() {
        return scientificCode;
    }

    public void setScientificCode(String scientificCode) {
        this.scientificCode = scientificCode;
    }

    public ServiceRejection(String drugCode, String denialCode, String rejectionReason, String requestId,
                            String scientificCode, long serviceResponseId) {
        super();
        this.drugCode = drugCode;
        this.denialCode = denialCode;
        this.rejectionReason = rejectionReason;
        this.requestId = requestId;
        this.serviceResponseId = serviceResponseId;
        this.scientificCode = scientificCode;
    }

    public ServiceRejection(Long id, String drugCode, String denialCode, String rejectionReason, String requestId,
                            long serviceResponseId) {
        super();
        this.id = id;
        this.drugCode = drugCode;
        this.denialCode = denialCode;
        this.rejectionReason = rejectionReason;
        this.requestId = requestId;
        this.serviceResponseId = serviceResponseId;
    }

    public ServiceRejection(String drugCode, String denialCode, String rejectionReason, String requestId,
                            long serviceResponseId, String eligibilityReferenceNumber) {
        super();
        this.drugCode = drugCode;
        this.denialCode = denialCode;
        this.rejectionReason = rejectionReason;
        this.requestId = requestId;
        this.serviceResponseId = serviceResponseId;
        this.eligibilityReferenceNumber = eligibilityReferenceNumber;
    }

    public ServiceRejection(String denialCode, String rejectionReason, String eligibilityReferenceNumber) {
        super();
        this.denialCode = denialCode;
        this.rejectionReason = rejectionReason;
        this.eligibilityReferenceNumber = eligibilityReferenceNumber;
    }

	public ServiceRejection(String denialCode, String rejectionReason) {
		this.denialCode = denialCode;
		this.rejectionReason = rejectionReason;
	}

	public ServiceRejection(String denialCode, String rejectionReason, String requestId, long serviceResponseId) {
		this.denialCode = denialCode;
		this.rejectionReason = rejectionReason;
		this.requestId = requestId;
		this.serviceResponseId = serviceResponseId;
	}
	
	public ServiceRejection(String denialCode, String rejectionReason, String requestId, long serviceResponseId, Long id) {
		this.denialCode = denialCode;
		this.rejectionReason = rejectionReason;
		this.requestId = requestId;
		this.serviceResponseId = serviceResponseId;
		this.id = id;
	}

	public ServiceRejection(Long id, String drugCode, String denialCode, String rejectionReason, String requestId,
			long serviceResponseId, String eligibilityReferenceNumber, boolean isModifiedByPayer,
			String scientificCode) {
		super();
		this.id = id;
		this.drugCode = drugCode;
		this.denialCode = denialCode;
		this.rejectionReason = rejectionReason;
		this.requestId = requestId;
		this.serviceResponseId = serviceResponseId;
		this.eligibilityReferenceNumber = eligibilityReferenceNumber;
		this.isModifiedByPayer = isModifiedByPayer;
		this.scientificCode = scientificCode;
	}
}
