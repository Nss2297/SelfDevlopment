package com.waseel.pbm.dssservice.service.cache;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class DSSCacheService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@CacheEvict(value = "dss-cache", key = "#payerId")
	public void removePayerConfigfromCacheByPayer(String payerId) {
		log.info("PayerConfig Object Removed from the Cache for PayerId ID [{}]", payerId);
	}
	
	@CacheEvict(value = "dss-cache", key = "#payerIdEnabled")
	public void removePayerConfigfromCacheByEnabledPayer(String payerIdEnabled) {
		log.info("PayerConfig Object Removed from the Cache for Enabled PayerId ID [{}]", payerIdEnabled);
	}

	@CacheEvict(value = "dss-cache", key = "{#payerId, #moduleId}")
	public void removeConfiguredModulesfromCacheByModuleId(String payerId, String moduleId) {
		log.info("ConfiguredModules Object Removed from the Cache for PayerId ID [{}] and ModuleID [{}]", payerId,
				moduleId);
	}

	@CacheEvict(value = "dss-cache", key = "{#payerId, #moduleIdList}")
	public void removeConfiguredModulesfromCacheByListofModuleId(String payerId, List<Double> moduleIdList) {
		log.info("ConfiguredModules Object Removed from the Cache for PayerId ID [{}] and List of ModuleIds [{}]",
				payerId, moduleIdList);
	}

	@CacheEvict(value = "dss-cache", key = "#rejectionCode")
	public void removeCommonRejectionsfromCache(String rejectionCode) {
		log.info("CommonRejections Object Removed from the Cache for Rejection Code [{}]", rejectionCode);
	}

	@CacheEvict(value = "dss-cache", allEntries = true)
	public void removeAllCache() {
		log.info("All cache from dss-cache is evicted");
	}

	@CacheEvict(value = { "dss-cache", "idf-cache", "fdb-cache" }, allEntries = true)
	public void removeAllServicesCache() {
		log.info("All cache from all PBM services is evicted");
	}

}
