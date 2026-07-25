package com.shoppingService.interview.collectionframework.map;

public class WeakHashMapTempModel {

	@Override
	public String toString() {
		return "WeakHashMap";
	}
	
	public void finalize() {
		System.out.println("Finalize method is invoked.");
	}
}
