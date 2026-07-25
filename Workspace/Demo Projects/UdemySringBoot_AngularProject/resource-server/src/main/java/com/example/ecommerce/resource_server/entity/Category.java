package com.example.ecommerce.resource_server.entity;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "CATEGORY")
@Data
public class Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2889743371744507031L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CATEGORY_ID", nullable = false, updatable = false)
	private Long categoryId;

	@Column(name = "CATEGORY_NAME", nullable = false, unique = true)
	private String categoryName;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "category")
	@JsonIgnore
	Set<Product> products;

	public Category() {
		super();
	}

	public Category(String categoryName) {
		super();
		this.categoryName = categoryName;
	}

	public Category(Long categoryId) {
		super();
		this.categoryId = categoryId;
	}

}
