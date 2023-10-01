package org.devices.specifications.api.service.impl;

import org.devices.specifications.api.model.Brand;
import org.devices.specifications.api.model.Model;
import org.devices.specifications.api.model.Specifications;
import org.devices.specifications.api.service.CacheService;
import org.devices.specifications.api.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class CacheServiceImpl extends CacheService {

    @Autowired
    private Utils utils;

    @Override
    public void saveBrandCache(Set<Brand> brandsToCache) {
        if(brandsToCache != null && !brandsToCache.isEmpty()) {
            this.brandsCache.addAll(brandsToCache);
        }
    }

    @Override
    public void saveModelCache(String brandUrl, Set<Model> modelsToCache) {
        if(brandUrl != null && !brandUrl.trim().isEmpty() && modelsToCache != null && !modelsToCache.isEmpty()) {
            if(modelCache.containsKey(brandUrl.trim())) {
                modelCache.get(brandUrl.trim()).addAll(modelsToCache);
            } else {
                Set<Model> models = new HashSet<>(modelsToCache);
                modelCache.put(brandUrl.trim(), models);
            }
        }
    }

    @Override
    public void saveSpecificationCache(String modelUrl, Specifications specifications) {
        if(modelUrl != null && !modelUrl.trim().isEmpty() && specifications != null) {
            specificationCache.put(modelUrl.trim(), specifications);
        }
    }

    @Override
    public Brand getBrandFromCache(String brandName) {
        if(!brandsCache.isEmpty() && brandName != null && !brandName.trim().isEmpty()) {
            return utils.searchBrand(brandsCache, brandName);
        }
        return null;
    }

    @Override
    public Set<Model> getModelsFromCache(String brandUrl) {
        if(brandUrl != null && !brandUrl.trim().isEmpty()) {
            if(modelCache.containsKey(brandUrl.trim())) {
                return modelCache.get(brandUrl.trim());
            }
        }
        return Collections.emptySet();
    }

    @Override
    public Specifications getSpecificationFromCache(String modelUrl) {
        if(modelUrl != null && !modelUrl.trim().isEmpty()) {
            if(specificationCache.containsKey(modelUrl.trim())) {
                return specificationCache.get(modelUrl.trim());
            }
        }
        return null;
    }

}
