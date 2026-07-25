package com.shoppingService.LeetCode.Problem_28;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//28. Find the Index of the First Occurrence in a String
public class LpsKmpBrutForce2 {
	private static final Logger log = LoggerFactory.getLogger(LpsKmpBrutForce2.class);

	public static void main(String[] args) {
		LpsKmpBrutForce2 test = new LpsKmpBrutForce2();
		String haystack = "AAAYAAAYAAAY", needle = "AAAX";
		int index = test.fetchIndex(haystack, needle);
		log.info("Index==> {}", index);
	}

	private int fetchIndex(String haystack, String needle) {
		int index = -1, haystackLength = haystack.length();
		if (StringUtils.isBlank(haystack)) {
			return 0;
		}
		int a = 0, s = 0, d = 0, needleLength = needle.length();
		while (d < haystackLength) {
			int endIndex = d + needleLength;
			if (endIndex > haystackLength) {
				return index;
			}
			String substring = haystack.substring(d, endIndex);
			while (s < needleLength) {
				if (substring.charAt(a) == needle.charAt(s)) {
					++s;
					++a;
				} else {
					d = d + needleLength;
					s = 0;
					a = 0;
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
