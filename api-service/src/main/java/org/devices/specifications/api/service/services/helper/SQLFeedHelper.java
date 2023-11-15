package org.devices.specifications.api.service.services.helper;

import org.devices.specifications.api.common.model.Brand;
import org.devices.specifications.api.common.model.Model;
import org.devices.specifications.api.common.model.Property;
import org.devices.specifications.api.common.model.Specifications;
import org.devices.specifications.api.common.model.sql.BrandModelMap;
import org.devices.specifications.api.common.model.sql.BriefSpecification;
import org.devices.specifications.api.common.model.sql.DetailSpecification;
import org.devices.specifications.api.common.model.sql.UrlHtml;
import org.devices.specifications.api.service.repository.BrandModelMapRepository;
import org.devices.specifications.api.service.repository.BriefSpecificationRepository;
import org.devices.specifications.api.service.repository.DetailSpecificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class SQLFeedHelper {

    @Autowired(required = false)
    private BrandModelMapRepository brandModelMapRepository;

    @Autowired(required = false)
    private BriefSpecificationRepository briefSpecificationRepository;

    @Autowired(required = false)
    private DetailSpecificationRepository detailSpecificationRepository;

    public int saveOrUpdateBrandModel(Brand brand, Model model) {
        BrandModelMap brandModelMap = getBrandModelMap(brand, model);

        int alreadySavedId = getMapIdByBrandModelMap(brandModelMap);
        if(alreadySavedId == -1) {
            BrandModelMap brandModelMapSaved = brandModelMapRepository.save(brandModelMap);
            return brandModelMapSaved.getMapId();
        }
        return alreadySavedId;
    }

    public int saveOrUpdateBriefSpecifications(int mapId, Specifications specifications) {
        BriefSpecification briefSpecification = getBriefSpecifications(specifications);
        briefSpecification.setMapId(mapId);

        int alreadySavedId = getSpecificationIdByBriefSpecification(briefSpecification);
        if(alreadySavedId == -1) {
            BriefSpecification briefSpecificationSaved = briefSpecificationRepository.save(briefSpecification);
            return briefSpecificationSaved.getSpecificationId();
        }
        return alreadySavedId;
    }

    public int saveOrUpdateDetailSpecifications(int mapId, Set<Property> properties) {
        int countSaved = 0;
        for (Property property : properties) {
            DetailSpecification detailSpecification = getDetailSpecification(property);
            detailSpecification.setMapId(mapId);

            int alreadySavedId = getSpecificationIdByDetailSpecification(detailSpecification);
            if(alreadySavedId == -1) {
                detailSpecificationRepository.save(detailSpecification);
                countSaved++;
            }
        }
        return countSaved;
    }

    public int getMapIdByBrandModelMap(BrandModelMap brandModelMap) {
        Example<BrandModelMap> example = Example.of(brandModelMap, ExampleMatcher.matchingAll().withIgnoreCase());
        Optional<BrandModelMap> brandModelMapOptional = brandModelMapRepository.findOne(example);
        if(brandModelMapOptional.isPresent()) {
            return brandModelMapOptional.get().getMapId();
        }
        return -1;
    }

    public int getSpecificationIdByBriefSpecification(BriefSpecification briefSpecification) {
        Example<BriefSpecification> example = Example.of(briefSpecification, ExampleMatcher.matchingAll().withIgnoreCase());
        Optional<BriefSpecification> briefSpecificationOptional = briefSpecificationRepository.findOne(example);
        if(briefSpecificationOptional.isPresent()) {
            return briefSpecificationOptional.get().getSpecificationId();
        }
        return -1;
    }

    public int getSpecificationIdByDetailSpecification(DetailSpecification detailSpecification) {
        Example<DetailSpecification> example = Example.of(detailSpecification, ExampleMatcher.matchingAll().withIgnoreCase());
        Optional<DetailSpecification> detailSpecificationOptional = detailSpecificationRepository.findOne(example);
        if(detailSpecificationOptional.isPresent()) {
            return detailSpecificationOptional.get().getSpecificationId();
        }
        return -1;
    }

    public int findMapIdByBrandModel(Brand brand, Model model) {
        List<BrandModelMap> brandModelMapAll = brandModelMapRepository.findAll();
        for(BrandModelMap brandModelMap: brandModelMapAll) {
            if(brandModelMap.getBrandName().equalsIgnoreCase(brand.getBrandName())
            && brandModelMap.getModelName().equalsIgnoreCase(model.getModelName())) {
                return brandModelMap.getMapId();
            }
        }
        return -1;
    }

    public boolean isBrandModelExists(Brand brand, Model model) {
        return getMapIdByBrandModelMap(getBrandModelMap(brand, model)) != -1;
    }

    private BrandModelMap getBrandModelMap(Brand brand, Model model) {
        BrandModelMap brandModelMap = new BrandModelMap();
        brandModelMap.setBrandName(brand.getBrandName());
        brandModelMap.setBrandUrl(brand.getBrandUrl());
        brandModelMap.setModelName(model.getModelName());
        brandModelMap.setModelUrl(model.getModelUrl());
        return brandModelMap;
    }

    private BriefSpecification getBriefSpecifications(Specifications specifications) {
        BriefSpecification briefSpecification = new BriefSpecification();
        briefSpecification.setDeviceName(specifications.getDeviceName());
        briefSpecification.setDimensions(specifications.getDimensions());
        briefSpecification.setWeight(specifications.getWeight());
        briefSpecification.setSoc(specifications.getSoc());
        briefSpecification.setCpu(specifications.getCpu());
        briefSpecification.setGpu(specifications.getGpu());
        briefSpecification.setRam(specifications.getRam());
        briefSpecification.setStorage(specifications.getStorage());
        briefSpecification.setMemoryCards(specifications.getMemoryCards());
        briefSpecification.setDisplay(specifications.getDisplay());
        briefSpecification.setBattery(specifications.getBattery());
        briefSpecification.setOs(specifications.getOs());
        briefSpecification.setCamera(specifications.getCamera());
        briefSpecification.setSimCards(specifications.getSimCards());
        briefSpecification.setWifi(specifications.getWifi());
        briefSpecification.setUsb(specifications.getUsb());
        briefSpecification.setBluetooth(specifications.getBluetooth());
        briefSpecification.setPositioning(specifications.getPositioning());
        return briefSpecification;
    }

    private DetailSpecification getDetailSpecification(Property property) {
        DetailSpecification detailSpecification = new DetailSpecification();
        detailSpecification.setCategory(property.getCategory());
        detailSpecification.setCategoryDescription(property.getCategoryDescription());
        detailSpecification.setPropertyName(property.getPropertyName());
        detailSpecification.setPropertyDescription(property.getPropertyDescription());
        detailSpecification.setValues(Arrays.toString(property.getValues().toArray(new String[0])));
        return detailSpecification;
    }
}
