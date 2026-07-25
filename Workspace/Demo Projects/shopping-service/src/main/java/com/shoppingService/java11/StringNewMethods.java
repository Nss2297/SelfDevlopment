package com.shoppingService.java11;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StringNewMethods {
public static void main(String[] args) {
	String a = " ";
	log.info("{}", a.isBlank());
	log.info("{}", a.isEmpty());
	String s = '\u2001' +" sdf ";
	log.info("{}", s.strip());
	log.info("{}", s.trim());
}
}
