package com.waseel.drugexclusionvalidationservice.persist.businessrules;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "`PhysicianInfo`", schema = "PBM_BUSINESS_RULES")
public class PhysicianInfo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`PhysicianInfoId`", nullable = false, updatable = false)
	private Long physicianInfoId;

	@Column(name = "`ProviderId`")
	private Long providerId;

	@Column(name = "`RegistrationNumber`", length = 20)
	private String registrationNumber;

	@Column(name = "`Name`")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "`Category`")
	private PhysicianCategory category;

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
	}

	public PhysicianInfo(Long physicianInfoId, Long providerId, String registrationNumber, String name,
			PhysicianCategory category) {
		this.physicianInfoId = physicianInfoId;
		this.providerId = providerId;
		this.registrationNumber = registrationNumber;
		this.name = name;
		this.category = category;
	}
}
