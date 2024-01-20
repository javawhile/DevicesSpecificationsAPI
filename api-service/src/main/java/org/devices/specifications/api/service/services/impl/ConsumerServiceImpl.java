package org.devices.specifications.api.service.services.impl;

import org.devices.specifications.api.common.fetcher.impl.BrandFetcher;
import org.devices.specifications.api.common.fetcher.impl.PropertyFetcher;
import org.devices.specifications.api.common.fetcher.impl.SpecificationsFetcher;
import org.devices.specifications.api.common.fetcher.impl.ModelFetcher;
import org.devices.specifications.api.common.model.*;
import org.devices.specifications.api.service.services.CacheService;
import org.devices.specifications.api.service.services.ConsumerService;
import org.devices.specifications.api.common.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    private Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);

    @Value("#{'${brands.supported}'.split(',')}")
    private Set<String> brandsSupported;

    private Set<Brand> brandsObjectSupported;

    @Value("${scrap.web.base.url}")
    private String baseUrl;

    @Autowired
    private BrandFetcher brandFetcher;

    @Autowired
    private ModelFetcher modelFetcher;

    @Autowired
    private SpecificationsFetcher specificationsFetcher;

    @Autowired
    private PropertyFetcher propertyFetcher;

    @Autowired
    private Utils utils;

    @Autowired
    private CacheService cacheService;

    @PostConstruct
    public void initObjects() {
        Set<Brand> allBrands = brandFetcher.fetchForUrl(baseUrl, getConnectionConfig());
        if (allBrands == null || allBrands.isEmpty()) {
            logger.error("allBrands cannot be null or empty");
            return;
        }
        if(brandsSupported == null || brandsSupported.isEmpty()) {
            logger.error("brandsSupported cannot be null or empty");
            return;
        }
        if(brandsObjectSupported == null) {
            brandsObjectSupported = new HashSet<>();
        }
        brandsObjectSupported.clear();
        for (String brand : brandsSupported) {
            if (brand != null && !brand.trim().isEmpty()) {
                Brand brandSearched = utils.searchBrand(allBrands, brand);
                if(brandSearched != null) {
                    brandsObjectSupported.add(brandSearched);
                    logger.info("brandName={}, brandUrl={} initialized", brandSearched.getBrandName(), brandSearched.getBrandUrl());
                }
            }
        }
    }

    @Override
    public boolean isBrandSupported(final String brandName) {
        if(brandName != null && !brandName.trim().isEmpty()) {
            boolean isBrandInSupportedSet = brandsSupported.contains(brandName);
            boolean isBrandObjectInitialized = utils.searchBrand(brandsObjectSupported, brandName) != null;
            return isBrandInSupportedSet && isBrandObjectInitialized;
        }
        return false;
    }

    @Override
    public Set<String> getSupportedBrands() {
        Set<String> brandsInitialized = utils.getReadableValuesFromSet(brandsObjectSupported, Brand::getBrandName);
        brandsInitialized.retainAll(brandsSupported);
        return brandsInitialized;
    }

    @Override
    public Brand getBrandByName(final String brandName) {
        if (!isBrandSupported(brandName)) {
            logger.error("getBrandByName: brandName={} not supported", brandName);
            return null;
        }
        return utils.searchBrand(brandsObjectSupported, brandName);
    }

    @Override
    public Set<Model> getAllModelsByBrandName(final String brandName) {
        Brand brand = getBrandByName(brandName);
        if (brand == null || isNotValidString(brand.getBrandUrl())) {
            logger.error("getAllModelsByBrandName: brandName={} not found", brandName);
            return Collections.emptySet();
        }
        Set<Model> models = cacheService.getModels(brand);
        if (models == null || models.isEmpty()) {
            models = modelFetcher.fetchForUrl(brand.getBrandUrl(), getConnectionConfig());
            cacheService.cacheModels(brand, models);
        }
        return models;
    }

    @Override
    public Specifications getSpecificationsByModel(final Model model) {
        if (model == null || isNotValidString(model.getModelUrl())) {
            logger.error("getSpecificationsByModel: model or modelUrl cannot be null or empty");
            return null;
        }
        Specifications specifications = cacheService.getSpecifications(model);
        if(specifications == null) {
            specifications = specificationsFetcher.fetchForUrl(model.getModelUrl(), getConnectionConfig());
            cacheService.cacheSpecifications(model, specifications);
        }
        return specifications;
    }

    @Override
    public Set<Property> getDetailSpecificationsByModel(final Model model) {
        if (model == null || isNotValidString(model.getModelUrl())) {
            logger.error("getSpecificationsByModel: model or modelUrl cannot be null or empty");
            return null;
        }
        Set<Property> properties = cacheService.getDetailSpecifications(model);
        if(properties == null || properties.isEmpty()) {
            properties = propertyFetcher.fetchForUrl(model.getModelUrl(), getConnectionConfig());
            cacheService.cacheDetailSpecifications(model, properties);
        }
        return properties;
    }

    @Override
    public Specifications getSpecificationsByBrandModel(String brandName, String modelName) {
        //GET MODEL=============================================================
        Set<Model> allModelsByBrand = getAllModelsByBrandName(brandName);
        Model model = utils.searchModel(allModelsByBrand, modelName);
        if (model == null) {
            String message = String.format("model=%s not found", modelName);
            logger.error(message);
            throw new RuntimeException(message);
        }

        //GET SPECIFICATIONS==================================================
        Specifications specifications = getSpecificationsByModel(model);
        if (specifications == null) {
            String message = "error while getting specifications";
            logger.error(message);
            throw new RuntimeException(message);
        }

        return specifications;
    }

    @Override
    public Set<Property> getDetailSpecificationsByBrandModel(String brandName, String modelName) {
        //GET MODEL=============================================================
        Set<Model> allModelsByBrand = getAllModelsByBrandName(brandName);
        Model model = utils.searchModel(allModelsByBrand, modelName);
        if (model == null) {
            String message = String.format("model=%s not found", modelName);
            logger.error(message);
            throw new RuntimeException(message);
        }

        //GET DETAILED SPECIFICATIONS==================================================
        Set<Property> properties = getDetailSpecificationsByModel(model);
        if (properties == null || properties.isEmpty()) {
            String message = "error while getting detail specifications";
            logger.error(message);
            throw new RuntimeException(message);
        }

        return properties;
    }

    private boolean isNotValidString(String string) {
        return string == null || string.trim().isEmpty();
    }

    private ConnectionConfig getConnectionConfig() {
        return new ConnectionConfig();
    }
}
