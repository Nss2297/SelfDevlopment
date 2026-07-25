package com.waseel.prescription.persist.prescriptionservice;

import com.waseel.prescription.persist.hira.SwitchAccount;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "DispensedPrescription", schema = "PRESCRIPTION_SERVICE")
public class DispensedPrescription implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "PsDispensedPrescriptionSeq")
    @SequenceGenerator(name = "PsDispensedPrescriptionSeq", sequenceName = "PS_DispensedPrescription_SEQ", allocationSize = 0, initialValue = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "RequestID", length = 100)
    private String requestId;

    @Column(name = "EPrescriptionReferenceNumber", length = 100)
    private String ePrescriptionReferenceNumber;

    @Column(name = "ProviderID", length = 20)
    private String providerId;

    @Column(name = "PayerID", length = 20)
    private String payerId;

    @Column(name = "StatusCode")
    private String statusCode;

    @Column(name = "DispenseDate")
    private Timestamp dispenseDate = Timestamp.from(Instant.now());

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ProviderID", referencedColumnName = "SwitchAccountId", insertable = false, updatable = false)
    private SwitchAccount switchAccount;

    public SwitchAccount getSwitchAccount() {
        return switchAccount;
    }

    public void setSwitchAccount(SwitchAccount switchAccount) {
        this.switchAccount = switchAccount;
    }

    public DispensedPrescription() {
    }

    public DispensedPrescription(String requestId, String ePrescriptionReferenceNumber, String providerId,
                                 String payerId, String statusCode, Timestamp dispenseDate) {
        super();
        this.requestId = requestId;
        this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
        this.providerId = providerId;
        this.payerId = payerId;
        this.statusCode = statusCode;
        this.dispenseDate = dispenseDate;
    }

    public Timestamp getDispenseDate() {
        return dispenseDate;
    }

    public void setDispenseDate(Timestamp dispenseDate) {
        this.dispenseDate = dispenseDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getePrescriptionReferenceNumber() {
        return ePrescriptionReferenceNumber;
    }

    public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
        this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

}
