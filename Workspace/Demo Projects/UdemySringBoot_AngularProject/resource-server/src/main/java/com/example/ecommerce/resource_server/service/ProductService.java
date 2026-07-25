package com.example.ecommerce.resource_server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.ecommerce.resource_server.entity.Product;
import com.example.ecommerce.resource_server.model.ProductResponseDTO;
import com.example.ecommerce.resource_server.model.ProductResponseListDTO;
import com.example.ecommerce.resource_server.model.ProductSpecParams;
import com.example.ecommerce.resource_server.repository.ProductRepository;
import com.example.ecommerce.resource_server.specification.ProductSpecificationTitleBrandCategory;

@Service
public class ProductService implements IProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductSpecificationTitleBrandCategory productSpecification;

	@Value("${pagination.page.size.default}")
	private Integer defaultPageSize;

	@Override
	public ProductResponseDTO findByProductId(Long productId) {
		return productRepository.findById(productId).map(product -> {
			ProductResponseDTO responseDTO = new ProductResponseDTO();
			responseDTO.populateProductResponseDTO(product);
			return responseDTO;
		}).orElse(null);
//		Optional<Product> productOpt = productRepository.findById(productId);
//		return null;
	}

	@Override
	public ProductResponseListDTO getProductList(ProductSpecParams params) {
		Integer pageIndex = params.getPageIndex();
		ProductResponseListDTO responseList = null;
		Page<Product> page = null;
		if (null == pageIndex || pageIndex == 0) {
			page = new PageImpl<>(productRepository.findAll(productSpecification.getProducts(params)));
//			if (!products.isEmpty()) {
//				List<ProductResponseDTO> dataList = products.stream().map(product -> {
//					ProductResponseDTO productResponseDTO = new ProductResponseDTO();
//					productResponseDTO.populateProductResponseDTO(product);
//					return productResponseDTO;
//				}).toList();
//				return ProductResponseListDTO.builder().dataList(dataList).totalCount(Long.valueOf(products.size()))
//						.totalPages(0).pageIndex(0).build();
//			}
		} else {
			Integer pageSize = params.getPageSize();
			if (null == pageSize || 0 == pageSize) {
				params.setPageSize(defaultPageSize);
			}
			Pageable pageable = PageRequest.of(params.getPageIndex() - 1, params.getPageSize());
			page = productRepository.findAll(productSpecification.getProducts(params), pageable);
		}
		if (null != page && page.hasContent() && !page.getContent().isEmpty()) {
			List<ProductResponseDTO> dataList = page.getContent().stream().map(product -> {
				ProductResponseDTO productResponseDTO = new ProductResponseDTO();
				productResponseDTO.populateProductResponseDTO(product);
				return productResponseDTO;
			}).toList();
			return ProductResponseListDTO.builder().totalPages(page.getTotalPages()).totalCount(page.getTotalElements())
					.pageIndex(page.getNumber()).dataList(dataList).build();
		}

		return responseList;
	}

	@Override
	public List<Product> fetchTestData() {
		return this.productRepository.findAll();
	}

}
