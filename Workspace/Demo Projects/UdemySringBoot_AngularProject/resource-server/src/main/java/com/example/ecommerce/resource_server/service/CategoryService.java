package com.example.ecommerce.resource_server.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.resource_server.entity.Category;
import com.example.ecommerce.resource_server.repository.CategoryRepository;

@Service
public class CategoryService implements ICategoryService {

	private final Logger log = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> fetchCategories() {
		return categoryRepository.findAll();
	}

}
