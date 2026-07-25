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
@Table(name = "BRAND")
@Data
public class Brand implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7788931679814262069L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BRAND_ID", nullable = false, updatable = false)
	private Long brandId;

	@Column(name = "BRAND_NAME", nullable = false, unique = true)
	private String brandName;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "brand")
	@JsonIgnore
	private Set<Product> products;

	public Brand() {
		super();
	}

	public Brand(String brandName) {
		super();
		this.brandName = brandName;
	}

	public Brand(Long brandId) {
		super();
		this.brandId = brandId;
	}

}
