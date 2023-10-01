package org.devices.specifications.api.controller.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface CacheClient {

    @GetMapping("/cache/clear/all")
    ResponseEntity<String> clearAllCache();

    @GetMapping("/cache/clear/brands")
    ResponseEntity<String> clearBrandsCache();

    @GetMapping("/cache/clear/models")
    ResponseEntity<String> clearModelsCache();

    @GetMapping("/cache/clear/specifications")
    ResponseEntity<String> clearSpecificationsCache();
}
