package com.shoppingService.comparableAndComparator.linkedHashMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SortingByValues {
	public static void main(String[] args) {
		LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
		map.put("Computer", 1);
		map.put("Science", 3);
		map.put("Portal", 2);
		System.out.println("Before sorting:: " + map);
		List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o1.getValue() - o2.getValue();
			}
		});
		map.clear();
		for (Map.Entry<String, Integer> obj : list) {
			map.put(obj.getKey(), obj.getValue());
		}
		System.out.println("After sorting:: " + map);
	}
}
