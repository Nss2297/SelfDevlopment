package com.shoppingService.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test3 {
public static void main(String args[]) {
	Date date = new Date();
	SimpleDateFormat format = new SimpleDateFormat("MMM-yyyy");
	System.out.println(format.format(date));
}
}
