package com.waseel.pbm.fdbvalidationservice.service.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class FdbCacheService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@CacheEvict(value = "fdb-cache", allEntries = true)
	public void removeAllCache() {
		log.info("All cache from fdb-cache is evicted");
	}
}
