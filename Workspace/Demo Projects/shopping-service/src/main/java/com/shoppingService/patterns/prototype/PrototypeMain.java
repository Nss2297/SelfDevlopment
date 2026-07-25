package com.shoppingService.patterns.prototype;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrototypeMain {

	public static void main(String[] args) throws CloneNotSupportedException {
		Shop shop = new Shop();
		shop.setShopName("Novelty");
		shop.fetchBooks();
		Shop shop2 = shop.clone();
		shop.getBooks().remove(0);
		shop2.setShopName("A1");
		log.info("{}", shop);
		log.info("{}", shop2);
	}
}
