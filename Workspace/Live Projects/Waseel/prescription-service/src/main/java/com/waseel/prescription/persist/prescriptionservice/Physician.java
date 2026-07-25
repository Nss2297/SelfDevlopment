package com.waseel.prescription.persist.prescriptionservice;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "Physician", schema = "PRESCRIPTION_SERVICE")
public class Physician implements Serializable {

	private static final long serialVersionUID = -7669695797223039849L;

	@Id
	@GeneratedValue(generator = "PsPhysicianSeq")
	@SequenceGenerator(name = "PsPhysicianSeq", sequenceName = "PS_Physician_Seq", allocationSize = 0, initialValue = 1)
	@Column(name = "ID", nullable = false)
	private long id;

	@Column(name = "PhysicianLicenseNumber", length = 20)
	private String physicianLicenseNumber;

	@Column(name = "RequestID", length = 100, updatable = false)
	private String requestId;

	@Column(name = "PhysicianName", length = 250)
	private String physicianName;

	@Column(name = "PhysicianCategory", length = 100)
	private String physicianCategory;

	@Column(name = "PhysicianSpeciality", length = 100)
	private String physicianSpeciality;

	public String getPhysicianSpeciality() {
		return physicianSpeciality;
	}

	public void setPhysicianSpeciality(String physicianSpeciality) {
		this.physicianSpeciality = physicianSpeciality;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPhysicianName() {
		return physicianName;
	}

	public void setPhysicianName(String physicianName) {
		this.physicianName = physicianName;
	}

	public String getPhysicianCategory() {
		return physicianCategory;
	}

	public void setPhysicianCategory(String physicianCategory) {
		this.physicianCategory = physicianCategory;
	}

	public String getPhysicianLicenseNumber() {
		return physicianLicenseNumber;
	}

	public void setPhysicianLicenseNumber(String physicianLicenseNumber) {
		this.physicianLicenseNumber = physicianLicenseNumber;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Physician() {
		super();
	}

	public Physician(String physicianLicenseNumber, String requestId, String physicianName, String physicianCategory,
			String physicianSpeciality) {
		super();
		this.physicianLicenseNumber = physicianLicenseNumber;
		this.requestId = requestId;
		this.physicianName = physicianName;
		this.physicianCategory = physicianCategory;
		this.physicianSpeciality = physicianSpeciality;
	}

	public Physician(long id, String physicianLicenseNumber, String requestId, String physicianName,
			String physicianCategory, String physicianSpeciality) {
		super();
		this.id = id;
		this.physicianLicenseNumber = physicianLicenseNumber;
		this.requestId = requestId;
		this.physicianName = physicianName;
		this.physicianCategory = physicianCategory;
		this.physicianSpeciality = physicianSpeciality;
	}

}
