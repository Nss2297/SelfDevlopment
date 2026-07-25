package com.example.ecommerce.resource_server.model;

import java.math.BigDecimal;

import com.example.ecommerce.resource_server.entity.Brand;
import com.example.ecommerce.resource_server.entity.Category;
import com.example.ecommerce.resource_server.entity.Product;

import lombok.Data;

@Data
public class ProductResponseDTO {

	private Long productId;
	private BigDecimal unitPrice;
	private Long brandId;
	private Long categoryId;
	private String title;
	private String sku;
	private String description;
	private String categoryName;
	private String brandName;
	private String imageUrl;

	public void populateProductResponseDTO(Product product) {
		this.productId = product.getProductId();
		this.unitPrice = product.getUnitPrice();
		Brand brand = product.getBrand();
		this.brandId = brand.getBrandId();
		Category category = product.getCategory();
		this.categoryId = category.getCategoryId();
		this.title = product.getTitle();
		this.sku = product.getSku();
		this.description = product.getDescription();
		this.categoryName = category.getCategoryName();
		this.brandName = brand.getBrandName();
		this.imageUrl = product.getImageUrl();
	}
}
