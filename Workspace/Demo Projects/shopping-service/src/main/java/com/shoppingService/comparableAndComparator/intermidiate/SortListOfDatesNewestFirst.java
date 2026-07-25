package com.shoppingService.comparableAndComparator.intermidiate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortListOfDatesNewestFirst {
public static void main(String[] args) {
	List<LocalDate> dates = Arrays.asList(
		    LocalDate.of(2023, 5, 20),
		    LocalDate.of(2024, 2, 14),
		    LocalDate.of(2021, 11, 3)
		);
	System.out.println(dates);
	dates.sort(Comparator.reverseOrder());
	System.out.println(dates);
}
}
