package com.waseel.drugexclusionvalidationservice.persist.businessrules;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DEPT_SPEC_PHYSC_ASSC", schema = "PBM_BUSINESS_RULES")
public class DeptSpecPhyscAssc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	public DeptSpecPhyscAssc() {
	}

	public DeptSpecPhyscAssc(Long deptSpecPhyscAsscId, BigDecimal specialityId, Long departmentId, Long physicianInfoId,
			Boolean isEnabled) {
		this.deptSpecPhyscAsscId = deptSpecPhyscAsscId;
		this.specialityId = specialityId;
		this.departmentId = departmentId;
		this.physicianInfoId = physicianInfoId;
		this.isEnabled = isEnabled;
	}
}
