package com.shoppingService.Test;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test11 {
	private static final Logger log = LoggerFactory.getLogger(Test11.class);

	public static void main(String args[]) throws ParseException {
//		log.info("{}", Pattern.matches("[~`@]+", "~"));
//		Pattern p = Pattern.compile("[~`!@#$%^&/*()=+{}|_:;',<.>?\\-\\[\\]\\\"\\\\]g");
//		Matcher m = p.matcher("~3");
//		log.info("{}", m.matches());
		 final String regex = "[~`!@#$%^&/*()=+\\{\\}|_:;',<.>?\\-\\[\\]\\\"]";
	        final String string = "1@44@";
	        
	        final Pattern pattern = Pattern.compile(regex);
	        final Matcher matcher = pattern.matcher(string);
	        log.info("{}", matcher.find());
//	        while (matcher.find()) {
//	            System.out.println("Full match: " + matcher.group(0));
//	            
//	            for (int i = 1; i <= matcher.groupCount(); i++) {
//	                System.out.println("Group " + i + ": " + matcher.group(i));
//	            }
//	        }
	}
}
