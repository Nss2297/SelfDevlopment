package com.waseel.prescription.persist.businessrules;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

@Entity
@Table(name = "DEPT_SPEC_PHYSC_ASSC", schema = "PBM_BUSINESS_RULES")
public class DeptSpecPhyscAssc implements Serializable {

	private static final long serialVersionUID = -2350263838056293298L;

	@Id
	@Column(name = "DEPT_SPEC_PHYSC_ASSC_ID", nullable = false, updatable = false)
	private Long deptSpecPhyscAsscId;

	@Column(name = "SPECIALITY_ID", nullable = false)
	private BigDecimal specialityId;

	@Column(name = "DEPARTMENT_ID", nullable = false)
	private Long departmentId;

	@Column(name = "`PhysicianInfoId`", nullable = false)
	private Long physicianInfoId;

	@Column(name = "IS_ENABLED", nullable = false)
	private Boolean isEnabled = true;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPECIALITY_ID", referencedColumnName = "SPECIALITY_ID", insertable = false, updatable = false)
	private Speciality speciality;

	public Boolean getEnabled() {
		return isEnabled;
	}

	public void setEnabled(Boolean enabled) {
		isEnabled = enabled;
	}

	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	public Long getDeptSpecPhyscAsscId() {
		return deptSpecPhyscAsscId;
	}

	public void setDeptSpecPhyscAsscId(Long deptSpecPhyscAsscId) {
		this.deptSpecPhyscAsscId = deptSpecPhyscAsscId;
	}

	public BigDecimal getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(BigDecimal specialityId) {
		this.specialityId = specialityId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getPhysicianInfoId() {
		return physicianInfoId;
	}

	public void setPhysicianInfoId(Long physicianInfoId) {
		this.physicianInfoId = physicianInfoId;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
