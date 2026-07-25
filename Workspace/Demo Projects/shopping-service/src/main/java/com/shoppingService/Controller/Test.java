package com.shoppingService.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static void main(String args[]) throws ParseException {
		SimpleDateFormat thiqahFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date thiqahDate = thiqahFormat.parse("1445-09-02");  
		System.out.println(thiqahDate);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String date = dateFormat.format(thiqahDate);
		Date expiryDate = dateFormat.parse(date);
		System.out.println(expiryDate);
	} 
}
