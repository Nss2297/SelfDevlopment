package com.shoppingService.comparableAndComparator.intermidiate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortMapByValueButIfEqualByKey {
	public static void main(String[] args) {
		Map<String, Integer> map = new HashMap<>();
		map.put("b", 15);
		map.put("e", 78);
		map.put("a", 35);
		map.put("c", 90);
		map.put("e", 90);
		map.put("d", 85);
		System.out.println(map);
		List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
		list.stream()
				.sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder())
						.thenComparing(Map.Entry.comparingByValue(Comparator.naturalOrder())))
				.forEach(System.out::println);
	}
}
