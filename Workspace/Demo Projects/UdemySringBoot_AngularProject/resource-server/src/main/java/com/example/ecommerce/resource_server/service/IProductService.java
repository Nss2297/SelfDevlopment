package com.example.ecommerce.resource_server.service;

import java.util.List;

import com.example.ecommerce.resource_server.entity.Product;
import com.example.ecommerce.resource_server.model.ProductResponseDTO;
import com.example.ecommerce.resource_server.model.ProductResponseListDTO;
import com.example.ecommerce.resource_server.model.ProductSpecParams;

public interface IProductService {

	public ProductResponseDTO findByProductId(Long productId);

	public ProductResponseListDTO getProductList(ProductSpecParams params);

	public List<Product> fetchTestData();
}
