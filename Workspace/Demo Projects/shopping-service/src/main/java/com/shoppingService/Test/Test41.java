package com.shoppingService.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test41 {
	private static final Logger log = LoggerFactory.getLogger(Test41.class);

	public static void main(String[] args) {
		Optional<List<String>> genericAndIrreplicableDrugs = Optional.empty();
		log.info("{}", genericAndIrreplicableDrugs.isPresent());
	}

}
