package com.shoppingService.patterns.builder;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LombokTest {
	private String os;
	private Integer ram;
	private String processor;
	private BigDecimal screenSize;
	private Integer battery;

}
