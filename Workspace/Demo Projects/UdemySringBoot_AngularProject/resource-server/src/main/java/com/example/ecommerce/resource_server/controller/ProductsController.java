package com.example.ecommerce.resource_server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.resource_server.entity.Brand;
import com.example.ecommerce.resource_server.entity.Category;
import com.example.ecommerce.resource_server.entity.Product;
import com.example.ecommerce.resource_server.model.ProductResponseDTO;
import com.example.ecommerce.resource_server.model.ProductResponseListDTO;
import com.example.ecommerce.resource_server.model.ProductSpecParams;
import com.example.ecommerce.resource_server.service.IBrandService;
import com.example.ecommerce.resource_server.service.ICategoryService;
import com.example.ecommerce.resource_server.service.IProductService;

@RestController
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private IProductService productService;

	@Autowired
	private ICategoryService categoryService;

	@Autowired
	private IBrandService brandService;

	@GetMapping("/categories")
	public ResponseEntity<List<Category>> getCategories() {
		return ResponseEntity.ok(categoryService.fetchCategories());
	}

	@GetMapping("/brands")
	public ResponseEntity<List<Brand>> getBrands() {
		return ResponseEntity.ok(brandService.fetchAllBrands());
	}

	@GetMapping
	public ResponseEntity<ProductResponseListDTO> fetchProducts(ProductSpecParams params) {
		return ResponseEntity.ok(productService.getProductList(params));
	}

	@GetMapping("/{productId}")
	public ResponseEntity<ProductResponseDTO> fetchProductDetailsById(@PathVariable Long productId) {
		return ResponseEntity.ok(productService.findByProductId(productId));
	}

	@GetMapping("/test")
	public ResponseEntity<List<Product>> testData() {
		return ResponseEntity.ok(productService.fetchTestData());
	}

//	@GetMapping
//	public List<String> getProducts() {
//		String[] products = new String[] { "Shoes-01", "Jackets-01", "Suits-01" };
//		return Arrays.asList(products);
//	}
//	@GetMapping("/categories")
//	public List<String> getCategories() {
//		String[] categories = new String[] { "Shoes", "Jackets", "Suits" };
//		return Arrays.asList(categories);
//	}

//	@GetMapping("/brands")
//	public List<String> getBrands() {
//		String[] categories = new String[] { "Shoes", "Jackets", "Suits" };
//		return Arrays.asList(categories);
//	}
}
