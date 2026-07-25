package com.waseel.eligibility.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.SortNatural;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the WSL_GENINFO database table.
 * 
 */
@Entity
@Table(name = "WSL_GENINFO")
@NamedQuery(name = "WslGeninfo.findAll", query = "SELECT w FROM WslGeninfo w")
public class WslGeninfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CLAIMID", unique = true, nullable = false)
	@JsonIgnore
	private Long claimid;

	private String provclaimno;

	private String providerid;

	@Transient
	@JsonIgnore
	private Long oldUploadId;

	private Long uploadid;

	@Temporal(TemporalType.DATE)
	private Date admissiondate;

	private String admissiontype;

	private String age;

	private String approvalnumber;

	private String bednumber;

	private String bloodpressure;

	private String casetype;

	@Lob
	private String chiefcomplaintsymptoms;

	@Temporal(TemporalType.DATE)
	@JsonIgnore
	private Date claimuploadeddate;

	@Lob
	private String commreport;

	private String departmentcode;

	@Temporal(TemporalType.DATE)
	private Date dischargedate;

	private BigDecimal discount;

	private String eligibilitynumber;

	private String estimatedlengthofstay;

	private String firstname;

	private String gender;

	private BigDecimal gross;

	private String height;

	private String idnumber;

	private String illnessduration;

	private String lastname;

	@Temporal(TemporalType.DATE)
	private Date lmp;

	private String memberid;

	private String middlename;

	private String nationality;

	private String acccode;

	private String plantype;

	private String fullname;

	@Temporal(TemporalType.DATE)
	private Date memberdob;

	private BigDecimal net;

	private BigDecimal netvatamount;

	private String otherconditions;

	private String patientfilenumber;

	private BigDecimal patientshare;

	private BigDecimal patientsharevatamount;

	private String payerid;

	private String physiciancategory;

	private String physicianid;

	private String physicianname;

	private String policynumber;

	private String pulse;

	@Lob
	private String radiologyreport;

	private String resprate;

	private String roomnumber;

	@Lob
	private String signicantsigns;

	private String temperature;

	private String unitofdiscount;

	private String unitofgross;

	private String unitofnet;

	private String unitofnetvatamount;

	private String unitofpatientshare;

	private String unitofpatientsharevatamount;

	@Temporal(TemporalType.DATE)
	private Date visitdate;

	private String visittype;

	private String contactnumber;

	private String weight;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "geninfo", fetch = FetchType.LAZY, orphanRemoval = true)
	private Claimprop claimprop;

	@OneToMany(mappedBy = "geninfo", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<WslClaimAttachment> wslClaimAttachments;

	@OrderBy("diagnosisid ASC")
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "geninfo", fetch = FetchType.LAZY, orphanRemoval = true)
	private List<WslClaimDiagnosis> wslClaimDiagnosis;

	@SortNatural
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "geninfo", fetch = FetchType.LAZY, orphanRemoval = true)
	private SortedSet<WslClaimIllness> wslClaimIllnesses;

	@SortNatural
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "geninfo", fetch = FetchType.LAZY, orphanRemoval = true)
	private SortedSet<WslClaimInvestigation> wslClaimInvestigations;

	@SortNatural
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "geninfo", fetch = FetchType.LAZY, orphanRemoval = true)
	private SortedSet<WslClaimInvoice> wslClaimInvoices;

	public WslGeninfo() {
	}

	@Transient
	public Date getLastUpdatedDate() {
		if (claimprop != null)
			return claimprop.getLastupdatedate();
		else
			return null;
	}

	public Date getAdmissiondate() {
		return this.admissiondate;
	}

	public void setAdmissiondate(Date admissiondate) {
		this.admissiondate = admissiondate;
	}

	public String getAdmissiontype() {
		return this.admissiontype;
	}

	public void setAdmissiontype(String admissiontype) {
		this.admissiontype = admissiontype;
	}

	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getApprovalnumber() {
		return this.approvalnumber;
	}

	public void setApprovalnumber(String approvalnumber) {
		this.approvalnumber = approvalnumber;
	}

	public String getBednumber() {
		return this.bednumber;
	}

	public void setBednumber(String bednumber) {
		this.bednumber = bednumber;
	}

	public String getBloodpressure() {
		return this.bloodpressure;
	}

	public void setBloodpressure(String bloodpressure) {
		this.bloodpressure = bloodpressure;
	}

	public String getCasetype() {
		return this.casetype;
	}

	public void setCasetype(String casetype) {
		this.casetype = casetype;
	}

	public String getChiefcomplaintsymptoms() {
		return this.chiefcomplaintsymptoms;
	}

	public void setChiefcomplaintsymptoms(String chiefcomplaintsymptoms) {
		this.chiefcomplaintsymptoms = chiefcomplaintsymptoms;
	}

	public Date getClaimuploadeddate() {
		return this.claimuploadeddate;
	}

	public void setClaimuploadeddate(Date claimuploadeddate) {
		this.claimuploadeddate = claimuploadeddate;
	}

	public String getDepartmentcode() {
		return this.departmentcode;
	}

	public void setDepartmentcode(String departmentcode) {
		this.departmentcode = departmentcode;
	}

	public Date getDischargedate() {
		return this.dischargedate;
	}

	public void setDischargedate(Date dischargedate) {
		this.dischargedate = dischargedate;
	}

	public BigDecimal getDiscount() {
		return this.discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public String getEligibilitynumber() {
		return this.eligibilitynumber;
	}

	public void setEligibilitynumber(String eligibilitynumber) {
		this.eligibilitynumber = eligibilitynumber;
	}

	public String getEstimatedlengthofstay() {
		return this.estimatedlengthofstay;
	}

	public void setEstimatedlengthofstay(String estimatedlengthofstay) {
		this.estimatedlengthofstay = estimatedlengthofstay;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public BigDecimal getGross() {
		return this.gross;
	}

	public void setGross(BigDecimal gross) {
		this.gross = gross;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getIdnumber() {
		return this.idnumber;
	}

	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	public String getIllnessduration() {
		return this.illnessduration;
	}

	public void setIllnessduration(String illnessduration) {
		this.illnessduration = illnessduration;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getLmp() {
		return this.lmp;
	}

	public void setLmp(Date lmp) {
		this.lmp = lmp;
	}

	public String getMemberid() {
		return this.memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	public String getMiddlename() {
		return this.middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
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

	public String getOtherconditions() {
		return this.otherconditions;
	}

	public void setOtherconditions(String otherconditions) {
		this.otherconditions = otherconditions;
	}

	public String getPatientfilenumber() {
		return this.patientfilenumber;
	}

	public void setPatientfilenumber(String patientfilenumber) {
		this.patientfilenumber = patientfilenumber;
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

	public String getPayerid() {
		return this.payerid;
	}

	public void setPayerid(String payerid) {
		this.payerid = payerid;
	}

	public String getPhysiciancategory() {
		return this.physiciancategory;
	}

	public void setPhysiciancategory(String physiciancategory) {
		this.physiciancategory = physiciancategory;
	}

	public String getPhysicianid() {
		return this.physicianid;
	}

	public void setPhysicianid(String physicianid) {
		this.physicianid = physicianid;
	}

	public String getPhysicianname() {
		return this.physicianname;
	}

	public void setPhysicianname(String physicianname) {
		this.physicianname = physicianname;
	}

	public String getPolicynumber() {
		return this.policynumber;
	}

	public void setPolicynumber(String policynumber) {
		this.policynumber = policynumber;
	}

	public String getPulse() {
		return this.pulse;
	}

	public void setPulse(String pulse) {
		this.pulse = pulse;
	}

	public String getRadiologyreport() {
		return this.radiologyreport;
	}

	public void setRadiologyreport(String radiologyreport) {
		this.radiologyreport = radiologyreport;
	}

	public String getResprate() {
		return this.resprate;
	}

	public void setResprate(String resprate) {
		this.resprate = resprate;
	}

	public String getRoomnumber() {
		return this.roomnumber;
	}

	public void setRoomnumber(String roomnumber) {
		this.roomnumber = roomnumber;
	}

	public String getSignicantsigns() {
		return this.signicantsigns;
	}

	public void setSignicantsigns(String signicantsigns) {
		this.signicantsigns = signicantsigns;
	}

	public String getTemperature() {
		return this.temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
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

	public Date getVisitdate() {
		return this.visitdate;
	}

	public void setVisitdate(Date visitdate) {
		this.visitdate = visitdate;
	}

	public String getVisittype() {
		return this.visittype;
	}

	public void setVisittype(String visittype) {
		this.visittype = visittype;
	}

	public String getWeight() {
		return this.weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Claimprop getClaimprop() {
		return this.claimprop;
	}

	public void setClaimprop(Claimprop claimprop) {
		this.claimprop = claimprop;
	}

	public Set<WslClaimAttachment> getWslClaimAttachments() {
		return this.wslClaimAttachments;
	}

	public void setWslClaimAttachments(Set<WslClaimAttachment> wslClaimAttachments) {
		this.wslClaimAttachments = wslClaimAttachments;
	}

	public List<WslClaimDiagnosis> getWslClaimDiagnosis() {
		return this.wslClaimDiagnosis;
	}

	public void setWslClaimDiagnosis(List<WslClaimDiagnosis> wslClaimDiagnosis) {
		this.wslClaimDiagnosis = wslClaimDiagnosis;
	}

	public SortedSet<WslClaimIllness> getWslClaimIllnesses() {
		return this.wslClaimIllnesses;
	}

	public void setWslClaimIllnesses(SortedSet<WslClaimIllness> wslClaimIllnesses) {
		this.wslClaimIllnesses = wslClaimIllnesses;
	}

	public SortedSet<WslClaimInvestigation> getWslClaimInvestigations() {
		return this.wslClaimInvestigations;
	}

	public void setWslClaimInvestigations(SortedSet<WslClaimInvestigation> wslClaimInvestigations) {
		this.wslClaimInvestigations = wslClaimInvestigations;
	}

	public SortedSet<WslClaimInvoice> getWslClaimInvoices() {
		return this.wslClaimInvoices;
	}

	public void setWslClaimInvoices(SortedSet<WslClaimInvoice> wslClaimInvoices) {
		this.wslClaimInvoices = wslClaimInvoices;
	}

	public Long getClaimid() {
		return claimid;
	}

	public void setClaimid(Long claimid) {
		this.claimid = claimid;
	}

	public String getProvclaimno() {
		return provclaimno;
	}

	public void setProvclaimno(String provclaimno) {
		this.provclaimno = provclaimno;
	}

	public String getProviderid() {
		return providerid;
	}

	public void setProviderid(String providerid) {
		this.providerid = providerid;
	}

	public Long getUploadid() {
		return uploadid;
	}

	public void setUploadid(Long uploadid) {
		this.uploadid = uploadid;
	}

	public String getAcccode() {
		return acccode;
	}

	public void setAcccode(String acccode) {
		this.acccode = acccode;
	}

	public String getPlantype() {
		return plantype;
	}

	public void setPlantype(String plantype) {
		this.plantype = plantype;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Date getMemberdob() {
		return memberdob;
	}

	public void setMemberdob(Date memberdob) {
		this.memberdob = memberdob;
	}

	public String getContactnumber() {
		return contactnumber;
	}

	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}

	public String getCommreport() {
		return commreport;
	}

	public void setCommreport(String commreport) {
		this.commreport = commreport;
	}

	public Long getOldUploadId() {
		return oldUploadId;
	}

	public void setOldUploadId(Long oldUploadId) {
		this.oldUploadId = oldUploadId;
	}

	@Override
	public String toString() {
		return "WslGeninfo [claimid=" + claimid + ", provclaimno=" + provclaimno + ", providerid=" + providerid
				+ ", admissiondate=" + admissiondate + ", admissiontype=" + admissiontype + ", age=" + age
				+ ", approvalnumber=" + approvalnumber + ", bednumber=" + bednumber + ", bloodpressure=" + bloodpressure
				+ ", casetype=" + casetype + ", chiefcomplaintsymptoms=" + chiefcomplaintsymptoms
				+ ", claimuploadeddate=" + claimuploadeddate + ", commreport=" + commreport + ", departmentcode="
				+ departmentcode + ", dischargedate=" + dischargedate + ", discount=" + discount
				+ ", eligibilitynumber=" + eligibilitynumber + ", estimatedlengthofstay=" + estimatedlengthofstay
				+ ", firstname=" + firstname + ", gender=" + gender + ", gross=" + gross + ", height=" + height
				+ ", idnumber=" + idnumber + ", illnessduration=" + illnessduration + ", lastname=" + lastname
				+ ", lmp=" + lmp + ", memberid=" + memberid + ", middlename=" + middlename + ", nationality="
				+ nationality + ", net=" + net + ", netvatamount=" + netvatamount + ", otherconditions="
				+ otherconditions + ", patientfilenumber=" + patientfilenumber + ", patientshare=" + patientshare
				+ ", patientsharevatamount=" + patientsharevatamount + ", payerid=" + payerid + ", physiciancategory="
				+ physiciancategory + ", physicianid=" + physicianid + ", physicianname=" + physicianname
				+ ", policynumber=" + policynumber + ", pulse=" + pulse + ", radiologyreport=" + radiologyreport
				+ ", resprate=" + resprate + ", roomnumber=" + roomnumber + ", signicantsigns=" + signicantsigns
				+ ", temperature=" + temperature + ", unitofdiscount=" + unitofdiscount + ", unitofgross=" + unitofgross
				+ ", unitofnet=" + unitofnet + ", unitofnetvatamount=" + unitofnetvatamount + ", unitofpatientshare="
				+ unitofpatientshare + ", unitofpatientsharevatamount=" + unitofpatientsharevatamount + ", visitdate="
				+ visitdate + ", visittype=" + visittype + ", weight=" + weight + ", claimprop=" + claimprop
				+ ", wslClaimAttachments=" + wslClaimAttachments + ", wslClaimDiagnosis=" + wslClaimDiagnosis
				+ ", wslClaimIllnesses=" + wslClaimIllnesses + ", wslClaimInvestigations=" + wslClaimInvestigations
				+ ", wslClaimInvoices=" + wslClaimInvoices + "]";
	}

}