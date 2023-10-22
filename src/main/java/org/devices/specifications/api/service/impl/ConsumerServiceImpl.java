package org.devices.specifications.api.service.impl;

import org.devices.specifications.api.fetcher.BrandFetcher;
import org.devices.specifications.api.fetcher.PropertyFetcher;
import org.devices.specifications.api.fetcher.SpecificationsFetcher;
import org.devices.specifications.api.fetcher.ModelFetcher;
import org.devices.specifications.api.model.Property;
import org.devices.specifications.api.service.CacheService;
import org.devices.specifications.api.service.ConsumerService;
import org.devices.specifications.api.model.Brand;
import org.devices.specifications.api.model.Specifications;
import org.devices.specifications.api.model.Model;
import org.devices.specifications.api.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private BrandFetcher brandFetcher;

    @Autowired
    private ModelFetcher modelFetcher;

    @Autowired
    private SpecificationsFetcher specificationsFetcher;

    @Autowired
    private PropertyFetcher propertyFetcher;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private Utils utils;

    @Override
    public Set<Brand> getAllBrands(boolean useCache) {
        if (useCache) {
            //SEARCH IN CACHE
            Set<Brand> brandsCache = cacheService.getAllBrands();
            if (!brandsCache.isEmpty()) {
                return brandsCache;
            }
            return getAllBrands(false);
        }
        Set<Brand> allBrandsFromSource = brandFetcher.getAllBrands();
        cacheService.saveBrands(allBrandsFromSource);
        return allBrandsFromSource;
    }

    @Override
    public Brand getBrandByName(String brandName, boolean useCache) {
        if (useCache) {
            //SEARCH IN CACHE
            Brand brand = cacheService.getBrand(brandName);
            if (brand != null && isValidString(brand.getBrandUrl())) {
                return brand;
            }
            return getBrandByName(brandName, false);
        }
        Set<Brand> allBrandsFromSource = getAllBrands(false);
        return utils.searchBrand(allBrandsFromSource, brandName);
    }

    @Override
    public Set<Model> getAllModelsByBrand(Brand brand, boolean useCache) {
        if (brand == null || !isValidString(brand.getBrandUrl())) {
            return Collections.emptySet();
        }
        if (useCache) {
            //SEARCH IN CACHE
            Set<Model> possibleModels = cacheService.getModels(brand.getBrandUrl());
            if (possibleModels != null && !possibleModels.isEmpty()) {
                return possibleModels;
            }
            return getAllModelsByBrand(brand, false);
        }
        Set<Model> allModels = modelFetcher.getAllModels(brand.getBrandUrl());
        cacheService.saveModels(brand.getBrandUrl(), allModels);
        return allModels;
    }

    @Override
    public Set<Model> getAllModelsByBrandName(String brandName, boolean useCache) {
        return getAllModelsByBrand(getBrandByName(brandName, useCache), useCache);
    }

    @Override
    public Specifications getSpecificationsByModel(Model model, boolean useCache) {
        if (model == null || !isValidString(model.getModelUrl())) {
            return null;
        }
        if (useCache) {
            //SEARCH IN CACHE
            Specifications specifications = cacheService.getSpecification(model.getModelUrl());
            if (specifications != null) {
                return specifications;
            }
            return getSpecificationsByModel(model, false);
        }
        Specifications specifications = specificationsFetcher.fetchForUrl(model.getModelUrl());
        cacheService.saveSpecifications(model.getModelUrl(), specifications);
        return specifications;
    }

    @Override
    public Set<Property> getDetailSpecificationsByModel(Model model, boolean useCache) {
        if (model == null || !isValidString(model.getModelUrl())) {
            return null;
        }
        if (useCache) {
            //SEARCH IN CACHE
            Set<Property> properties = cacheService.getDetailSpecification(model.getModelUrl());
            if (properties != null) {
                return properties;
            }
            return getDetailSpecificationsByModel(model, false);
        }
        Set<Property> properties = propertyFetcher.fetchForUrl(model.getModelUrl());
        cacheService.saveDetailSpecifications(model.getModelUrl(), properties);
        return properties;
    }

    @Override
    public Specifications getSpecificationsByBrandModel(String brandName, String modelName, boolean useCache) {
        //GET BRAND===============================================================
        Brand brand = getBrandByName(brandName, useCache);
        if (brand == null) {
            String message = String.format("brand=%s not found", brandName);
            throw new RuntimeException(message);
        }

        //GET MODEL=============================================================
        Set<Model> allModelsByBrand = getAllModelsByBrandName(brandName, useCache);
        Model model = utils.searchModel(allModelsByBrand, modelName);
        if (model == null) {
            String message = String.format("model=%s not found", modelName);
            throw new RuntimeException(message);
        }

        //GET SPECIFICATIONS==================================================
        Specifications specifications = getSpecificationsByModel(model, useCache);
        if (specifications == null) {
            String message = "error while getting specifications";
            throw new RuntimeException(message);
        }

        return specifications;
    }

    @Override
    public Set<Property> getDetailSpecificationsByBrandModel(String brandName, String modelName, boolean useCache) {
        //GET BRAND===============================================================
        Brand brand = getBrandByName(brandName, useCache);
        if (brand == null) {
            String message = String.format("brand=%s not found", brandName);
            throw new RuntimeException(message);
        }

        //GET MODEL=============================================================
        Set<Model> allModelsByBrand = getAllModelsByBrandName(brandName, useCache);
        Model model = utils.searchModel(allModelsByBrand, modelName);
        if (model == null) {
            String message = String.format("model=%s not found", modelName);
            throw new RuntimeException(message);
        }

        //GET DETAILED SPECIFICATIONS==================================================
        Set<Property> properties = getDetailSpecificationsByModel(model, useCache);
        if (properties == null || properties.isEmpty()) {
            String message = "error while getting detail specifications";
            throw new RuntimeException(message);
        }

        return properties;
    }

    private boolean isValidString(String string) {
        return string != null && !string.trim().isEmpty();
    }
}
