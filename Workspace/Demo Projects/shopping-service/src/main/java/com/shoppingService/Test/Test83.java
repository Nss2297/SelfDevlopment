package com.shoppingService.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Test83 {
	public static void main(String[] args) {
		List<List<Integer>> list1 = List.of( Arrays.asList(1, 2),
			    Arrays.asList(3, 4),
			    Arrays.asList(5, 6));
		System.out.println("List: " + Arrays.toString(list1.toArray()));
		List<Integer> list2=list1.stream().flatMap(List::stream).collect(Collectors.toList());
		System.out.println("Flatted List: " + list2);
	}
}
