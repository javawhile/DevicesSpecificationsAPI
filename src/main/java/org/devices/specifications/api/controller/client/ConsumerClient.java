package org.devices.specifications.api.controller.client;

import org.devices.specifications.api.model.Specifications;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public interface ConsumerClient {

    @GetMapping("/brands/all")
    ResponseEntity<Set<String>> getAllBrands();

    @GetMapping("/models/{brandName}")
    ResponseEntity<Set<String>> getAllModelsByBrandName(@PathVariable("brandName") String brandName);

    @GetMapping("/specifications/{brandName}/{modelName}")
    ResponseEntity<Specifications> getSpecificationsByBrandModel(@PathVariable("brandName") String brandName, @PathVariable("modelName") String modelName);

    @GetMapping("/brands/all/nocache")
    ResponseEntity<Set<String>> getAllBrandsNoCache();

    @GetMapping("/models/{brandName}/nocache")
    ResponseEntity<Set<String>> getAllModelsByBrandNameNoCache(@PathVariable("brandName") String brandName);

    @GetMapping("/specifications/{brandName}/{modelName}/nocache")
    ResponseEntity<Specifications> getSpecificationsByBrandModelNoCache(@PathVariable("brandName") String brandName, @PathVariable("modelName") String modelName);
}
