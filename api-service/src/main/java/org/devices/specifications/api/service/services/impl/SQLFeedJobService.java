package org.devices.specifications.api.service.services.impl;

import org.devices.specifications.api.common.model.Brand;
import org.devices.specifications.api.common.model.Model;
import org.devices.specifications.api.common.model.Property;
import org.devices.specifications.api.common.model.Specifications;
import org.devices.specifications.api.common.utils.Utils;
import org.devices.specifications.api.service.services.ConsumerService;
import org.devices.specifications.api.service.services.FeedJobService;
import org.devices.specifications.api.service.services.helper.SQLFeedHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Qualifier("sqlFeedService")
public class SQLFeedJobService extends FeedJobService {

    @Value("${feed.job.brands.common}")
    private String brandsStringCommon;

    @Value("${feed.job.brands.others}")
    private String brandsStringOthers;

    @Value("${enable.cache}")
    private boolean useCache;

    @Autowired
    private SQLFeedHelper sqlFeedHelper;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private Utils utils;

    @Override
    public Set<Brand> getBrandsSet() {
        Set<Brand> brandsSet = new HashSet<>();
        List<String> brands = getBrandsList(brandsStringCommon);
        if (brands != null && !brands.isEmpty()) {
            for (String brandName : brands) {
                Brand brandObject = consumerService.getBrandByName(brandName, useCache);
                if (brandObject != null) {
                    brandsSet.add(brandObject);
                }
            }
        }
        return brandsSet;
    }

    @Override
    public Map<Brand, Set<Model>> getModelsByBrands(Set<Brand> brands) {
        Map<Brand, Set<Model>> modelsByBrands = new HashMap<>();
        if (brands != null && !brands.isEmpty()) {
            for (Brand brand : brands) {
                Set<Model> models = consumerService.getAllModelsByBrand(brand, false);
                modelsByBrands.put(brand, models);
            }
        }
        return modelsByBrands;
    }

    @Override
    public boolean isBrandModelExists(Brand brand, Model model) {
        return sqlFeedHelper.isBrandModelExists(brand, model);
    }

    @Override
    public int getIterationsCountPerBlock() {
        return 5;
    }

    @Override
    public long getSleepTimePerBlock() {
        Random random = new Random();
        return random.nextInt(6) * 10000;
    }

    @Override
    public Specifications getSpecifications(Brand brand, Model model) {
        return consumerService.getSpecificationsByModel(model, useCache);
    }

    @Override
    public void saveSpecifications(Brand brand, Model model, Specifications specifications) {
        int mapId = sqlFeedHelper.saveOrUpdateBrandModel(brand, model);
        if (mapId != -1) {
            int specificationsId = sqlFeedHelper.saveOrUpdateBriefSpecifications(mapId, specifications);
            if (specificationsId != -1) {
                log("persist successful mapId: {}, specificationsId: {}", mapId, specificationsId);
            }
        }
    }

    @Override
    public Set<Property> getDetailSpecifications(Brand brand, Model model) {
        return consumerService.getDetailSpecificationsByModel(model, useCache);
    }

    @Override
    public void saveDetailSpecifications(Brand brand, Model model, Set<Property> properties) {
        int mapId = sqlFeedHelper.findMapIdByBrandModel(brand, model);
        if (mapId != -1) {
            int savedPropertiesCount = sqlFeedHelper.saveOrUpdateDetailSpecifications(mapId, properties);
            if (savedPropertiesCount == properties.size()) {
                log("persist successful mapId: {}, savedPropertiesCount: {}", mapId, savedPropertiesCount);
            }
        }
    }

    private List<String> getBrandsList(String brandsString) {
        String[] brands = brandsString.split(",");
        return utils.preprocessStringArray(brands);
    }
}
