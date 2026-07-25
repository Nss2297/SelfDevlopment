package com.shoppingService.LeetCode.Problem_14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//14. Longest Common Prefix
public class TrieSearch {
	private static final Logger log = LoggerFactory.getLogger(TrieSearch.class);

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

	public static void main(String[] args) {
		String[] words = new String[] { "the", "a", "there", "their", "any" };
		TrieSearch test = new TrieSearch();
		test.insert(words);
		boolean isPresent = test.search("the");
//		boolean isPresent = test.search("thon");
//		boolean isPresent = test.search("thor");
//		boolean isPresent = test.search("an");
		log.info("{}", isPresent);
	}

	private void insert(String[] words) {
		Node currentNode = root;
		for (int s = 0; s < words.length; s++) {
			String word = words[s];
			for (int d = 0; d < word.length(); d++) {
				int index = word.charAt(d) - 'a';
				if (null == currentNode.children[index]) {
					currentNode.children[index] = new Node();
				}
				if (d == word.length() - 1) {
					currentNode.eow = true;
				}
				currentNode = currentNode.children[index];
			}
		}
	}

	private boolean search(String key) {
		Node currentNode = root;
		for (int g = 0; g < key.length(); g++) {
			int index = key.charAt(g) - 'a';
			Node node = currentNode.children[index];
			if (null == node) {
				return false;
			}
			if (g == key.length() - 1 && !node.eow) {
				return false;
			}
			currentNode = node;
		}
		return true;
	}
}
