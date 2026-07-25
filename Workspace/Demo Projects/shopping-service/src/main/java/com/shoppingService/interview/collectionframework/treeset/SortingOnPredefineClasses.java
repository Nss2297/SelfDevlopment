package com.shoppingService.interview.collectionframework.treeset;

import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SortingOnPredefineClasses {
	private static final Logger log = LoggerFactory.getLogger(SortingOnPredefineClasses.class);

	public static void main(String[] args) {
		TreeSet<EmployeeModel> treeSet1 = new TreeSet<>();
		treeSet1.add(new EmployeeModel("Sahil", 105L));
		treeSet1.add(new EmployeeModel("Raj", 101L));
		treeSet1.add(new EmployeeModel("Darpan", 106L));
		treeSet1.add(new EmployeeModel("Shrushti", 103L));
		treeSet1.add(new EmployeeModel("Dev", 102L));
		treeSet1.add(new EmployeeModel("Bhoomi", 104L));
		log.info("Default natural sorting order on predefined comparable classes.");
		log.info("{}", treeSet1);

		log.info("Customize sorting order on predefined comparable classes.");
		TreeSet<EmployeeModel> treeSet2 = new TreeSet<>(new ComparableComparator());
		treeSet2.add(new EmployeeModel("Sahil", 105L));
		treeSet2.add(new EmployeeModel("Raj", 101L));
		treeSet2.add(new EmployeeModel("Darpan", 106L));
		treeSet2.add(new EmployeeModel("Shrushti", 103L));
		treeSet2.add(new EmployeeModel("Dev", 102L));
		treeSet2.add(new EmployeeModel("Bhoomi", 104L));
		log.info("{}", treeSet2);
	}

}
