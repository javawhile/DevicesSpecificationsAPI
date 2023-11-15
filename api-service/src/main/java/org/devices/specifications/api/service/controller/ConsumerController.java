package org.devices.specifications.api.service.controller;

import org.devices.specifications.api.service.controller.client.ConsumerClient;
import org.devices.specifications.api.common.model.Brand;
import org.devices.specifications.api.common.model.Model;
import org.devices.specifications.api.common.model.Property;
import org.devices.specifications.api.common.model.Specifications;
import org.devices.specifications.api.service.services.ConsumerService;
import org.devices.specifications.api.common.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class ConsumerController implements ConsumerClient {

    @Value("${enable.cache}")
    private boolean useCache;

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private Utils utils;

    @Override
    public ResponseEntity<Set<String>> getAllBrands() {
        Set<Brand> allBrands = consumerService.getAllBrands(useCache);
        Set<String> allReadableFormat = new HashSet<>();
        if(allBrands != null && !allBrands.isEmpty()) {
            allReadableFormat.addAll(utils.getReadableValuesFromSet(allBrands, Brand::getBrandName));
        } else {
            allReadableFormat.add("not found");
        }
        return getResponseEntity(allReadableFormat);
    }

    @Override
    public ResponseEntity<Set<String>> getAllBrandsNoCache() {
        Set<Brand> allBrands = consumerService.getAllBrands(false);
        Set<String> allReadableFormat = new HashSet<>();
        if(allBrands != null && !allBrands.isEmpty()) {
            allReadableFormat.addAll(utils.getReadableValuesFromSet(allBrands, Brand::getBrandName));
        } else {
            allReadableFormat.add("not found");
        }
        return getResponseEntity(allReadableFormat);
    }

    @Override
    public ResponseEntity<Set<String>> getAllModelsByBrandName(String brandName) {
        Set<Model> allModels = consumerService.getAllModelsByBrandName(brandName, useCache);
        Set<String> allReadableFormat = new HashSet<>();
        if(allModels != null && !allModels.isEmpty()) {
            allReadableFormat.addAll(utils.getReadableValuesFromSet(allModels, Model::getModelName));
        } else {
            allReadableFormat.add("not found");
        }
        return getResponseEntity(allReadableFormat);
    }

    @Override
    public ResponseEntity<Set<Property>> getDetailSpecificationsByBrandModel(String brandName, String modelName) {
        Set<Property> properties = consumerService.getDetailSpecificationsByBrandModel(brandName, modelName, useCache);
        return getResponseEntity(properties);
    }

    @Override
    public ResponseEntity<Set<String>> getAllModelsByBrandNameNoCache(String brandName) {
        Set<Model> allModels = consumerService.getAllModelsByBrandName(brandName, false);
        Set<String> allReadableFormat = new HashSet<>();
        if(allModels != null && !allModels.isEmpty()) {
            allReadableFormat.addAll(utils.getReadableValuesFromSet(allModels, Model::getModelName));
        } else {
            allReadableFormat.add("not found");
        }
        return getResponseEntity(allReadableFormat);
    }

    @Override
    public ResponseEntity<Specifications> getSpecificationsByBrandModel(String brandName, String modelName) {
        Specifications specifications = consumerService.getSpecificationsByBrandModel(brandName, modelName, useCache);
        return getResponseEntity(specifications);
    }

    @Override
    public ResponseEntity<Specifications> getSpecificationsByBrandModelNoCache(String brandName, String modelName) {
        Specifications specifications = consumerService.getSpecificationsByBrandModel(brandName, modelName, false);
        return getResponseEntity(specifications);
    }

    @Override
    public ResponseEntity<Set<Property>> getDetailSpecificationsByBrandModelNoCache(String brandName, String modelName) {
        Set<Property> properties = consumerService.getDetailSpecificationsByBrandModel(brandName, modelName, false);
        return getResponseEntity(properties);
    }

    private <T> ResponseEntity<T> getResponseEntity(T data) {
        return new ResponseEntity<T>(data, HttpStatus.OK);
    }

}
