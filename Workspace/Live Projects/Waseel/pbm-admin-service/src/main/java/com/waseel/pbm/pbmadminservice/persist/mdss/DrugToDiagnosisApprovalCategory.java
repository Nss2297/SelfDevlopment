package com.waseel.pbm.pbmadminservice.persist.mdss;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "DrugToDiagnosisApprovalCategory", schema = "MDSS")
public class DrugToDiagnosisApprovalCategory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "DrugToDiagnosisApprovalCategorySeq")
	@SequenceGenerator(sequenceName = "DrugToDiagnosisApprovalCategory_SEQ", name = "DrugToDiagnosisApprovalCategorySeq", initialValue = 1, allocationSize = 0)
	@Column(name = "Id")
	@JsonIgnore
	private Long id;

	@Column(name = "Name")
	private String name;

	@JsonIgnore
	@Column(name = "IsEnabled")
	private Character isEnabled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Character getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Character isEnabled) {
		this.isEnabled = isEnabled;
	}

}
