package com.shoppingService.LeetCode.Problem_28;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//28. Find the Index of the First Occurrence in a String
public class LpsKmp1 {
	private static final Logger log = LoggerFactory.getLogger(LpsKmp1.class);

	public static void main(String[] args) {
		LpsKmp1 test = new LpsKmp1();
		String needle = "AAAYAAAX";
		if (StringUtils.isNotBlank(needle)) {
			log.info("{}", test.lpsIndex(needle));
		}

	}

	private int[] lpsIndex(String needle) {
		if (0 == needle.length()) {
			return new int[0];
		}
		int length = needle.length();
		int[] lps = new int[length];
		lps[0] = 0;
		int prevLps = 0;
		int i = 1;
		while (i < length) {
			if (needle.charAt(i) == needle.charAt(prevLps)) {
				lps[i] = prevLps + 1;
				++i;
				++prevLps;
			} else if (0 == prevLps) {
				lps[i] = 0;
				++i;
			} else {
				prevLps = lps[prevLps - 1];
			}
		}
		return lps;
	}
}
