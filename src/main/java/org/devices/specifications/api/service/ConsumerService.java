package org.devices.specifications.api.service;

import org.devices.specifications.api.model.Brand;
import org.devices.specifications.api.model.Specifications;
import org.devices.specifications.api.model.Model;

import java.util.Set;

public interface ConsumerService {
    Set<Brand> getAllBrands(boolean useCache);
    Brand getBrandByName(String brandName, boolean useCache);
    Set<Model> getAllModelsByBrandName(String brandName);
    Set<Model> getAllModelsByBrandUrl(String brandUrl, boolean useCache);
    Model getModelByName(String brandUrl, String modelName, boolean useCache);
    Specifications getSpecificationsByUrl(String url, boolean useCache);
    Specifications getSpecificationsByBrandModel(String brandName, String modelName);
}
