package com.example.ecommerce.resource_server.service;

import java.util.List;

import com.example.ecommerce.resource_server.entity.Brand;

public interface IBrandService {

	public List<Brand> fetchAllBrands();
}
