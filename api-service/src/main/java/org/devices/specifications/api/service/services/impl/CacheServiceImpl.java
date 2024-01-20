package org.devices.specifications.api.service.services.impl;

import org.devices.specifications.api.common.model.Brand;
import org.devices.specifications.api.common.model.Model;
import org.devices.specifications.api.common.model.Property;
import org.devices.specifications.api.common.model.Specifications;
import org.devices.specifications.api.service.services.CacheService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class CacheServiceImpl implements CacheService {

    private final Map<Brand, Set<Model>> modelsCacheMap = new HashMap<>();

    private final Map<Model, Specifications> specificationsCacheMap = new HashMap<>();

    private final Map<Model, Set<Property>> detailSpecificationsCacheMap = new HashMap<>();

    @Override
    public Set<Model> getModels(final Brand brand) {
        return modelsCacheMap.getOrDefault(brand, new HashSet<>());
    }

    @Override
    public void cacheModels(final Brand brand, final Set<Model> models) {
        Set<Model> cachedModels = modelsCacheMap.getOrDefault(brand, new HashSet<>());
        cachedModels.addAll(models);
        modelsCacheMap.put(brand, cachedModels);
    }

    @Override
    public Specifications getSpecifications(final Model model) {
        return specificationsCacheMap.getOrDefault(model, null);
    }

    @Override
    public void cacheSpecifications(final Model model, final Specifications specifications) {
        specificationsCacheMap.put(model, specifications);
    }

    @Override
    public Set<Property> getDetailSpecifications(final Model model) {
        return detailSpecificationsCacheMap.getOrDefault(model, new HashSet<>());
    }

    @Override
    public void cacheDetailSpecifications(final Model model, final Set<Property> properties) {
        Set<Property> cachedProperties = detailSpecificationsCacheMap.getOrDefault(model, new HashSet<>());
        cachedProperties.addAll(properties);
        detailSpecificationsCacheMap.put(model, cachedProperties);
    }
}
