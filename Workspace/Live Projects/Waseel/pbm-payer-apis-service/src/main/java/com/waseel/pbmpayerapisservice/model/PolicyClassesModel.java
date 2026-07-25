package com.waseel.pbmpayerapisservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonInclude(Include.NON_NULL)
@JsonTypeName("policyClasses")
public class PolicyClassesModel {

	private String classCode;
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
