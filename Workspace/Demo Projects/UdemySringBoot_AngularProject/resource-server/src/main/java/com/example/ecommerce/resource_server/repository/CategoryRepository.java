package com.example.ecommerce.resource_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.resource_server.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
