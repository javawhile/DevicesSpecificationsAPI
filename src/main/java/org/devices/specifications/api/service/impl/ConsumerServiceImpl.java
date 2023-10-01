package org.devices.specifications.api.service.impl;

import org.devices.specifications.api.fetcher.BrandFetcher;
import org.devices.specifications.api.fetcher.SpecificationsFetcher;
import org.devices.specifications.api.fetcher.ModelFetcher;
import org.devices.specifications.api.service.CacheService;
import org.devices.specifications.api.service.ConsumerService;
import org.devices.specifications.api.model.Brand;
import org.devices.specifications.api.model.Specifications;
import org.devices.specifications.api.model.Model;
import org.devices.specifications.api.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private CacheService cacheService;

    @Autowired
    private Utils utils;

    @Override
    public Set<Brand> getAllBrands(boolean useCache) {
        if(useCache) {
            Set<Brand> brandsCache = cacheService.getBrandsCache();
            if(!brandsCache.isEmpty()) {
                return brandsCache;
            }
            return getAllBrands(false);
        }
        Set<Brand> allBrandsFromSource = brandFetcher.getAllBrands();
        cacheService.saveBrandCache(allBrandsFromSource);
        return allBrandsFromSource;
    }

    @Override
    public Brand getBrandByName(String brandName, boolean useCache) {
        if(useCache) {
            //SEARCH IN CACHE
            Brand brand = cacheService.getBrandFromCache(brandName);
            if(brand == null || brand.getBrandUrl() == null || brand.getBrandUrl().trim().isEmpty()) {
                return getBrandByName(brandName, false);
            }
            return brand;
        }

        Set<Brand> allBrandsFromSource = getAllBrands(false);
        return utils.searchBrand(allBrandsFromSource, brandName);
    }

    @Override
    public Set<Model> getAllModelsByBrandName(String brandName) {
        Brand brand = getBrandByName(brandName, true);
        if(brand != null && brand.getBrandUrl() != null && !brand.getBrandUrl().trim().isEmpty()) {
            return getAllModelsByBrandUrl(brand.getBrandUrl().trim(), true);
        }
        return null;
    }

    @Override
    public Set<Model> getAllModelsByBrandUrl(String brandUrl, boolean useCache) {
        if(useCache) {
            Set<Model> possibleModels = cacheService.getModelsFromCache(brandUrl);
            if(possibleModels != null && !possibleModels.isEmpty()) {
                return possibleModels;
            }
            return getAllModelsByBrandUrl(brandUrl, false);
        }
        Set<Model> allModels = modelFetcher.getAllModels(brandUrl);
        cacheService.saveModelCache(brandUrl, allModels);
        return allModels;
    }

    @Override
    public Model getModelByName(String brandUrl, String modelName, boolean useCache) {
        if(useCache) {
            //SEARCH IN CACHE
            Set<Model> possibleModels = cacheService.getModelsFromCache(brandUrl);
            if(possibleModels == null || possibleModels.isEmpty()) {
                return getModelByName(brandUrl, modelName, false);
            }
            return utils.searchModel(possibleModels, modelName);
        }
        Set<Model> allModels = getAllModelsByBrandUrl(brandUrl, useCache);
        if(allModels == null || allModels.isEmpty()) {
            return null;
        }
        return utils.searchModel(allModels, modelName);
    }

    @Override
    public Specifications getSpecificationsByUrl(String modelUrl, boolean useCache) {
        if(useCache) {
            //SEARCH IN CACHE
            Specifications specifications = cacheService.getSpecificationFromCache(modelUrl);
            if(specifications == null) {
                return getSpecificationsByUrl(modelUrl, false);
            }
            return specifications;
        }
        Specifications specifications = specificationsFetcher.fetchForUrl(modelUrl);
        if(specifications != null) {
            cacheService.saveSpecificationCache(modelUrl, specifications);
        }
        return specifications;
    }

    @Override
    public Specifications getSpecificationsByBrandModel(String brandName, String modelName) {
        //GET BRAND STARTED===============================================================
        Brand brand = getBrandByName(brandName, true);
        if(brand == null) {
            String message = String.format("brand=%s not found", brandName);
            throw new RuntimeException(message);
        }
        //GET BRAND ENDED===============================================================

        //GET MODEL STARTED=============================================================
        Model model = getModelByName(brand.getBrandUrl(), modelName, true);
        if(model == null) {
            String message = String.format("model=%s not found", modelName);
            throw new RuntimeException(message);
        }
        //GET MODEL ENDED=============================================================

        //GET SPECIFICATIONS STARTED==================================================
        Specifications specifications = getSpecificationsByUrl(model.getModelUrl(), true);
        if(specifications == null) {
            String message = "error while getting specifications";
            throw new RuntimeException(message);
        }
        //GET SPECIFICATIONS ENDED====================================================

        return specifications;
    }
}
