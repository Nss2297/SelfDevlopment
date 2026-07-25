package com.example.ecommerce.resource_server.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "PRODUCT")
@Data
public class Product implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7915800245914780305L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCT_ID", nullable = false, updatable = false)
	private Long productId;

	@Column(name = "SKU")
	private String sku;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "UNIT_PRICE")
	private BigDecimal unitPrice;

	@Column(name = "IMAGE_URL")
	private String imageUrl;

	@Column(name = "IS_ACTIVE", nullable = false, columnDefinition = "bit default 1")
	private Boolean isActive;

	@Column(name = "UNITS_IN_STOCK")
	private Integer unitsInStock;

	@Column(name = "DATE_CREATED", nullable = false)
	@CreationTimestamp
	private Date dateCreated;

	@Column(name = "LAST_UPDATED", nullable = true)
	@UpdateTimestamp
	private Date lastUpdated;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "categoryId", nullable = false)
	@JsonIgnore
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "brandId", nullable = false)
	@JsonIgnore
	private Brand brand;

	public Product() {
		super();
	}

	public Product(Category category, Brand brand, String sku, String title, String description, BigDecimal unitPrice,
			String imageUrl, Boolean isActive, Integer unitsInStock, Date dateCreated, Date lastUpdated) {
		super();
		this.sku = sku;
		this.title = title;
		this.description = description;
		this.unitPrice = unitPrice;
		this.imageUrl = imageUrl;
		this.isActive = isActive;
		this.unitsInStock = unitsInStock;
		this.dateCreated = dateCreated;
		this.lastUpdated = lastUpdated;
		this.category = category;
		this.brand = brand;
	}

}
