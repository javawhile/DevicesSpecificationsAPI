package org.devices.specifications.api.controller;

import org.devices.specifications.api.model.Brand;
import org.devices.specifications.api.model.Model;
import org.devices.specifications.api.model.Specifications;
import org.devices.specifications.api.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ConsumerController {

    @Autowired
    private ConsumerService consumerService;

    @GetMapping("/brands/all")
    public ResponseEntity<Set<String>> getAllBrands() {
        Set<Brand> allBrands = consumerService.getAllBrands(true);
        Set<String> allBrandNames = new HashSet<>();
        if(allBrands != null && !allBrands.isEmpty()) {
            allBrandNames = allBrands.stream()
                    .filter(Objects::nonNull)
                    .map(Brand::getBrandName)
                    .collect(Collectors.toSet());
        } else {
            allBrandNames.add("not found");
        }
        return getResponseEntity(allBrandNames);
    }

    @GetMapping("/models/{brandName}")
    public ResponseEntity<Set<String>> getAllModelsByBrandName(@PathVariable("brandName") String brandName) {
        Set<Model> allModels = consumerService.getAllModelsByBrandName(brandName);
        Set<String> allModelNames = new HashSet<>();
        if(allModels != null && !allModels.isEmpty()) {
            allModelNames = allModels.stream()
                    .filter(Objects::nonNull)
                    .map(Model::getModelName)
                    .collect(Collectors.toSet());
        } else {
            allModelNames.add("not found");
        }
        return getResponseEntity(allModelNames);
    }

    @GetMapping("/specifications/{brandName}/{modelName}")
    public ResponseEntity<Specifications> getSpecificationsByBrandModel(@PathVariable("brandName") String brandName, @PathVariable("modelName") String modelName) {
        Specifications specifications = consumerService.getSpecificationsByBrandModel(brandName, modelName);
        return getResponseEntity(specifications);
    }

    private <T> ResponseEntity<T> getResponseEntity(T data) {
        return new ResponseEntity<T>(data, HttpStatus.OK);

    }

}
