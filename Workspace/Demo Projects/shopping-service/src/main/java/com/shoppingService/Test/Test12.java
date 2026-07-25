package com.shoppingService.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test12 {
	private static final Logger log = LoggerFactory.getLogger(Test12.class);

	public static void main(String args[]) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -7);
		Date previousDate = cal.getTime();
		log.info("Startdate:-[{}] EndDate:-[{}]", dateFormat.parse(dateFormat.format(previousDate)),
				dateFormat.parse(dateFormat.format(date)));
	}
}
