package com.waseel.prescription.persist.hira;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * DrugListServiceId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class DrugListServiceId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String registrationNo;
	private Long drugListServiceId;

	// Constructors

	/** default constructor */
	public DrugListServiceId() {
	}

	/** full constructor */
	public DrugListServiceId(String registrationNo, Long drugListServiceId) {
		this.registrationNo = registrationNo;
		this.drugListServiceId = drugListServiceId;
	}

	// Property accessors

	@Column(name = "`RegistrationNo`", nullable = false, length = 30)
	public String getRegistrationNo() {
		return this.registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	@Column(name = "`DrugListServiceId`", nullable = false, precision = 0)
	public Long getDrugListServiceId() {
		return this.drugListServiceId;
	}

	public void setDrugListServiceId(Long drugListServiceId) {
		this.drugListServiceId = drugListServiceId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof DrugListServiceId))
			return false;
		DrugListServiceId castOther = (DrugListServiceId) other;

		return ((this.getRegistrationNo() == castOther.getRegistrationNo()) || (this
				.getRegistrationNo() != null
				&& castOther.getRegistrationNo() != null && this
				.getRegistrationNo().equals(castOther.getRegistrationNo())))
				&& ((this.getDrugListServiceId() == castOther
						.getDrugListServiceId()) || (this
						.getDrugListServiceId() != null
						&& castOther.getDrugListServiceId() != null && this
						.getDrugListServiceId().equals(
								castOther.getDrugListServiceId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getRegistrationNo() == null ? 0 : this.getRegistrationNo()
						.hashCode());
		result = 37
				* result
				+ (getDrugListServiceId() == null ? 0 : this
						.getDrugListServiceId().hashCode());
		return result;
	}

}