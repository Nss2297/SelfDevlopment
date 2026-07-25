package com.shoppingService.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TerminalOperations {
	public static void main(String[] args) {
		List<Integer> list1 = List.of(1, 2, 3, 4, 5, 3, 5, 1);
		log.info("Orignal list: {}", Arrays.toString(list1.toArray()));
		// Example 1: Collecting elements into a List
		List<Integer> list2 = list1.stream().map(num -> num * 10).collect(Collectors.toList());
		log.info("List: {}", Arrays.toString(list2.toArray()));
		// Example 2: Collecting elements into a Set
		Set<Integer> set = list1.stream().map(num -> num * 10).collect(Collectors.toSet());
		log.info("Set: {}", Arrays.toString(set.toArray()));
		// Example 3: Counting elements in the list
		long count1 = list2.stream().count();
		log.info("List count: {}", count1);
		// Example 4: Counting elements in the Set
		long count2 = set.stream().count();
		log.info("Set count: {}", count2);
		// Example 5: Checking if any element is greater than 2 anyMatch
		boolean isGreaterThan2 = list2.stream().anyMatch(num -> num > 2);
		log.info("isGreaterThan2: {}", isGreaterThan2);
		boolean noneMatch = list2.stream().noneMatch(num -> num > 20);
		log.info("noneMatch: {}", noneMatch);
		boolean allMatch = list2.stream().allMatch(num -> num > 0);
		log.info("allMatch: {}", allMatch);
        // Example 5: Reducing elements to find the sum
		int sum=list2.stream().reduce(0, Integer::sum);
		log.info("sum: {}", sum);
	}
}
