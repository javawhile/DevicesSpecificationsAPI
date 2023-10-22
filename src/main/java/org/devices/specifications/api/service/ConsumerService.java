package org.devices.specifications.api.service;

import org.devices.specifications.api.model.Brand;
import org.devices.specifications.api.model.Property;
import org.devices.specifications.api.model.Specifications;
import org.devices.specifications.api.model.Model;

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
