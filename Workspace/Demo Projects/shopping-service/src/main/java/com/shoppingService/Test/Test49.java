package com.shoppingService.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test49 {
	private static final Logger log = LoggerFactory.getLogger(Test49.class);

	public static void main(String[] args) {
		 String a = "as";
		    String as = new String("as");
		    log.info("{}", a.equals(as));
		    log.info("{}", a.hashCode());
		    log.info("{}", as.hashCode());
	}

}
