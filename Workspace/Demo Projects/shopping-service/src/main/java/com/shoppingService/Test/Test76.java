package com.shoppingService.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test76 {
	public static void main(String[] args) {
		List<String> list1 = List.of("java", "stream", "map", "filter", "reduce");
		System.out.println("List: " + Arrays.toString(list1.toArray()));
		Map<Integer, List<String>> map = list1.stream().collect(Collectors.groupingBy(element -> element.length()));
		System.out.println("" + map);
	}
}
