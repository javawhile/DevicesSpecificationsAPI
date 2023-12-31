package org.devices.specifications.api.service.controller;

import org.devices.specifications.api.service.controller.client.CacheClient;
import org.devices.specifications.api.service.services.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CacheController implements CacheClient {

    @Autowired
    private CacheService cacheService;

    @Override
    public ResponseEntity<String> clearAllCache() {
        return getResponseEntity(String.format("%d items cleared", cacheService.resetAllCache()));
    }

    @Override
    public ResponseEntity<String> clearBrandsCache() {
        return getResponseEntity(String.format("%d items cleared", cacheService.resetBrandsCache()));
    }

    @Override
    public ResponseEntity<String> clearModelsCache() {
        return getResponseEntity(String.format("%d items cleared", cacheService.resetModelsCache()));
    }

    @Override
    public ResponseEntity<String> clearSpecificationsCache() {
        return getResponseEntity(String.format("%d items cleared", cacheService.resetSpecificationsCache()));
    }

    @Override
    public ResponseEntity<String> clearDetailSpecificationsCache() {
        return getResponseEntity(String.format("%d items cleared", cacheService.resetDetailSpecificationsCache()));
    }

    private <T> ResponseEntity<T> getResponseEntity(T data) {
        return new ResponseEntity<T>(data, HttpStatus.OK);

    }
}
