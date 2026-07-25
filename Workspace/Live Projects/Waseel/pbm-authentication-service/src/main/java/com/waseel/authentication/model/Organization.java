package com.waseel.authentication.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.SortedSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.waseel.authentication.model.portal.enity.SwitchAccount;

@Entity
@Table(name = "`Organization`")
public class Organization implements java.io.Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5206291993029681336L;

	private BigDecimal organizationId;
	private String organizationName;
	private String separateInvoice;
	private String code;
	private String organizationArabicName;
	private String vATRegistrationNumber;
	private Date createdAt;
	private Date lastUpdatedDate;
	
	@OneToMany(mappedBy = "`OrganizationId`", fetch = FetchType.LAZY)
	private SortedSet<SwitchAccount> switchAccounts;

	public Organization() {
	}

	public Organization(BigDecimal organizationId, String organizationName,
			String separateInvoice, String code, String organizationArabicName,
			String vATRegistrationNumber, Date createdAt, Date lastUpdatedDate) {
		this.organizationId = organizationId;
		this.organizationName = organizationName;
		this.separateInvoice = separateInvoice;
		this.code = code;
		this.organizationArabicName = organizationArabicName;
		this.vATRegistrationNumber = vATRegistrationNumber;
		this.createdAt = createdAt;
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@Id
	@Column(name = "`OrganizationId`", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(BigDecimal organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "`OrganizationName`", nullable = false, length = 100)
	public String getOrganizationName() {
		return this.organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	@Column(name = "`SeparateInvoice`", nullable = false, length = 1)
	public String getSeparateInvoice() {
		return this.separateInvoice;
	}

	public void setSeparateInvoice(String separateInvoice) {
		this.separateInvoice = separateInvoice;
	}

	@Column(name = "`Code`", nullable = false, length = 100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "`OrganizationArabicName`", length = 90)
	public String getOrganizationArabicName() {
		return organizationArabicName;
	}

	public void setOrganizationArabicName(String OrganizationArabicName) {
		this.organizationArabicName = OrganizationArabicName;
	}

	@Column(name = "`VATRegistrationNumber`", length = 50)
	public String getVATRegistrationNumber() {
		return vATRegistrationNumber;
	}

	public void setVATRegistrationNumber(String VATRegistrationNumber) {
		this.vATRegistrationNumber = VATRegistrationNumber;
	}
	
	@Column(name = "`CreatedAt`", length = 7)
	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Column(name = "`LastUpdatedDate`", length = 7)
	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
}
