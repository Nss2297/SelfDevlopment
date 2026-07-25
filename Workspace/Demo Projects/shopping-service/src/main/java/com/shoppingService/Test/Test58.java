package com.shoppingService.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test58 {
	private static final Logger log = LoggerFactory.getLogger(Test58.class);

	public static void main(String[] args) {
		Test58 test56 = new Test58();
		int index = test56.strStr("leetcode", "leeto");
		log.info("{}", index);
	}

	private int strStr(String haystack, String needle) {
        for (int a = 0; a < haystack.length(); a++) {
            int limit = a + needle.length();
            if (limit < haystack.length()) {
                String str = haystack.substring(a, limit);
                if (str.equals(needle)) {
                    return a;
                }

            }
        }
        return -1;
    }
}
