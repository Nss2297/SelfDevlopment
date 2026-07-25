package com.shoppingService.LeetCode.Problem_28;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//28. Find the Index of the First Occurrence in a String
public class LpsKmpBrutForce1 {
	private static final Logger log = LoggerFactory.getLogger(LpsKmpBrutForce1.class);

	public static void main(String[] args) {
		LpsKmpBrutForce1 test = new LpsKmpBrutForce1();
		String haystack = "AAAYAAAYAAAX", needle = "AAAX";
		int index = test.fetchIndex(haystack, needle);
		log.info("Index==> {}", index);
	}

	private int fetchIndex(String haystack, String needle) {
		int index = -1;
		if (StringUtils.isBlank(haystack)) {
			return 0;
		}
		int a = 0, s = 0, needleLength = needle.length(), haystackLength = haystack.length();
		for (int d = 0; d < haystackLength; d++) {
			int endIndex = d + needleLength;
			if (endIndex > haystackLength) {
				return index;
			}
			String str1 = haystack.substring(d, endIndex);
			while (s < needleLength) {
				if (str1.charAt(a) == needle.charAt(s)) {
					++s;
					++a;
				} else {
					a = 0;
					s = 0;
					break;
				}
				if (s == needleLength) {
					index = d;
					return index;
				}
			}
		}
		return index;
	}
}
