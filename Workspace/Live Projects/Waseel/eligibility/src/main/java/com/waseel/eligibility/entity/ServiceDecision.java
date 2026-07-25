package com.waseel.eligibility.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the SERVICE_DECISION database table.
 * 
 */
@Entity
@Table(name = "SERVICE_DECISION")
@NamedQuery(name = "ServiceDecision.findAll", query = "SELECT s FROM ServiceDecision s")
public class ServiceDecision implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SERVICEDECISIONID", unique = true, nullable = false)
	private Long servicedecisionid;

	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVICEID", nullable = false)
	private WslServiceDetail service;

	private BigDecimal discount;

	private BigDecimal gross;

	private BigDecimal net;

	private BigDecimal netvatamount;

	private BigDecimal netvatrate;

	private BigDecimal patientshare;

	private BigDecimal patientsharevatamount;

	private BigDecimal patientsharevatrate;

	private BigDecimal pricecorrection;

	private BigDecimal rejection;

//	private String servicecode;

	private String unitofdiscount;

	private String unitofgross;

	private String unitofnet;

	private String unitofnetvatamount;

	private String unitofnetvatrate;

	private String unitofpatientshare;

	private String unitofpatientsharevatamount;

	private String unitofpatientsharevatrate;

	private String unitofpricecorrection;

	private String unitofrejection;
	
	private BigDecimal approvedquantity;
	
	private String servicedenialcode;
	
	private String decisioncomment;
	
	private String servicestatuscode;
	
	public String getServicestatuscode() {
		return this.servicestatuscode;
	}

	public void setServicestatuscode(String servicestatuscode) {
		this.servicestatuscode = servicestatuscode;
	}

	public ServiceDecision() {
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getGross() {
		return this.gross;
	}

	public void setGross(BigDecimal gross) {
		this.gross = gross;
	}

	public BigDecimal getNet() {
		return this.net;
	}

	public void setNet(BigDecimal net) {
		this.net = net;
	}

	public BigDecimal getNetvatamount() {
		return this.netvatamount;
	}

	public void setNetvatamount(BigDecimal netvatamount) {
		this.netvatamount = netvatamount;
	}

	public BigDecimal getNetvatrate() {
		return this.netvatrate;
	}

	public void setNetvatrate(BigDecimal netvatrate) {
		this.netvatrate = netvatrate;
	}

	public BigDecimal getPatientshare() {
		return this.patientshare;
	}

	public void setPatientshare(BigDecimal patientshare) {
		this.patientshare = patientshare;
	}

	public BigDecimal getPatientsharevatamount() {
		return this.patientsharevatamount;
	}

	public void setPatientsharevatamount(BigDecimal patientsharevatamount) {
		this.patientsharevatamount = patientsharevatamount;
	}

	public BigDecimal getPatientsharevatrate() {
		return this.patientsharevatrate;
	}

	public void setPatientsharevatrate(BigDecimal patientsharevatrate) {
		this.patientsharevatrate = patientsharevatrate;
	}

	public BigDecimal getPricecorrection() {
		return this.pricecorrection;
	}

	public void setPricecorrection(BigDecimal pricecorrection) {
		this.pricecorrection = pricecorrection;
	}

	public BigDecimal getRejection() {
		return this.rejection;
	}

	public void setRejection(BigDecimal rejection) {
		this.rejection = rejection;
	}

//	public String getServicecode() {
//		return this.servicecode;
//	}
//
//	public void setServicecode(String servicecode) {
//		this.servicecode = servicecode;
//	}

	public String getUnitofdiscount() {
		return this.unitofdiscount;
	}

	public void setUnitofdiscount(String unitofdiscount) {
		this.unitofdiscount = unitofdiscount;
	}

	public String getUnitofgross() {
		return this.unitofgross;
	}

	public void setUnitofgross(String unitofgross) {
		this.unitofgross = unitofgross;
	}

	public String getUnitofnet() {
		return this.unitofnet;
	}

	public void setUnitofnet(String unitofnet) {
		this.unitofnet = unitofnet;
	}

	public String getUnitofnetvatamount() {
		return this.unitofnetvatamount;
	}

	public void setUnitofnetvatamount(String unitofnetvatamount) {
		this.unitofnetvatamount = unitofnetvatamount;
	}

	public String getUnitofnetvatrate() {
		return this.unitofnetvatrate;
	}

	public void setUnitofnetvatrate(String unitofnetvatrate) {
		this.unitofnetvatrate = unitofnetvatrate;
	}

	public String getUnitofpatientshare() {
		return this.unitofpatientshare;
	}

	public void setUnitofpatientshare(String unitofpatientshare) {
		this.unitofpatientshare = unitofpatientshare;
	}

	public String getUnitofpatientsharevatamount() {
		return this.unitofpatientsharevatamount;
	}

	public void setUnitofpatientsharevatamount(String unitofpatientsharevatamount) {
		this.unitofpatientsharevatamount = unitofpatientsharevatamount;
	}

	public String getUnitofpatientsharevatrate() {
		return this.unitofpatientsharevatrate;
	}

	public void setUnitofpatientsharevatrate(String unitofpatientsharevatrate) {
		this.unitofpatientsharevatrate = unitofpatientsharevatrate;
	}

	public String getUnitofpricecorrection() {
		return this.unitofpricecorrection;
	}

	public void setUnitofpricecorrection(String unitofpricecorrection) {
		this.unitofpricecorrection = unitofpricecorrection;
	}

	public String getUnitofrejection() {
		return this.unitofrejection;
	}

	public void setUnitofrejection(String unitofrejection) {
		this.unitofrejection = unitofrejection;
	}

	public Long getServicedecisionid() {
		return servicedecisionid;
	}

	public void setServicedecisionid(Long servicedecisionid) {
		this.servicedecisionid = servicedecisionid;
	}

	public WslServiceDetail getService() {
		return service;
	}

	public void setService(WslServiceDetail service) {
		this.service = service;
	}

	public BigDecimal getApprovedquantity() {
		return approvedquantity;
	}

	public void setApprovedquantity(BigDecimal approvedquantity) {
		this.approvedquantity = approvedquantity;
	}

	public String getServicedenialcode() {
		return servicedenialcode;
	}

	public void setServicedenialcode(String servicedenialcode) {
		this.servicedenialcode = servicedenialcode;
	}

	public String getDecisioncomment() {
		return decisioncomment;
	}

	public void setDecisioncomment(String decisioncomment) {
		this.decisioncomment = decisioncomment;
	}

}