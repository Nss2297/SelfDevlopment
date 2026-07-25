package com.waseel.pbm.dssservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waseel.pbm.dssservice.service.cache.DSSCacheService;

@RestController
@RequestMapping("evict-cache")
public class DssCacheEvict {
	private final Logger log = LoggerFactory.getLogger(DssCacheEvict.class);

	@Autowired
	private DSSCacheService configuredModulesCacheService;

	@GetMapping("/all")
	public ResponseEntity<Object> evictAll() {
		log.info("Evicting all cache in [dss-cache]");
		configuredModulesCacheService.removeAllCache();
		return ResponseEntity.ok().build();
	}

	@GetMapping("/all-services")
	public ResponseEntity<Object> evictAllServices() {
		log.info("Evicting cache for all PBM services");
		configuredModulesCacheService.removeAllServicesCache();
		return ResponseEntity.ok().build();
	}

	@GetMapping("payer-config/{payerId}")
	public ResponseEntity<Object> evictByPayerId(@PathVariable String payerId) {
		log.info("Evicting cache {} By DSS Modules", payerId);
		configuredModulesCacheService.removePayerConfigfromCacheByPayer(payerId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("payer-config/{payerIdEnabled}")
	public ResponseEntity<Object> evictByEnabledPayerId(@PathVariable String payerIdEnabled) {
		log.info("Evicting cache {} By DSS Modules", payerIdEnabled);
		configuredModulesCacheService.removePayerConfigfromCacheByPayer(payerIdEnabled);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("configured-modules/{payerId}/{moduleId}")
	public ResponseEntity<Object> evictByPayerIdAndModuleId(@PathVariable String payerId,
			@PathVariable String moduleId) {
		log.info("Evicting cache for payer: {}  and module: {} ", payerId, moduleId);
		configuredModulesCacheService.removeConfiguredModulesfromCacheByModuleId(payerId, moduleId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("configured-modules/{payerId}")
	public ResponseEntity<Object> evictByPayerIdAndModuleIds(@PathVariable String payerId,
			@RequestParam(name = "moduleIds", required = true) List<Double> moduleIds) {
		log.info("Evicting cache for payer: {}  and module: {} ", payerId, moduleIds);
		configuredModulesCacheService.removeConfiguredModulesfromCacheByListofModuleId(payerId, moduleIds);
		return ResponseEntity.ok().build();
	}

	@GetMapping("common-rejections/{rejectionCode}")
	public ResponseEntity<Object> evictCommonRejections(@PathVariable String rejectionCode) {
		log.info("Evicting cache for common rejections");
		configuredModulesCacheService.removeCommonRejectionsfromCache(rejectionCode);
		return ResponseEntity.ok().build();
	}
}
