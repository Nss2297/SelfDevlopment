package com.shoppingService.LeetCode.Problem_28;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//28. Find the Index of the First Occurrence in a String
public class LpsKmp2 {
	private static final Logger log = LoggerFactory.getLogger(LpsKmp2.class);

	public static void main(String[] args) {
		LpsKmp2 test = new LpsKmp2();
		String haystack = "AAAYAAAX";
		String needle = "AAAX";
		if (StringUtils.isNotBlank(needle)) {
			log.info("{}", test.lpsIndex(needle, haystack));
		}

	}

	private int lpsIndex(String needle, String haystack) {
		int index = -1, i = 0, j = 0, hastackLength = haystack.length(), needleLength = needle.length();
		while (i < hastackLength) {
			if (needle.charAt(j) == (haystack.charAt(i))) {
				++i;
				++j;
			} else if (j == 0) {
				++i;
			} else {
				--j;
			}
			if (j == needleLength) {
				index = i - needleLength;
			}
		}
		return index;
	}
}
