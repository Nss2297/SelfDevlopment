package com.shoppingService.java8;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Java8Reduce {

	public static void main(String args[]) {
		List<String> words = Arrays.asList("1", "Two", "3", "Four", "5", "Six", "Seven");
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
		
	    Optional<String> logenstString = words.stream().reduce((word1, word2)->word1.length()>=word2.length()?word1:word2);
	    System.out.println(logenstString.get());
	    
	    Optional<String> combinedString = words.stream().reduce((word1, word2)->word1.concat(word2));	
	    System.out.println(combinedString.get());
	    
	    int sum = numbers.stream().reduce(0, (number1, number2)->number1+ number2);
	    System.out.println(sum);
	}
}
