package com.shoppingService.Test;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test59 {
	private static final Logger log = LoggerFactory.getLogger(Test59.class);

	public static void main(String[] args) {
		Test59 test = new Test59();
		String haystack = "AAAYAAAX", needle = "AAAX";
		int index = test.getMatchingIndex(haystack, needle);
		log.info("{}", index);
	}

	private int getMatchingIndex(String haystack, String needle) {
		int index = -1, length = needle.length();
		if (StringUtils.isBlank(haystack)) {
			return 0;
		}
		int a = 0, s = 0;
		for (int d = 0; d < haystack.length(); d++) {
			String str1 = haystack.substring(d, d + length);
			for (int f = 0; f < length; f++) {
				if (str1.charAt(a) == needle.charAt(s)) {
					++s;
					++a;
				} else {
					a = 0;
					s = 0;
					break;
				}
			}
			if (s == length) {
				index = a;
				return index;
			}
		}
		return index;
	}
}
