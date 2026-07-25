package com.shoppingService.interview.collectionframework.treeset;

public class EmployeeModel implements Comparable {
	private String employeeName;
	private Long employeeId;

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public int compareTo(Object o) {
		Long empId1 = this.employeeId;
		EmployeeModel employeeModel = (EmployeeModel) o;
		Long empId2 = employeeModel.getEmployeeId();
		if (empId1 < empId2) {
			return -1;
		} else if (empId1 > empId2) {
			return 1;
		}
		return 0;
	}

	public EmployeeModel(String employeeName, Long employeeId) {
		super();
		this.employeeName = employeeName;
		this.employeeId = employeeId;
	}

	@Override
	public String toString() {
		return employeeName + "--" + employeeId;
	}

}
