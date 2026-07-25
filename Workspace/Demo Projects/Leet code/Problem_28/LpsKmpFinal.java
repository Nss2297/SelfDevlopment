package com.shoppingService.LeetCode.Problem_28;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//28. Find the Index of the First Occurrence in a String
public class LpsKmpFinal {
	private static final Logger log = LoggerFactory.getLogger(LpsKmpFinal.class);

	public static void main(String[] args) {
		LpsKmpFinal test = new LpsKmpFinal();
//		String haystack = "AAAYAAAX", needle = "AAAX";
//		String haystack = "leetcode", needle = "leeto";
//		String haystack = "sadbutsad", needle = "sad";
//		String haystack = "mississippi", needle = "issipi";
		String haystack = "mississippi", needle = "issip";
		int[] lps = test.fetchLps(needle);
		int index = test.fetchIndex(haystack, needle, lps);
		log.info("LPS==> {}", lps);
		log.info("Index==> {}", index);
	}

	private int[] fetchLps(String needle) {
		int[] lps = new int[needle.length()];
		int prevLps = 0, a = 1;
		while (a < needle.length()) {
			char char1 = needle.charAt(a), char2 = needle.charAt(prevLps);
			if (char1 == char2) {
				lps[a] = prevLps + 1;
				++a;
				++prevLps;
			} else if (0 == prevLps) {
				lps[a] = 0;
				++a;
			} else {
				prevLps = lps[prevLps - 1];
			}
		}
		return lps;
	}

	private int fetchIndex(String haystack, String needle, int[] lps) {
		int index = -1, needleLength = needle.length(), a = 0, s = 0, haystackLength = haystack.length();
		if (StringUtils.isBlank(haystack)) {
			return index;
		}
		while (a < haystackLength) {
			char char1 = haystack.charAt(a), char2 = needle.charAt(s);
			if (char1 == char2) {
				++a;
				++s;
			} else if (0 == s) {
				++a;
			} else {
				s = lps[s - 1];
			}
			if (s == needleLength) {
				index = a - needleLength;
				return index;
			}
		}
		return index;
	}
}
