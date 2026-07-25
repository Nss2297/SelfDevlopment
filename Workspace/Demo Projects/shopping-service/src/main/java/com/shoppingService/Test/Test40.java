package com.shoppingService.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test40 {
	private static final Logger log = LoggerFactory.getLogger(Test40.class);

	public static void main(String[] args) {
		List<String> categaorizedDispensibleDrugList = new ArrayList<>();
		List<String> drugs = new ArrayList<>();
		drugs.add("1");
		drugs.add("2");
		List<String> genericAndIrreplicableDrugs = new ArrayList<>();
		genericAndIrreplicableDrugs.add("3");
		genericAndIrreplicableDrugs.add("2");
		Optional<Long> drugListIdOpt = null;
		Set<String> drugCodesSet = drugs.stream().collect(Collectors.toSet());
//		log.info("{}", drugCodesSet.size());
		drugCodesSet.remove("1");
//		log.info("{}", drugCodesSet.isEmpty());
//		log.info("{}", org.apache.commons.lang.StringUtils.strip(genericAndIrreplicableDrugs.toString(), "[]"));
//		log.info("{}", org.apache.commons.lang.StringUtils.join(genericAndIrreplicableDrugs, ","));
		log.info("{}", 1L==2L);
	}

}
