package com.shoppingService.Test;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test1 {

	public static void main(String args[]) {
		String time= "2022-09-22T18:40:33+05:30";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+SSS");
		
		try {
			Date date = format.parse(time);
//			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			String time1 = timestamp.getHours()+":"+timestamp.getMinutes();
			System.out.println(time1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
