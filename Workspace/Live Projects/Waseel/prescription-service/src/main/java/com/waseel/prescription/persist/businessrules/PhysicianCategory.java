package com.waseel.prescription.persist.businessrules;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PhysicianCategory", schema = "PBM_BUSINESS_RULES")
public class PhysicianCategory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PhysicianCategoryName",length = 50,nullable = false)
	private String physicianCategoryName;

	@Column(name = "CategoryDescription",length = 150,nullable = false)
	private String categoryDescription;

	public String getPhysicianCategoryName() {
		return physicianCategoryName;
	}

	public void setPhysicianCategoryName(String physicianCategoryName) {
		this.physicianCategoryName = physicianCategoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public PhysicianCategory() {
	}

	public PhysicianCategory(String physicianCategoryName, String categoryDescription) {
		this.physicianCategoryName = physicianCategoryName;
		this.categoryDescription = categoryDescription;
	}
}
