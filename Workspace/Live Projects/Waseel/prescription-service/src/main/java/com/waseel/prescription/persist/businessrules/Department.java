package com.waseel.prescription.persist.businessrules;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DEPARTMENT", schema = "PBM_BUSINESS_RULES")
public class Department implements Serializable {

	private static final long serialVersionUID = 8191420666289701758L;

	@Id
	@Column(name = "DEPARTMENT_ID", nullable = false, updatable = false)
	private Long departmentId;

	@Column(name = "DEPARTMENT_NAME", nullable = false, unique = true, length = 100)
	private String departmentName;

	@Column(name = "LAST_UPDATE_DATE", nullable = false)
	private Date lastUpdateDate;

	@Column(name = "IS_DELETED", nullable = false, columnDefinition = "CHAR(1) default ('0')")
	private Boolean isDeleted = false;

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Department() {
		super();
	}

	public Department(Long departmentId, String departmentName) {
		super();
		this.departmentId = departmentId;
		this.departmentName = departmentName;
	}

	public Department(Long departmentId, String departmentName, Date lastUpdateDate, Boolean isDeleted) {
		super();
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.lastUpdateDate = lastUpdateDate;
		this.isDeleted = isDeleted;
	}
}
