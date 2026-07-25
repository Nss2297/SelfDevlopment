package com.waseel.pbm.pbmadminservice.model.drugformulary;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan50Length;

@JsonInclude(Include.NON_NULL)
@JsonTypeName("policyClasses")
public class PolicyClassesModel {

	@NotEmpty(message = "classCode {notNullOrEmpty}")
	@NoMoreThan50Length(message = "classCode {noMoreThan50LengthValidation}")
	private String classCode;

	@NotEmpty(message = "className {notNullOrEmpty}")
	@NoMoreThan50Length(message = "className {noMoreThan50LengthValidation}")
	private String className;

	public String getClassCode() {
		return classCode;
	}

	public String getClassName() {
		return className;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
