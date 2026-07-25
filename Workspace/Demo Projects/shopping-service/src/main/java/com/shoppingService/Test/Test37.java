package com.shoppingService.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test37 {
	private static final Logger log = LoggerFactory.getLogger(Test37.class);

	public static void main(String[] args) {
		List<String> drugExclusionDetails = new ArrayList<>();
		drugExclusionDetails.add("a");
		List<String> drugExclusionDetailsList = new ArrayList<>();
		drugExclusionDetailsList.add("b");
		drugExclusionDetailsList.add("c");
		drugExclusionDetailsList.add("d");
		drugExclusionDetailsList.add("e");
		drugExclusionDetailsList.add("a");
		drugExclusionDetails.add("c");
		drugExclusionDetailsList.stream()
				.filter(drug -> drugExclusionDetails.stream().noneMatch(exclusionDrug -> exclusionDrug.equals(drug)))
				.forEach(drug->{
					drugExclusionDetails.add(drug);
				});
		drugExclusionDetails.stream().forEach(drug -> {
			log.info("{}", drug);
		});
	}

}
