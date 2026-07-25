package com.example.ecommerce.resource_server.specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.example.ecommerce.resource_server.entity.Product;
import com.example.ecommerce.resource_server.model.ProductSpecParams;

import jakarta.persistence.criteria.Predicate;

@Component
public class ProductSpecificationTitleBrandCategory {

	private static final Long DEFAULT_LONG_VALUE = Long.valueOf("0");
	private static final String SORT_PRICE_ASSCENDING_STRING = "priceAsc";
	private static final String SORT_PRICE_DESCENDING_STRING = "priceDesc";

	public Specification<Product> getProducts(ProductSpecParams params) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			String search = params.getSearch();
			if (StringUtils.isNotBlank(search)) {
				predicates.add(criteriaBuilder.like(root.get("title"), "%" + search + "%"));
			}
			Long categoryId = params.getCategoryId();
			if (null != categoryId && categoryId > DEFAULT_LONG_VALUE) {
				predicates.add(criteriaBuilder.equal(root.get("category").get("categoryId"), categoryId));
			}
			Long brandId = params.getBrandId();
			if (null != brandId && brandId > DEFAULT_LONG_VALUE) {
				predicates.add(criteriaBuilder.equal(root.get("brand").get("brandId"), brandId));
			}
			BigDecimal minPrice = params.getMinPrice();
			BigDecimal maxPrice = params.getMaxPrice();
			if (null != minPrice && null != maxPrice) {
				predicates.add(criteriaBuilder.between(root.get("unitPrice"), minPrice, maxPrice));
			}
			String sort = params.getSort();
			if (StringUtils.isNotBlank(sort)) {
				switch (sort) {
				case SORT_PRICE_ASSCENDING_STRING:
					query.orderBy(criteriaBuilder.asc(root.get("unitPrice")));
					break;
				case SORT_PRICE_DESCENDING_STRING:
					query.orderBy(criteriaBuilder.desc(root.get("unitPrice")));
					break;
				default:
					query.orderBy(criteriaBuilder.asc(root.get("title")));
				}
			}
			return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
		};

	}
}
