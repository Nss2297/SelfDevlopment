package com.shoppingService.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test60 {
	private static final Logger log = LoggerFactory.getLogger(Test60.class);

	public static void main(String[] args) {
		Test60 test = new Test60();
//		String haystack = "AAAYAAAX", needle = "AAAX";
//		String haystack = "leetcode", needle = "leeto";
//		String haystack = "gadbutsad", needle = "sad";
//		String haystack = "sadbutsad", needle = "sad";
//		String haystack = "mississippi", needle = "issipi";
		String haystack = "mississippi", needle = "issip";
		int index = test.strStr(haystack, needle);
		log.info("{}", index);
	}

	private int strStr(String haystack, String needle) {
		int haystackLength = haystack.length();
		if (haystackLength < 0) {
			return 0;
		}
		int needleLength = needle.length();
		int[] lps = fetchLps(needleLength, needle);
		int index = fetchIndex(haystack, needle, lps, needleLength, haystackLength);
		return index;
	}

	private int[] fetchLps(int needleLength, String needle) {
		int prevLps = 0, a = 1;
		int[] lps = new int[needleLength];
		lps[0] = 0;
		while (a < needleLength) {
			char char1 = needle.charAt(a), char2 = needle.charAt(prevLps);
			if (char1 == char2) {
				lps[a] = prevLps + 1;
				++prevLps;
				++a;
			} else if (0 == prevLps) {
				lps[a] = 0;
				++a;
			} else {
				prevLps = lps[prevLps - 1];
			}
		}
		return lps;
	}

	private int fetchIndex(String haystack, String needle, int[] lps, int needleLength, int haystackLength) {
		int index = -1, a = 0, s = 0;
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
