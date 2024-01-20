package org.devices.specifications.api.service.services;

import org.devices.specifications.api.common.model.Brand;
import org.devices.specifications.api.common.model.Model;
import org.devices.specifications.api.common.model.Property;
import org.devices.specifications.api.common.model.Specifications;

import java.util.Set;

public interface CacheService {
    Set<Model> getModels(final Brand brand);
    void cacheModels(final Brand brand, final Set<Model> models);

    Specifications getSpecifications(final Model model);
    void cacheSpecifications(final Model model, final Specifications specifications);

    Set<Property> getDetailSpecifications(final Model model);
    void cacheDetailSpecifications(final Model model, final Set<Property> properties);
}
