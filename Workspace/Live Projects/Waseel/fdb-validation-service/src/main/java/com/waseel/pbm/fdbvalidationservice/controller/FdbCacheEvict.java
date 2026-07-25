package com.waseel.pbm.fdbvalidationservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waseel.pbm.fdbvalidationservice.service.cache.FdbCacheService;

@RestController
@RequestMapping("evict-cache")
public class FdbCacheEvict {
	private final Logger log = LoggerFactory.getLogger(FdbCacheEvict.class);

	@Autowired
	private FdbCacheService fdbCacheService;

	@GetMapping("/all")
	public ResponseEntity<Object> evictAll() {
		log.info("Evicting all cache in [fdb-cache]");
		fdbCacheService.removeAllCache();
		return ResponseEntity.ok().build();
	}
}
