package com.waseel.prescription.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DateUtil {

	private static final String sdf = "yyyy-MM-dd HH:mm:ss";

	public static Timestamp getTimestampFromString(String dateStr, String format) throws ParseException {
		Date date = new SimpleDateFormat(format).parse(dateStr);
		SimpleDateFormat myFormat = new SimpleDateFormat(sdf);
		return date == null ? null : Timestamp.valueOf(myFormat.format(date));
	}

	public static Timestamp getTimestampFromDate(Date date) {
		SimpleDateFormat myFormat = new SimpleDateFormat(sdf);
		return date == null ? null : Timestamp.valueOf(myFormat.format(date.getTime()));
	}

	public static Timestamp getTimestampOneWeekAgo() {
		SimpleDateFormat myFormat = new SimpleDateFormat(sdf);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -8);
		return Timestamp.valueOf(myFormat.format(cal.getTime()));
	}

	public static String dateToStringCustom(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date.getTime());
	}

	public static Date stringToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(sdf);
		Date date = null;
		try {
			date = formatter.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
