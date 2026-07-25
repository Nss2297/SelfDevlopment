package com.shoppingService.streams;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DuplicateElements {

	private static Set<Integer> fetchDuplicateElementsByASet(Stream<Integer> stream) {
		Set<Integer> set = new HashSet();
		return stream.filter(num -> !set.add(num)).collect(Collectors.toSet());
	}

	private static Set<Integer> fetchDuplicatesByGroypingBy(Stream<Integer> stream) {
		return stream.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
				.filter(num -> num.getValue() > 1L).map(Map.Entry::getKey).collect(Collectors.toSet());
	}

	private static Set<Integer> fetchDuplicateElementsByFrequency(List<Integer> list) {
		return list.stream().filter(count -> Collections.frequency(list, count) > 1).collect(Collectors.toSet());
	}

	public static void main(String[] args) {
		Stream<Integer> stream1 = Stream.of(1, 2, 3, 41, 2, 5, 1, 3, 6, 45);
		log.info("Simple Set: {}", fetchDuplicateElementsByASet(stream1));
		Stream<Integer> stream2 = Stream.of(1, 2, 3, 41, 2, 5, 1, 3, 6, 45);
		log.info("Collectors.groupingBy(): {}", fetchDuplicatesByGroypingBy(stream2));
		Stream<Integer> stream3 = Stream.of(1, 2, 3, 41, 2, 5, 1, 3, 6, 45);
	}
}
