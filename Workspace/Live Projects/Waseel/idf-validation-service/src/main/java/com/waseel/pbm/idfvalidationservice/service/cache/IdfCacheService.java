package com.waseel.pbm.idfvalidationservice.service.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class IdfCacheService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@CacheEvict(value = "idf-cache", key = "#payerId")
	public void removeConfiguredModulesfromCacheByPayer(String payerId) {
		log.info("ConfiguredModules Object Removed from the Cache for PayerId ID [{}]", payerId);
	}

	@CacheEvict(value = "idf-cache", key = "{#payerId, #moduleId}")
	public void removeConfiguredModulesfromCacheByModuleId(String payerId, String moduleId) {
		log.info("ConfiguredModules Object Removed from the Cache for PayerId ID [{}] and ModuleID [{}]", payerId,
				moduleId);
	}

	@CacheEvict(value = "idf-cache", key = "#rejectionCode")
	public void removeCommonRejectionsfromCache(String rejectionCode) {
		log.info("CommonRejections Object Removed from the Cache for Rejection Code [{}]", rejectionCode);
	}

	@CacheEvict(value = "idf-cache", allEntries = true)
	public void removeAllCache() {
		log.info("All cache from idf-cache is evicted");
	}
}
