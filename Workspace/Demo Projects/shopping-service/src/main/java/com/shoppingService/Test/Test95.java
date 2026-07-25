package com.shoppingService.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test95 {

	public static void main(String[] args) {
		List<String> list1 = List.of("eat", "tea", "tan", "ate", "nat", "bat");
		System.out.println("List: " + list1);
		Map<String, List<String>> map = list1.stream().collect(Collectors.groupingBy(word -> {
			char[] arrayOfCharacters = word.toCharArray();
			Arrays.sort(arrayOfCharacters);
			return String.valueOf(arrayOfCharacters);
		}));
		System.out.println(map);
	}
}
