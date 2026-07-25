package com.shoppingService.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SliceAStream {
	private static Stream<Integer> sliceStreamByLimitAndSkip(Stream<Integer> intStream, int startIndex, int endIndex) {
		return intStream.skip(startIndex).limit(endIndex - startIndex + 1);
	}

	private static Stream<Integer> sliceByStreamCollectMethod(Stream<Integer> intStream, int startIndex, int endIndex) {
		return intStream.collect(Collectors.collectingAndThen(Collectors.toList(),
				list -> list.stream().skip(startIndex).limit(endIndex - startIndex + 1)));
	}

	private static Stream<Integer> sliceBySubListMethod(Stream<Integer> intStream, int startIndex, int endIndex) {
		return intStream.collect(Collectors.toList()).subList(startIndex, endIndex + 1).stream();
	}

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		for (int a = 11; a < 21; a++) {
			list.add(a);
		}
		Stream<Integer> intStream1 = list.stream();
		Stream<Integer> slicedStream1 = sliceStreamByLimitAndSkip(intStream1, 4, 8);
		log.info("List: {}", Arrays.toString(list.toArray()));
		log.info("Sliced by limit() and skip(): {}", Arrays.toString(slicedStream1.toArray()));
		Stream<Integer> intStream2 = list.stream();
		Stream<Integer> slicedStream2 = sliceByStreamCollectMethod(intStream2, 4, 8);
		log.info("Sliced by stream.collect(Collectors.collectAndThen(null, null)): {}",
				Arrays.toString(slicedStream2.toArray()));
		Stream<Integer> intStream3 = list.stream();
		Stream<Integer> slicedStream3 = sliceBySubListMethod(intStream3, 4, 8);
		log.info("Sliced by subList(): {}", Arrays.toString(slicedStream3.toArray()));
	}
}
