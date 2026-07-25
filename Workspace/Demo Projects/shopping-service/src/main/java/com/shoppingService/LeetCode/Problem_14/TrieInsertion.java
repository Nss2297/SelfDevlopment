package com.shoppingService.LeetCode.Problem_14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//14. Longest Common Prefix
public class TrieInsertion {
	private static final Logger log = LoggerFactory.getLogger(TrieInsertion.class);

	static class Node {
		Node[] children;
		boolean eow;

		public Node() {
			children = new Node[26];// a-z
			for (int i = 0; i < children.length; i++) {
				children[i] = null;
			}
			eow = false;
		}
	}

	static Node root = new Node();

	public static void main(String[] args) {
		TrieInsertion test = new TrieInsertion();
		String[] words = new String[] { "the", "a", "there", "their", "any" };
		test.insertWordsIntoTheTrie(words);
		for (int i = 0; i < root.children.length; i++) {
			log.info("{}", root.children[i]);
		}
	}

	private void insertWordsIntoTheTrie(String[] words) {
		for (int i = 0; i < words.length; i++) {
			for (int s = 0; s < words[i].length(); s++) {
				int index = words[i].charAt(s) - 'a';
				if (null == root.children[index]) {
					root.children[index] = new Node();
				}
				if (i == words.length - 1) {
					root.eow = true;
				}
				root = root.children[index];
			}
		}
	}
}
