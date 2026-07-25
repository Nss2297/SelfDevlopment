package com.shoppingService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

	@Autowired
	private RestTemplate restTemplate;
	
	@GetMapping("/shopPaynow/{price}")
	public String invokePaynow(@PathVariable String price) throws Exception {
		return this.restTemplate.getForObject("http://PAYMENT-SERVICE/payment-provider/payNow/"+price, String.class);
	}
}
