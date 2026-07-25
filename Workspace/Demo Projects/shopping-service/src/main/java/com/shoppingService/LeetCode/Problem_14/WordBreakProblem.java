package com.shoppingService.LeetCode.Problem_14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//14. Longest Common Prefix
public class WordBreakProblem {
	private static final Logger log = LoggerFactory.getLogger(WordBreakProblem.class);

	static class Node {
		Node[] children = new Node[26];

		public Node() {
			for (int i = 0; i < children.length; i++) {
				children[i] = null;
			}
		}

		boolean eow = false;
	}

	private static Node root = new Node();

	public static void main(String args[]) {
		String[] words = new String[] { "i", "like", "sam", "samsung", "mobile", "ice" };
//		String key = "ilikesamsung";
//		String key = "ilikesam";
//		String key = "ilikesamk";
//		String key = "icemobile";
		String key = "icemobileilikesamsung";
//		String key = "i";
//		String key = "like";
//		String key = "sam";
//		String key = "samsung";
		WordBreakProblem test = new WordBreakProblem();
		test.insert(words);
//		boolean isPresent = test.search(key);
		boolean isPresent = test.wordBreak(key);
		log.info("{}---{}", key, isPresent);
	}

	private void insert(String[] words) {
		Node currentNode = root;
		for (int s = 0; s < words.length; s++) {
			String word = words[s];
			for (int d = 0; d < word.length(); d++) {
				char character = word.charAt(d);
				int index = character - 'a';
				if (null == currentNode.children[index]) {
					currentNode.children[index] = new Node();
				}
				if (d == word.length() - 1) {
//					currentNode.eow = true;
					currentNode.children[index].eow = true;
				}
				currentNode = currentNode.children[index];
			}
			currentNode = root;
		}
	}

	private boolean search(String key) {
		Node currentNode = root;
		for (int f = 0; f < key.length(); f++) {
			int index = key.charAt(f) - 'a';
			Node node = currentNode.children[index];
			if (null == node) {
				return false;
			}
			if (f == key.length() - 1 && false == node.eow) {
				return false;
			}
			currentNode = node;
		}
		return true;
	}

	private boolean wordBreak(String key) {
		int keyLength = key.length();
		if (0 == keyLength) {
			return true;
		}
		for (int a = 1; a <= keyLength; a++) {
			String firstPart = key.substring(0, a);
			String secondPart = key.substring(a, keyLength);
			boolean wordPresentInSearch = search(firstPart);
			if (wordPresentInSearch && wordBreak(secondPart)) {
				return true;
			}
		}
		return false;
	}
}
