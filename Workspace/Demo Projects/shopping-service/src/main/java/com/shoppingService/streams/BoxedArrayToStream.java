package com.shoppingService.streams;

import java.util.Arrays;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoxedArrayToStream {

	private static Stream<String> convertArrayIntoSreamMethOne(String[] array) {
		return Arrays.stream(array);
	}

	private static Stream<String> convertArrayIntoSreamByStreamOf(String[] array) {
		return Stream.of(array);
	}

	private static Stream<String> convertArrayIntoStreamByStreamMethod(String[] array) {
		return Arrays.asList(array).stream();
	}

	public static void main(String[] args) {
		String[] array = new String[] { "Geeks", "forGeeks", "A computer Portal" };
		log.info("Array: {}", Arrays.toString(array));
		log.info("Arrays.stream: {}", Arrays.toString(convertArrayIntoSreamMethOne(array).toArray()));
		log.info("Stream.of: {}", Arrays.toString(convertArrayIntoSreamByStreamOf(array).toArray()));
		log.info("Arrays.asList().stream: {}", Arrays.toString(convertArrayIntoStreamByStreamMethod(array).toArray()));
	}
}
