package com.shoppingService.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test34 {
	private static final Logger log = LoggerFactory.getLogger(Test34.class);

	public static void main(String[] args) {
		List<String> list1 = new ArrayList<>();
//		list1.add("1");
//		list1.add("2");
//		list1.add("3");
//		list1.add("4");
		Optional<List<String>> listOpt = Optional.of(list1);
		log.info("Present: {}<================>Empty List: {}<================>List Size==1: {}", listOpt.isPresent(), listOpt.get().isEmpty(), listOpt.get().size()==1);
	}

}
