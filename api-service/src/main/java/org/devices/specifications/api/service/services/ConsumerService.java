package org.devices.specifications.api.service.services;

import org.devices.specifications.api.common.model.Brand;
import org.devices.specifications.api.common.model.Property;
import org.devices.specifications.api.common.model.Specifications;
import org.devices.specifications.api.common.model.Model;

import java.util.Set;

public interface ConsumerService {

    Set<Brand> getAllBrands(boolean useCache);
    Brand getBrandByName(String brandName, boolean useCache);

    Set<Model> getAllModelsByBrand(Brand brand, boolean useCache);
    Set<Model> getAllModelsByBrandName(String brandName, boolean useCache);

    Specifications getSpecificationsByModel(Model model, boolean useCache);
    Set<Property> getDetailSpecificationsByModel(Model model, boolean useCache);

    Specifications getSpecificationsByBrandModel(String brandName, String modelName, boolean useCache);
    Set<Property> getDetailSpecificationsByBrandModel(String brandName, String modelName, boolean useCache);
}
