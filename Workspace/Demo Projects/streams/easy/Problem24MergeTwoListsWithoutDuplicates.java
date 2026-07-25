package com.shoppingService.streams.easy;

import java.util.List;

public class Problem24MergeTwoListsWithoutDuplicates {
public static void main(String[] args) {
	List<Integer> l1 = List.of(1, 2, 3, 4);
	List<Integer> l2 = List.of(3, 4, 5, 6);
	List<List<Integer>> listOfList=List.of(l1,l2);
//	List<Integer> finalList=listOfList.stream()
//	.map(list->list.stream().distinct())
//	.flatMap(list->list.stream())
//	.toList();
//	System.out.println(finalList);
}
}
