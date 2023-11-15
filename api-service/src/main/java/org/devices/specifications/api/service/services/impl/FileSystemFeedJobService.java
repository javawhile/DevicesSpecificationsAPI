package org.devices.specifications.api.service.services.impl;

import org.devices.specifications.api.common.model.Brand;
import org.devices.specifications.api.common.model.Model;
import org.devices.specifications.api.common.model.Property;
import org.devices.specifications.api.common.model.Specifications;
import org.devices.specifications.api.common.utils.Utils;
import org.devices.specifications.api.service.services.ConsumerService;
import org.devices.specifications.api.service.services.FeedJobService;
import org.devices.specifications.api.service.services.helper.FileSystemFeedHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class FileSystemFeedJobService extends FeedJobService {

    @Value("${feed.job.brands.common}")
    private String brandsStringCommon;

    @Value("${feed.job.brands.others}")
    private String brandsStringOthers;

    @Value("${enable.cache}")
    private boolean useCache;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private FileSystemFeedHelper fileSystemFeedHelper;

    @Autowired
    private Utils utils;

    @Override
    public Set<Brand> getBrandsSet() {
        Set<Brand> brandsSet = new HashSet<>();
        List<String> brands = getBrandsList(brandsStringCommon);
        if(brands != null && !brands.isEmpty()) {
            for(String brandName: brands) {
                Brand brandObject = consumerService.getBrandByName(brandName, useCache);
                if(brandObject != null) {
                    brandsSet.add(brandObject);
                }
            }
        }
        return brandsSet;
    }

    @Override
    public Map<Brand, Set<Model>> getModelsByBrands(Set<Brand> brands) {
        Map<Brand, Set<Model>> modelsByBrands = new HashMap<>();
        if(brands != null && !brands.isEmpty()) {
            for(Brand brand: brands) {
                Set<Model> models = consumerService.getAllModelsByBrand(brand, false);
                modelsByBrands.put(brand, models);
            }
        }
        return modelsByBrands;
    }

    @Override
    public boolean isBrandModelExists(Brand brand, Model model) {
        String path = brand.getBrandName().concat("/").concat(model.getModelName()).concat("/");
        return fileSystemFeedHelper.isAlreadyExists(path);
    }

    @Override
    public int getIterationsCountPerBlock() {
        return 2;
    }

    @Override
    public long getSleepTimePerBlock() {
        return 60000;
    }

    @Override
    public Specifications getSpecifications(Brand brand, Model model) {
        return consumerService.getSpecificationsByModel(model, useCache);
    }

    @Override
    public void saveSpecifications(Brand brand, Model model, Specifications specifications) {
        try {
            String path = brand.getBrandName().concat("/").concat(model.getModelName()).concat("/");
            String data = fileSystemFeedHelper.convertToJson(specifications);
            fileSystemFeedHelper.save(data, "Specifications.json", path);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Set<Property> getDetailSpecifications(Brand brand, Model model) {
        return consumerService.getDetailSpecificationsByModel(model, useCache);
    }

    @Override
    public void saveDetailSpecifications(Brand brand, Model model, Set<Property> properties) {
        try {
            String path = brand.getBrandName().concat("/").concat(model.getModelName()).concat("/");
            String data = fileSystemFeedHelper.convertToJson(properties);
            fileSystemFeedHelper.save(data, "DetailSpecifications.json", path);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private List<String> getBrandsList(String brandsString) {
        String[] brands = brandsString.split(",");
        return utils.preprocessStringArray(brands);
    }

}
