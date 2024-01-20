package org.devices.specifications.api.service.controller;

import org.devices.specifications.api.service.controller.client.ConsumerClient;
import org.devices.specifications.api.common.model.Model;
import org.devices.specifications.api.common.model.Property;
import org.devices.specifications.api.common.model.Specifications;
import org.devices.specifications.api.service.services.ConsumerService;
import org.devices.specifications.api.common.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class ConsumerController implements ConsumerClient {

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private Utils utils;

    @Override
    public ResponseEntity<Set<String>> getBrands() {
        return getResponseEntity(consumerService.getSupportedBrands());
    }

    @Override
    public ResponseEntity<Set<String>> getAllModelsByBrandName(String brandName) {
        Set<Model> allModels = consumerService.getAllModelsByBrandName(brandName);
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
        Set<Property> properties = consumerService.getDetailSpecificationsByBrandModel(brandName, modelName);
        return getResponseEntity(properties);
    }

    @Override
    public ResponseEntity<Specifications> getSpecificationsByBrandModel(String brandName, String modelName) {
        Specifications specifications = consumerService.getSpecificationsByBrandModel(brandName, modelName);
        return getResponseEntity(specifications);
    }

    private <T> ResponseEntity<T> getResponseEntity(T data) {
        return new ResponseEntity<T>(data, HttpStatus.OK);
    }

}
