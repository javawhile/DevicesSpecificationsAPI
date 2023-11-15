package org.devices.specifications.api.service.services;

import org.devices.specifications.api.common.model.Brand;
import org.devices.specifications.api.common.model.Property;
import org.devices.specifications.api.common.model.Specifications;
import org.devices.specifications.api.common.model.Model;

import java.util.Set;

public interface CacheService {

    Integer resetAllCache();
    Integer resetBrandsCache();
    Integer resetModelsCache();
    Integer resetSpecificationsCache();
    Integer resetDetailSpecificationsCache();
    void saveBrand(String brandName, Brand brand);
    void saveBrands(Set<Brand> brands);
    void saveModels(String brandUrl, Set<Model> models);
    void saveSpecifications(String modelUrl, Specifications specifications);
    void saveDetailSpecifications(String modelUrl, Set<Property> properties);
    Brand getBrand(String brandName);
    Set<Brand> getAllBrands();
    Set<Model> getModels(String brandUrl);
    Specifications getSpecification(String modelUrl);
    Set<Property> getDetailSpecification(String modelUrl);
}
