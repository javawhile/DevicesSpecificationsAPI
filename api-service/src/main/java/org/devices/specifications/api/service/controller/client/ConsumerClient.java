package org.devices.specifications.api.service.controller.client;

import org.devices.specifications.api.common.model.Property;
import org.devices.specifications.api.common.model.Specifications;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public interface ConsumerClient {

    @GetMapping("/brands")
    ResponseEntity<Set<String>> getBrands();

    @GetMapping("/models/{brandName}")
    ResponseEntity<Set<String>> getAllModelsByBrandName(@PathVariable("brandName") String brandName);

    @GetMapping("/specifications/detail/{brandName}/{modelName}")
    ResponseEntity<Set<Property>> getDetailSpecificationsByBrandModel(@PathVariable("brandName") String brandName, @PathVariable("modelName") String modelName);

    @GetMapping("/specifications/{brandName}/{modelName}")
    ResponseEntity<Specifications> getSpecificationsByBrandModel(@PathVariable("brandName") String brandName, @PathVariable("modelName") String modelName);

}
