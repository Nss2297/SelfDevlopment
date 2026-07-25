package com.waseel.eligibility.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the WSL_CLAIM_INVOICE database table.
 * 
 */
@Entity
@Table(name = "WSL_CLAIM_INVOICE")
@NamedQuery(name = "WslClaimInvoice.findAll", query = "SELECT w FROM WslClaimInvoice w")
public class WslClaimInvoice implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "INVOICEID", unique = true, nullable = false)
	private Long invoiceid;

	private String invoicenumber;

	private String providerid;

	private BigDecimal discount;

	private BigDecimal gross;

	@Temporal(TemporalType.DATE)
	private Date invoicedate;

	private String invoicedepartment;

	private BigDecimal net;

	private BigDecimal netvatamount;

	private BigDecimal netvatrate;

	private BigDecimal patientshare;

	private BigDecimal patientsharevatamount;

	private BigDecimal patientsharevatrate;

	private String unitofdiscount;

	private String unitofgross;

	private String unitofnet;

	private String unitofnetvatamount;

	private String unitofnetvatrate;

	private String unitofpatientshare;

	private String unitofpatientsharevatamount;

	private String unitofpatientsharevatrate;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice", fetch = FetchType.LAZY)
	private List<WslServiceDetail> wslServiceDetails;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMID", nullable = false)
	@JsonIgnore
	private WslGeninfo geninfo;

	public WslClaimInvoice() {
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

	public Date getInvoicedate() {
		return this.invoicedate;
	}

	public void setInvoicedate(Date invoicedate) {
		this.invoicedate = invoicedate;
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

	public List<WslServiceDetail> getWslServiceDetails() {
		return this.wslServiceDetails;
	}

	public void setWslServiceDetails(List<WslServiceDetail> wslServiceDetails) {
		this.wslServiceDetails = wslServiceDetails;
	}

	public Long getInvoiceid() {
		return invoiceid;
	}

	public void setInvoiceid(Long invoiceid) {
		this.invoiceid = invoiceid;
	}

	public String getInvoicenumber() {
		return invoicenumber;
	}

	public void setInvoicenumber(String invoicenumber) {
		this.invoicenumber = invoicenumber;
	}

	public String getProviderid() {
		return providerid;
	}

	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}

	public String getInvoicedepartment() {
		return invoicedepartment;
	}

	public void setInvoicedepartment(String invoicedepartment) {
		this.invoicedepartment = invoicedepartment;
	}

	public WslGeninfo getGeninfo() {
		return geninfo;
	}

	public void setGeninfo(WslGeninfo geninfo) {
		this.geninfo = geninfo;
	}

}