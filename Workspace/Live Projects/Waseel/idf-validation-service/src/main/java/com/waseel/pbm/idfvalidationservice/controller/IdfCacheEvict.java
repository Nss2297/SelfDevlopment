package com.waseel.pbm.idfvalidationservice.controller;

import com.waseel.pbm.idfvalidationservice.service.cache.IdfCacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("evict-cache")
public class IdfCacheEvict {
    private final Logger log = LoggerFactory.getLogger(IdfCacheEvict.class);

    @Autowired
    private IdfCacheService configuredModulesCacheService;

    @GetMapping("/all")
    public ResponseEntity<IdfCacheEvict> evictAll() {
        log.info("Evicting all cache in [idf-cache]");
        configuredModulesCacheService.removeAllCache();
        return ResponseEntity.ok().build();
    }

    @GetMapping("configured-modules/{payerId}")
    public ResponseEntity<IdfCacheEvict> evictByPayerId(@PathVariable String payerId) {
        log.info("Evicting cache {} By IDF-Validation Modules", payerId);
        configuredModulesCacheService.removeConfiguredModulesfromCacheByPayer(payerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("configured-modules/{payerId}/{moduleId}")
    public ResponseEntity<IdfCacheEvict> evictByModuleId(@PathVariable String payerId, @PathVariable String moduleId) {
        log.info("Evicting cache for payer:{} and module:{}", payerId, moduleId);
        configuredModulesCacheService.removeConfiguredModulesfromCacheByModuleId(payerId, moduleId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("common-rejections/{rejectionCode}")
    public ResponseEntity<IdfCacheEvict> evictCommonRejections(@PathVariable String rejectionCode) {
        log.info("Evicting cache for common rejections");
        configuredModulesCacheService.removeCommonRejectionsfromCache(rejectionCode);
        return ResponseEntity.ok().build();
    }
}
