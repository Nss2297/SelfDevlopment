package com.shoppingService.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test9 {
	public static void main(String args[]) throws ParseException {
		SimpleDateFormat sameDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String payerDate = sameDateFormat.format(new Date());
		Date payerMeetingDate = sameDateFormat.parse(payerDate);
		Date payerMeetingDate1 = sameDateFormat.parse(payerDate);
		if(payerMeetingDate.compareTo(payerMeetingDate1)==0) {
			System.out.println(payerMeetingDate);
		}
	}
}
