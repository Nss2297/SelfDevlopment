package com.shoppingService.streams;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SetIntoStream {
	private static Stream<Integer> convertSetIntoStreamByCollectionSet(Set<Integer> set) {
		return set.stream();
	}

	public static void main(String[] args) {
		Set<Integer> set = new HashSet<>(Arrays.asList(2, 4, 6, 8, 10));
		log.info("Set: {}", set);
		log.info("Collection.set(): {}", Arrays.toString(convertSetIntoStreamByCollectionSet(set).toArray()));
	}
}
