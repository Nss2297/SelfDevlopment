package com.shoppingService.streams;

import java.util.Arrays;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrimitiveArraysIntoStream {
	private static IntStream convertPrimitiveArrayBySreamMethod(int[] array) {
		return Arrays.stream(array);
	}

	private static IntStream convertPrimitiveArraysByInstreamOfMethod(int[] array) {
		return IntStream.of(array);
	}

	public static void main(String[] args) {
		int[] array = new int[] { 1, 2, 3, 4, 5 };
		log.info("Array: {}", Arrays.toString(array));
		log.info("Array.stream: {}", Arrays.toString(convertPrimitiveArrayBySreamMethod(array).toArray()));
		log.info("IntStream.of: {}", Arrays.toString(convertPrimitiveArraysByInstreamOfMethod(array).toArray()));
	}
}
