package com.shoppingService.Test;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Test7 {
	private String id;
	@JsonIgnore
	private String name;

	public Test7(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
