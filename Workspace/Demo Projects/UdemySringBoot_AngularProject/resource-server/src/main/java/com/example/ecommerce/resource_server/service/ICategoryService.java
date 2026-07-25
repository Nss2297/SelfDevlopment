package com.example.ecommerce.resource_server.service;

import java.util.List;

import com.example.ecommerce.resource_server.entity.Category;

public interface ICategoryService {

	public List<Category> fetchCategories();

}
