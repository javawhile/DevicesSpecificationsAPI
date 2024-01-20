package org.devices.specifications.api.service.services;

import org.devices.specifications.api.common.pojo.BrandModelSpecifications;

import java.util.List;

public interface UIService {
    List<BrandModelSpecifications> getDetailSpecifications(String brandName, String modelName);
}
