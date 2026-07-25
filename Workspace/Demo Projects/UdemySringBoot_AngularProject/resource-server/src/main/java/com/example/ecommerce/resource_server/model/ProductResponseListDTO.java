package com.example.ecommerce.resource_server.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductResponseListDTO {

	private Integer totalPages;
	private Long totalCount;
	private Integer pageIndex;
	private List<ProductResponseDTO> dataList;

	public ProductResponseListDTO() {
		this.dataList = new ArrayList<>();
	}
}
