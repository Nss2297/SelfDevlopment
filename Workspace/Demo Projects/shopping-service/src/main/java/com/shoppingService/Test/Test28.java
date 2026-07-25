package com.shoppingService.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class Test28 {
	private static final Logger log = LoggerFactory.getLogger(Test28.class);

	public static void main(String[] args) throws UnsupportedEncodingException {
		String value = "Dallah Hospital";
		String encode = URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		log.info("encode:- {}", encode);
		log.info("decode:- {}", URLDecoder.decode(encode, StandardCharsets.UTF_8.toString()));	
	}
}
