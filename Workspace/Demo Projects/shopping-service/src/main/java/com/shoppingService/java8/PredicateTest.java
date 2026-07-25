package com.shoppingService.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class PredicateTest {

	public static void main(String args[]) {
		List<String> list = Arrays.asList("wewer", "Gsdfsdf", "Grtjgpoidpfd");
		Predicate<String> predicate = (s)->s.startsWith("G");
		List<String> gstr =  list.stream().filter(st->predicate.test(st)).collect(Collectors.toList());
//		for (String s : list) {
//			if(predicate.test(s)) {
//				System.out.println(s);
//			}
//		}
		gstr.stream().forEach(ts->
			System.out.println(ts));
	}
}
