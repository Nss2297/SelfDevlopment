package com.waseel.prescription.persist.businessrules;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PhysicianInfo", schema = "PBM_BUSINESS_RULES")
public class PhysicianInfo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PhysicianInfoId", nullable = false, updatable = false)
	private Long physicianInfoId;

	@Column(name = "ProviderId")
	private Long providerId;

	@Column(name = "RegistrationNumber", length = 20)
	private String registrationNumber;

	@Column(name = "Name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "Category")
	private PhysicianCategory category;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "PhysicianInfoId", referencedColumnName = "PhysicianInfoId", insertable = false, updatable = false)
	private List<DeptSpecPhyscAssc> deptSpecPhyscAssc;

	public List<DeptSpecPhyscAssc> getDeptSpecPhyscAssc() {
		return deptSpecPhyscAssc;
	}

	public void setDeptSpecPhyscAssc(List<DeptSpecPhyscAssc> deptSpecPhyscAssc) {
		this.deptSpecPhyscAssc = deptSpecPhyscAssc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PhysicianCategory getCategory() {
		return category;
	}

	public void setCategory(PhysicianCategory category) {
		this.category = category;
	}

	public Long getPhysicianInfoId() {
		return physicianInfoId;
	}

	public void setPhysicianInfoId(Long physicianInfoId) {
		this.physicianInfoId = physicianInfoId;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public PhysicianInfo() {
		super();
	}

	public PhysicianInfo(Long physicianInfoId, Long providerId, String registrationNumber, String name,
			PhysicianCategory category) {
		super();
		this.physicianInfoId = physicianInfoId;
		this.providerId = providerId;
		this.registrationNumber = registrationNumber;
		this.name = name;
		this.category = category;
	}

	public PhysicianInfo(Long physicianInfoId, Long providerId, String registrationNumber, String name) {
		super();
		this.physicianInfoId = physicianInfoId;
		this.providerId = providerId;
		this.registrationNumber = registrationNumber;
		this.name = name;
	}

}
