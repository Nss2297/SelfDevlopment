package com.shoppingService.comparableAndComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortMapByValues {
	public static void main(String[] args) {
		Map<Integer, String> map = new HashMap<>();
		map.put(3, "c");
		map.put(1, "a");
		map.put(4, "d");
		map.put(2, "b");
		map.put(5, "e");
		List<Map.Entry<Integer, String>> list = new ArrayList<Map.Entry<Integer, String>>(map.entrySet());
		System.out.println(list);
		Collections.sort(list, Comparator.comparing(data -> data.getValue()));
		System.out.println(list);
	}
}
