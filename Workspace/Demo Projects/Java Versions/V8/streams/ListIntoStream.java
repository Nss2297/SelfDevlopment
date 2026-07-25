package com.shoppingService.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListIntoStream {
	private static Stream<String> convertListIntoStream(List<String> list) {
		return list.stream();
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<>(Arrays.asList("GeeksForGeeks", "A computer portal", "for Geeks"));
		log.info("List: {}", Arrays.toString(list.toArray()));
		log.info("Stream: {}", Arrays.toString(convertListIntoStream(list).toArray()));
	}
}
