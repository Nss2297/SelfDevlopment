package com.shoppingService.LeetCode.Problem_14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//14. Longest Common Prefix
public class StartsWithProblem {
	private static final Logger log = LoggerFactory.getLogger(StartsWithProblem.class);

	static class Node {
		Node[] children = new Node[26];
		boolean eow = false;

		public Node() {
			for (int a = 0; a < children.length; a++) {
				children[a] = null;
			}
		}
	}

	private static Node root = new Node();

	public static void main(String[] args) {
		StartsWithProblem test = new StartsWithProblem();
		String[] words = new String[] { "flower", "flow", "flight" };
		test.insert(words);
		String lcp=fetchLongestCommonPrefix(words);
	}

	private void insert(String[] words) {
		Node currentNode = root;
		for (int s = 0; s < words.length; s++) {
			String word = words[s];
			for (int d = 0; d < word.length(); d++) {
				char ch = word.charAt(d);
				int index = ch - 'a';
				if (null == currentNode.children[index]) {
					currentNode.children[index] = new Node();
				}
				if (d == word.length() - 1) {
					currentNode.children[index].eow = true;
				}
				currentNode = currentNode.children[index];
			}
		}
	}
	
	private static String fetchLongestCommonPrefix(String[] words) {
		Node currentNode=root;
		String lcp="";
		int length=fetchLengthOfTheShortestWord(words);
		for(int d=0;d<length;d++) {
			int arrayLength=words.length;
			while(arrayLength>0) {
				char ch1=words[arrayLength].charAt(d);
				char ch2=words[arrayLength].charAt(d);
				char ch3=words[arrayLength].charAt(d);
				--arrayLength;
			}
			
		}
		return lcp;
	}
	
	private static int fetchLengthOfTheShortestWord(String[] words) {
		int length=words[0].length();
		for(int a=0;a<words.length;a++) {
			int wordsLength=words[a].length();
			if(wordsLength<length) {
				length=wordsLength;
			}
		}
		return length;
	}
}
