package com.waseel.eligibility.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the WSL_SERVICE_DETAILS database table.
 * 
 */
@Entity
@Table(name = "WSL_SERVICE_DETAILS")
@NamedQuery(name = "WslServiceDetail.findAll", query = "SELECT w FROM WslServiceDetail w")
public class WslServiceDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SERVICEID", unique = true, nullable = false)
	private Long serviceid;

	private String providerid;

	private String servicenumber;

	private BigDecimal discount;

	private BigDecimal gross;

	private BigDecimal net;

	private BigDecimal netvatamount;

	private BigDecimal netvatrate;

	private BigDecimal patientshare;

	private BigDecimal patientsharevatamount;

	private BigDecimal patientsharevatrate;

	private BigDecimal requestedquantity;

	private String servicecode;

	private String servicecomment;

	@Temporal(TemporalType.DATE)
	private Date servicedate;

	private String servicedescription;

	private String servicetype;

	private String toothnumber;

	private String unitofdiscount;

	private String unitofgross;

	private String unitofnet;

	private String unitofnetvatamount;

	private String unitofnetvatrate;

	private String unitofpatientshare;

	private String unitofpatientsharevatamount;

	private String unitofpatientsharevatrate;

	private BigDecimal unitprice;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "service")
	private ServiceDecision servicedecision;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INVOICEID", nullable = false)
	@JsonIgnore
	private WslClaimInvoice invoice;

	public WslClaimInvoice getInvoice() {
		return invoice;
	}

	public void setInvoice(WslClaimInvoice invoice) {
		this.invoice = invoice;
	}

	public WslServiceDetail() {
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

	public BigDecimal getRequestedquantity() {
		return this.requestedquantity;
	}

	public void setRequestedquantity(BigDecimal requestedquantity) {
		this.requestedquantity = requestedquantity;
	}

	public String getServicecode() {
		return this.servicecode;
	}

	public void setServicecode(String servicecode) {
		this.servicecode = servicecode;
	}

	public String getServicecomment() {
		return this.servicecomment;
	}

	public void setServicecomment(String servicecomment) {
		this.servicecomment = servicecomment;
	}

	public Date getServicedate() {
		return this.servicedate;
	}

	public void setServicedate(Date servicedate) {
		this.servicedate = servicedate;
	}

	public String getServicedescription() {
		return this.servicedescription;
	}

	public void setServicedescription(String servicedescription) {
		this.servicedescription = servicedescription;
	}

	public String getServicetype() {
		return this.servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public String getToothnumber() {
		return this.toothnumber;
	}

	public void setToothnumber(String toothnumber) {
		this.toothnumber = toothnumber;
	}

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

	public BigDecimal getUnitprice() {
		return this.unitprice;
	}

	public void setUnitprice(BigDecimal unitprice) {
		this.unitprice = unitprice;
	}

	public Long getServiceid() {
		return serviceid;
	}

	public void setServiceid(Long serviceid) {
		this.serviceid = serviceid;
	}

	public String getProviderid() {
		return providerid;
	}

	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}

	public String getServicenumber() {
		return servicenumber;
	}

	public void setServicenumber(String servicenumber) {
		this.servicenumber = servicenumber;
	}

	public ServiceDecision getServicedecision() {
		return servicedecision;
	}

	public void setServicedecision(ServiceDecision servicedecision) {
		this.servicedecision = servicedecision;
	}

}