package com.shoppingService.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test4 {

	public static void main(String args[]) throws ParseException {
		String paymentDate = "23-05-2022";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = dateFormat.parse(paymentDate);
		
		System.out.println(date);
	}
}
