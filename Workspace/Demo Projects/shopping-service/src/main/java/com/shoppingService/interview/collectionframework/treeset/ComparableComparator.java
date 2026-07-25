package com.shoppingService.interview.collectionframework.treeset;

import java.util.Comparator;

public class ComparableComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		EmployeeModel employee1 = (EmployeeModel) o1;
		EmployeeModel employee2 = (EmployeeModel) o2;
		String employee1Name = employee1.getEmployeeName();
		String employee2Name = employee2.getEmployeeName();
		return employee1Name.compareTo(employee2Name);
	}

}
