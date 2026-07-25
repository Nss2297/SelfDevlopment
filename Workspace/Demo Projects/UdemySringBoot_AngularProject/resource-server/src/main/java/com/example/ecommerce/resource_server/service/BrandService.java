package com.example.ecommerce.resource_server.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.resource_server.entity.Brand;
import com.example.ecommerce.resource_server.repository.BrandRepository;

@Service
public class BrandService implements IBrandService {

	private final Logger log = LoggerFactory.getLogger(BrandService.class);

	@Autowired
	private BrandRepository brandRepository;

	@Override
	public List<Brand> fetchAllBrands() {
		return brandRepository.findAll();
	}

}
