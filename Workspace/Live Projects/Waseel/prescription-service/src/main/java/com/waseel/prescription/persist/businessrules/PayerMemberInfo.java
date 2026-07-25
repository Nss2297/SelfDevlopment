package com.waseel.prescription.persist.businessrules;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PayerMemberInfo", schema = "PBM_BUSINESS_RULES")
public class PayerMemberInfo implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(generator = "PsPayerMemberInfoSeq")
    @SequenceGenerator(name = "PsPayerMemberInfoSeq", sequenceName = "Ps_PayerMemberInfo_Seq", allocationSize = 0, initialValue = 1)
    @Column(name = "ID", nullable = false)
    private long id;
    @Column(name = "PayerId", length = 20)
    private String payerId;
    @Column(name = "MemberName", length = 200)
    private String memberName;
    @Column(name = "IDNumber")
    private String idNumber;
    @Column(name = "MemberID", length = 50)
    private String memberId;
    @Column(name = "DOB")
    private Date dob;
    @Column(name = "Gender", length = 10)
    private String gender;
    @Column(name = "Nationality", length = 56)
    private String nationality;
    @Column(name = "MobileNumber", length = 15)
    private String mobileNumber;
    @Column(name = "Email", length = 64)
    private String email;
    @Column(name = "Status", length = 10)
    private String status;
    @Column(name = "IssueDate")
    private Date issueDate;
    @Column(name = "IsCancelled", columnDefinition = "CHAR(1) default ('0')")
    private boolean isCancelled = false;
    @Column(name = "CancelledDate")
    private Date cancelledDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public Date getCancelledDate() {
        return cancelledDate;
    }

    public void setCancelledDate(Date cancelledDate) {
        this.cancelledDate = cancelledDate;
    }
}
