package org.devices.specifications.api.service.impl;

import io.swagger.models.auth.In;
import org.devices.specifications.api.model.Brand;
import org.devices.specifications.api.model.Model;
import org.devices.specifications.api.model.Specifications;
import org.devices.specifications.api.service.CacheService;
import org.devices.specifications.api.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private Utils utils;

    @Autowired
    @Qualifier("brandsCache")
    private Map<String, Brand> brandsCache;

    @Autowired
    @Qualifier("modelsCache")
    private Map<String, Set<Model>> modelsCache;

    @Autowired
    @Qualifier("specificationsCache")
    private Map<String, Specifications> specificationsCache;

    @Override
    public Integer resetAllCache() {
        Integer size = brandsCache.size() + modelsCache.size() + specificationsCache.size();
        brandsCache.clear();
        modelsCache.clear();
        specificationsCache.clear();
        return size;
    }

    @Override
    public Integer resetBrandsCache() {
        Integer size = brandsCache.size();
        brandsCache.clear();
        return size;
    }

    @Override
    public Integer resetModelsCache() {
        Integer size = modelsCache.size();
        modelsCache.clear();
        return size;
    }

    @Override
    public Integer resetSpecificationsCache() {
        Integer size = specificationsCache.size();
        specificationsCache.clear();
        return size;
    }

    @Override
    public void saveBrand(String brandName, Brand brand) {
        if(isValidString(brandName) && brand != null) {
            brandsCache.put(brandName, brand);
        }
    }

    @Override
    public void saveBrands(Set<Brand> brands) {
        if(brands != null && !brands.isEmpty()) {
            for(Brand brand: brands) {
                if(brand != null) {
                    saveBrand(brand.getBrandName(), brand);
                }
            }
        }
    }

    @Override
    public void saveModels(String brandUrl, Set<Model> models) {
        if(isValidString(brandUrl) && models != null && !models.isEmpty()) {
            if(modelsCache.containsKey(brandUrl)) {
                Set<Model> modelsFromCache = modelsCache.get(brandUrl);
                if(modelsFromCache != null && !modelsFromCache.isEmpty()) {
                    models.addAll(modelsFromCache);
                }
            }
            modelsCache.put(brandUrl, models);
        }
    }

    @Override
    public void saveSpecifications(String modelUrl, Specifications specifications) {
        if(isValidString(modelUrl) && specifications != null) {
            specificationsCache.put(modelUrl, specifications);
        }
    }

    @Override
    public Brand getBrand(String brandName) {
        if(isValidString(brandName) && brandsCache.containsKey(brandName)) {
            return utils.searchBrand(getAllBrands(), brandName);
        }
        return null;
    }

    @Override
    public Set<Brand> getAllBrands() {
        Set<Brand> brands = new HashSet<>();
        for (Map.Entry<String, Brand> stringBrandEntry : brandsCache.entrySet()) {
            brands.add(stringBrandEntry.getValue());
        }
        return brands;
    }

    @Override
    public Set<Model> getModels(String brandUrl) {
        if(isValidString(brandUrl) && modelsCache.containsKey(brandUrl)) {
            return modelsCache.get(brandUrl);
        }
        return Collections.emptySet();
    }

    @Override
    public Specifications getSpecification(String modelUrl) {
        if(isValidString(modelUrl)) {
            return specificationsCache.get(modelUrl);
        }
        return null;
    }

    private boolean isValidString(String string) {
        return  string != null && !string.trim().isEmpty();
    }
}
