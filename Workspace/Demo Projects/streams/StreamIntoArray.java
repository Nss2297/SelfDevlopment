package com.shoppingService.streams;

import java.util.Arrays;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StreamIntoArray {
	private static Object[] convertStreamIntoArrayByToArrayMethod(Stream<Integer> stream) {
		return stream.toArray();
	}

	private static Object[] convertStreamIntoArrayByIntFunctionGenerator(Stream<Integer> stream) {
		return stream.toArray(Object[]::new);
	}

	private static int[] convertStreamIntoIntStreamIntoArray(Stream<Integer> stream) {
		return stream.mapToInt(Integer::intValue).toArray();
	}

	public static void main(String[] args) {
		log.info("Stream: {}", Arrays.toString(Stream.of(1, 2, 3, 4, 5).toArray()));
		Stream<Integer> stream1 = Stream.of(1, 2, 3, 4, 5);
		Object[] array1 = convertStreamIntoArrayByToArrayMethod(stream1);
		log.info("Array1: {}", Arrays.toString(array1));
		Stream<Integer> stream2 = Stream.of(1, 2, 3, 4, 5);
		Object[] array2 = convertStreamIntoArrayByIntFunctionGenerator(stream2);
		log.info("Array2: {}", Arrays.toString(array2));
		Stream<Integer> stream3 = Stream.of(1, 2, 3, 4, 5);
		int[] array3 = convertStreamIntoIntStreamIntoArray(stream3);
		log.info("Array3: {}", Arrays.toString(array3));
	}
}
