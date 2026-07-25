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
 * The persistent class for the CLAIMPROPS database table.
 * 
 */
@Entity
@Table(name = "CLAIMPROPS")
@NamedQuery(name = "Claimprop.findAll", query = "SELECT c FROM Claimprop c")
public class Claimprop implements Serializable {
	private static final long serialVersionUID = 1L;
	 


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLAIMPROPSID", unique = true, nullable = false)
	private Long claimpropsid;

	private String providerid;

	private String fullhash;

	private String invoiceshash;

	@Temporal(TemporalType.DATE)
	private Date lastsubmissiondate;

	@Temporal(TemporalType.DATE)
	private Date lastupdatedate;

	private String partialhash;

	private String payerbatchrefno;

	private String payerclaimrefno;

	@Temporal(TemporalType.DATE)
	private Date paymentdate;

	private String paymentreference;

	private String portaltransactionid;

	private String statuscode;

	private String statusdetail;

	private Long batchid;
	
	@Column(name="DEC_DISCOUNT")
	private BigDecimal decDiscount;

	@Column(name="DEC_GROSS")
	private BigDecimal decGross;

	@Column(name="DEC_NET")
	private BigDecimal decNet;

	@Column(name="DEC_NETVATAMOUNT")
	private BigDecimal decNetvatamount;

	@Column(name="DEC_NETVATRATE")
	private BigDecimal decNetvatrate;

	@Column(name="DEC_PATSHARE")
	private BigDecimal decPatshare;

	@Column(name="DEC_PATSHAREVATAMOUNT")
	private BigDecimal decPatsharevatamount;

	@Column(name="DEC_PATSHAREVATRATE")
	private BigDecimal decPatsharevatrate;
	
	private String unitofdecdiscount;

	private String unitofdecgross;

	private String unitofdecnet;

	private String unitofdecnetvatamount;

	private String unitofdecnetvatrate;

	private String unitofdecpatshare;

	private String unitofdecpatsharevatamount;

	private String unitofdecpatsharevatrate;

	private String unitofpricecorrection;

	private String unitofrejection;
	
	private BigDecimal rejection;
	
	private BigDecimal pricecorrection;
	
	private String eligibilitycheck;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "CLAIMID", nullable = false)
	@JsonIgnore
	private WslGeninfo geninfo;
	@Column(name="ELIGIBILITYSTATUSDESCRIPTION")
	private String eligibilityStatusDesc;

	
	public Claimprop() {
	}










	public String getFullhash() {
		return this.fullhash;
	}

	public void setFullhash(String fullhash) {
		this.fullhash = fullhash;
	}

	public String getInvoiceshash() {
		return this.invoiceshash;
	}

	public void setInvoiceshash(String invoiceshash) {
		this.invoiceshash = invoiceshash;
	}

	public Date getLastsubmissiondate() {
		return this.lastsubmissiondate;
	}

	public void setLastsubmissiondate(Date lastsubmissiondate) {
		this.lastsubmissiondate = lastsubmissiondate;
	}

	public Date getLastupdatedate() {
		return this.lastupdatedate;
	}

	public void setLastupdatedate(Date lastupdatedate) {
		this.lastupdatedate = lastupdatedate;
	}

	public String getPartialhash() {
		return this.partialhash;
	}

	public void setPartialhash(String partialhash) {
		this.partialhash = partialhash;
	}

	public String getPayerbatchrefno() {
		return this.payerbatchrefno;
	}

	public void setPayerbatchrefno(String payerbatchrefno) {
		this.payerbatchrefno = payerbatchrefno;
	}

	public String getPayerclaimrefno() {
		return this.payerclaimrefno;
	}

	public void setPayerclaimrefno(String payerclaimrefno) {
		this.payerclaimrefno = payerclaimrefno;
	}

	public Date getPaymentdate() {
		return this.paymentdate;
	}

	public void setPaymentdate(Date paymentdate) {
		this.paymentdate = paymentdate;
	}

	public String getPaymentreference() {
		return this.paymentreference;
	}

	public void setPaymentreference(String paymentreference) {
		this.paymentreference = paymentreference;
	}

	public String getPortaltransactionid() {
		return this.portaltransactionid;
	}

	public void setPortaltransactionid(String portaltransactionid) {
		this.portaltransactionid = portaltransactionid;
	}

	public String getStatuscode() {
		return this.statuscode;
	}

	public void setStatuscode(String statuscode) {
		this.statuscode = statuscode;
	}

	public String getStatusdetail() {
		return this.statusdetail;
	}

	public void setStatusdetail(String statusdetail) {
		this.statusdetail = statusdetail;
	}

	public Long getBatchid() {
		return batchid;
	}
	public Long setBatchid(Long batchId) {
		return batchid = batchId;
	}

	public Long getClaimpropsid() {
		return claimpropsid;
	}

	public void setClaimpropsid(Long claimpropsid) {
		this.claimpropsid = claimpropsid;
	}

	public String getProviderid() {
		return providerid;
	}

	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}

	public WslGeninfo getGeninfo() {
		return geninfo;
	}

	public void setGeninfo(WslGeninfo geninfo) {
		this.geninfo = geninfo;
	}

	public BigDecimal getDecDiscount() {
		return decDiscount;
	}

	public void setDecDiscount(BigDecimal decDiscount) {
		this.decDiscount = decDiscount;
	}

	public BigDecimal getDecGross() {
		return decGross;
	}

	public void setDecGross(BigDecimal decGross) {
		this.decGross = decGross;
	}

	public BigDecimal getDecNet() {
		return decNet;
	}

	public void setDecNet(BigDecimal decNet) {
		this.decNet = decNet;
	}

	public BigDecimal getDecNetvatamount() {
		return decNetvatamount;
	}

	public void setDecNetvatamount(BigDecimal decNetvatamount) {
		this.decNetvatamount = decNetvatamount;
	}

	public BigDecimal getDecNetvatrate() {
		return decNetvatrate;
	}

	public void setDecNetvatrate(BigDecimal decNetvatrate) {
		this.decNetvatrate = decNetvatrate;
	}

	public BigDecimal getDecPatshare() {
		return decPatshare;
	}

	public void setDecPatshare(BigDecimal decPatshare) {
		this.decPatshare = decPatshare;
	}

	public BigDecimal getDecPatsharevatamount() {
		return decPatsharevatamount;
	}

	public void setDecPatsharevatamount(BigDecimal decPatsharevatamount) {
		this.decPatsharevatamount = decPatsharevatamount;
	}

	public BigDecimal getDecPatsharevatrate() {
		return decPatsharevatrate;
	}

	public void setDecPatsharevatrate(BigDecimal decPatsharevatrate) {
		this.decPatsharevatrate = decPatsharevatrate;
	}

	public String getUnitofdecdiscount() {
		return unitofdecdiscount;
	}

	public void setUnitofdecdiscount(String unitofdecdiscount) {
		this.unitofdecdiscount = unitofdecdiscount;
	}

	public String getUnitofdecgross() {
		return unitofdecgross;
	}

	public void setUnitofdecgross(String unitofdecgross) {
		this.unitofdecgross = unitofdecgross;
	}

	public String getUnitofdecnet() {
		return unitofdecnet;
	}

	public void setUnitofdecnet(String unitofdecnet) {
		this.unitofdecnet = unitofdecnet;
	}

	public String getUnitofdecnetvatamount() {
		return unitofdecnetvatamount;
	}

	public void setUnitofdecnetvatamount(String unitofdecnetvatamount) {
		this.unitofdecnetvatamount = unitofdecnetvatamount;
	}

	public String getUnitofdecnetvatrate() {
		return unitofdecnetvatrate;
	}

	public void setUnitofdecnetvatrate(String unitofdecnetvatrate) {
		this.unitofdecnetvatrate = unitofdecnetvatrate;
	}

	public String getUnitofdecpatshare() {
		return unitofdecpatshare;
	}

	public void setUnitofdecpatshare(String unitofdecpatshare) {
		this.unitofdecpatshare = unitofdecpatshare;
	}

	public String getUnitofdecpatsharevatamount() {
		return unitofdecpatsharevatamount;
	}

	public void setUnitofdecpatsharevatamount(String unitofdecpatsharevatamount) {
		this.unitofdecpatsharevatamount = unitofdecpatsharevatamount;
	}

	public String getUnitofdecpatsharevatrate() {
		return unitofdecpatsharevatrate;
	}

	public void setUnitofdecpatsharevatrate(String unitofdecpatsharevatrate) {
		this.unitofdecpatsharevatrate = unitofdecpatsharevatrate;
	}

	public String getUnitofpricecorrection() {
		return unitofpricecorrection;
	}

	public void setUnitofpricecorrection(String unitofpricecorrection) {
		this.unitofpricecorrection = unitofpricecorrection;
	}

	public String getUnitofrejection() {
		return unitofrejection;
	}

	public void setUnitofrejection(String unitofrejection) {
		this.unitofrejection = unitofrejection;
	}

	public BigDecimal getRejection() {
		return rejection;
	}

	public void setRejection(BigDecimal rejection) {
		this.rejection = rejection;
	}

	public BigDecimal getPricecorrection() {
		return pricecorrection;
	}

	public void setPricecorrection(BigDecimal pricecorrection) {
		this.pricecorrection = pricecorrection;
	}

	public String getEligibilitycheck() {
		return eligibilitycheck;
	}

	public void setEligibilitycheck(String eligibilitycheck) {
		this.eligibilitycheck = eligibilitycheck;
	}
	public String getEligibilityStatusDesc() {
		return eligibilityStatusDesc;
	}








	public void setEligibilityStatusDesc(String eligibilityStatusDesc) {
		this.eligibilityStatusDesc = eligibilityStatusDesc;
	}
}