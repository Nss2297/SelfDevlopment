package com.shoppingService.streams;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StreamIntoFlatMap {
	private static Stream<Integer> flattenListByFlatMap(Collection<List<Integer>> collection) {
		return collection.stream().flatMap(list -> list.stream());
	}

	private static Stream<String> flattenTwoArraysOfSameType(String[] array1, String[] array2) {
		return Stream.of(array1, array2).flatMap(array -> Arrays.stream(array));
	}

	public static void main(String[] args) {
		Map<Integer, List<Integer>> map = new HashMap<>();
		map.put(1, List.of(1, 2, 3));
		map.put(2, List.of(4, 5, 6));
		Stream<Integer> stream1 = flattenListByFlatMap(map.values());
		log.info("List: {}", Arrays.toString(map.values().toArray()));
		log.info("Stream by flatMap(): {}", Arrays.toString(stream1.toArray()));
		String[] array1 = new String[] { "A", "B", "C" };
		String[] array2 = new String[] { "i", "J", "K" };
		Stream stream2 = flattenTwoArraysOfSameType(array1, array2);
		log.info("Array1: {}  Array2: {}", Arrays.toString(array1), Arrays.toString(array2));
		log.info("Flattened stream of same type array: {}", Arrays.toString(stream2.toArray()));
	}
}
