package com.example.ecommerce.resource_server.model;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Data
public class ProductSpecParams {

	private Integer pageSize;
	private String search;
	private String sort;
	private Long brandId;
	private Long categoryId;
	private static final Integer MAX_PAGE_SIZE = 20;
	private Integer pageIndex;
	private BigDecimal minPrice;
	private BigDecimal maxPrice;

	public void setPageSize(Integer pageSize) {
		if (null != pageSize && pageSize > 0 && pageSize > MAX_PAGE_SIZE) {
			this.pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}

	public void setSearch(String search) {
		this.search = StringUtils.toRootLowerCase(search);
	}

}
