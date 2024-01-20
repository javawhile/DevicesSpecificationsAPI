package org.devices.specifications.api.service.services.impl;

import org.devices.specifications.api.common.model.Property;
import org.devices.specifications.api.common.pojo.BrandModelSpecifications;
import org.devices.specifications.api.common.utils.Utils;
import org.devices.specifications.api.service.services.ConsumerService;
import org.devices.specifications.api.service.services.UIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class UIServiceImpl implements UIService {

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private Utils utils;

    @Override
    public List<BrandModelSpecifications> getDetailSpecifications(String brandName, String modelName) {
        List<BrandModelSpecifications> brandModelSpecifications = new ArrayList<>();
        Set<Property> detailSpecificationsByBrandModel = consumerService.getDetailSpecificationsByBrandModel(brandName, modelName);
        for(Property property: detailSpecificationsByBrandModel) {
            if(property != null) {
                BrandModelSpecifications brandModelSpecification = new BrandModelSpecifications();
                brandModelSpecification.setProperty(property.getPropertyName());
                brandModelSpecification.setCategory(property.getCategory());
                brandModelSpecification.setValues(property.getValues());
                brandModelSpecifications.add(brandModelSpecification);
            }
        }
        brandModelSpecifications.sort(Comparator.comparing(BrandModelSpecifications::getCategory));
        return brandModelSpecifications;
    }
}
