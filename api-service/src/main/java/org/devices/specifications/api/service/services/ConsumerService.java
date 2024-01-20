package org.devices.specifications.api.service.services;

import org.devices.specifications.api.common.model.Brand;
import org.devices.specifications.api.common.model.Property;
import org.devices.specifications.api.common.model.Specifications;
import org.devices.specifications.api.common.model.Model;

import java.util.List;
import java.util.Set;

public interface ConsumerService {
    boolean isBrandSupported(final String brandName);
    Set<String> getSupportedBrands();
    Brand getBrandByName(String brandName);

    Set<Model> getAllModelsByBrandName(String brandName);

    Specifications getSpecificationsByModel(Model model);
    Set<Property> getDetailSpecificationsByModel(Model model);

    Specifications getSpecificationsByBrandModel(String brandName, String modelName);
    Set<Property> getDetailSpecificationsByBrandModel(String brandName, String modelName);
}
