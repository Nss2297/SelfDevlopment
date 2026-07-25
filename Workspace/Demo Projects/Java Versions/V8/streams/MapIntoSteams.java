package com.shoppingService.streams;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapIntoSteams {

	private static <Integer, String> Stream<Map.Entry<Integer, String>> convertMapKeyAndValuesIntoStream(Map map) {
		return map.entrySet().stream();
	}

	private static Stream<Map.Entry<Integer, String>> convertMapKeysIntoStream(Map map) {
		return map.keySet().stream();
	}

	private static Stream<String> convertMapValuesIntoValues(Map map) {
		return map.values().stream();
	}

	public static void main(String args[]) {
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "Geeks");
		map.put(2, "forGeeks");
		map.put(3, "A computer Portal");
		log.info("Map: {}", map);
		Stream<Map.Entry<Integer, String>> stream1 = convertMapKeyAndValuesIntoStream(map);
		Stream<Map.Entry<Integer, String>> stream2 = convertMapKeysIntoStream(map);
		Stream<String> stream3 = convertMapValuesIntoValues(map);
		log.info("Stream of Key and values: {}", Arrays.toString(stream1.toArray()));
		log.info("Stream of Keys: {}", Arrays.toString(stream2.toArray()));
		log.info("Stream of Values: {}", Arrays.toString(stream3.toArray()));
	}
}
