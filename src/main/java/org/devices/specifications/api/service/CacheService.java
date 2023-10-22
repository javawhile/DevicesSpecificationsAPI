package org.devices.specifications.api.service;

import org.devices.specifications.api.model.Brand;
import org.devices.specifications.api.model.Property;
import org.devices.specifications.api.model.Specifications;
import org.devices.specifications.api.model.Model;

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
