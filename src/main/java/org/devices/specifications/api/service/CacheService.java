package org.devices.specifications.api.service;

import org.devices.specifications.api.model.Brand;
import org.devices.specifications.api.model.Specifications;
import org.devices.specifications.api.model.Model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class CacheService {

    protected final Set<Brand> brandsCache = new HashSet<>();
    protected final Map<String, Set<Model>> modelCache = new HashMap<>();
    protected final Map<String, Specifications> specificationCache = new HashMap<>();

    public void resetCache() {
        brandsCache.clear();
        modelCache.clear();
        specificationCache.clear();
    }

    public abstract void saveBrandCache(Set<Brand> brandsToCache);
    public abstract void saveModelCache(String brandUrl, Set<Model> modelsToCache);
    public abstract void saveSpecificationCache(String modelUrl, Specifications specifications);
    public abstract Brand getBrandFromCache(String brandName);
    public abstract Set<Model> getModelsFromCache(String brandUrl);
    public abstract Specifications getSpecificationFromCache(String modelUrl);

    public Set<Brand> getBrandsCache() {
        return brandsCache;
    }
}
