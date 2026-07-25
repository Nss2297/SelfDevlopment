package com.shoppingService.patterns.factorymethod;

public class OsFactory {
	public Os fetchOs(String os) {
		if (os.equalsIgnoreCase("Android")) {
			return new Android();
		} else if (os.equalsIgnoreCase("Windows")) {
			return new Windows();
		} else if (os.equalsIgnoreCase("Ios")) {
			return new Ios();
		}
		return null;
	}
}
