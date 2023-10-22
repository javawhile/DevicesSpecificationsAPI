package org.devices.specifications.api.utils;

import org.devices.specifications.api.model.Brand;
import org.devices.specifications.api.model.Model;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class Utils {

    public String removeTags(String string, String...tags) {
        if(string != null && !string.trim().isEmpty() && tags != null && tags.length >= 1) {
            for(String tag: tags) {
                string = string.replaceAll(tag, "");
            }
        }
        return string != null ? string.trim() : null;
    }

    public List<String> preprocessStringArray(String[] stringArray) {
        List<String> list = new ArrayList<>();
        if(stringArray != null && stringArray.length >= 1) {
            for (String line: stringArray) {
                if(line != null && !line.trim().isEmpty()) {
                    list.add(line.trim());
                }
            }
        }
        return list;
    }

    public Brand searchBrand(Set<Brand> allBrands, String brandName) {
        if(allBrands != null && !allBrands.isEmpty()) {
            for(Brand brand: allBrands) {
                if(brand != null) {
                    String brandNameFromList = brand.getBrandName().trim().toLowerCase();
                    String brandNameToSearch = brandName.trim().toLowerCase();
                    if(!brandNameFromList.trim().isEmpty() && !brandNameToSearch.trim().isEmpty()) {
                        if(getSearchFunctionForBrand().test(brandNameFromList, brandNameToSearch)) {
                            return brand;
                        }
                    }
                }
            }
        }
        return null;
    }

    public Model searchModel(Set<Model> allModels, String modelName) {
        if(allModels != null && !allModels.isEmpty()) {
            for(Model model: allModels) {
                if(model != null) {
                    String modelNameFromList = model.getModelName().trim().toLowerCase();
                    String modelNameToSearch = modelName.trim().toLowerCase();
                    if(!modelNameFromList.trim().isEmpty() && !modelNameToSearch.trim().isEmpty()) {
                        if(getSearchFunctionForModel().test(modelNameFromList, modelNameToSearch)) {
                            return model;
                        }
                    }
                }
            }
        }
        return null;
    }

    public BiPredicate<String, String> getSearchFunctionForBrand() {
        return (nameFromList, nameToSearch) -> {
            if(nameFromList != null && nameToSearch != null
                    && !nameFromList.trim().isEmpty() && !nameToSearch.trim().isEmpty()) {

                nameFromList = removeTags(nameFromList.trim(), " ").trim();
                nameToSearch = removeTags(nameToSearch.trim(), " ").trim();

                return nameFromList.equalsIgnoreCase(nameToSearch);
            }

            return false;
        };
    }

    public BiPredicate<String, String> getSearchFunctionForModel() {
        return (nameFromList, nameToSearch) -> {
            if(nameFromList != null && nameToSearch != null
                    && !nameFromList.trim().isEmpty() && !nameToSearch.trim().isEmpty()) {

                nameFromList = removeTags(nameFromList.trim(), " ").trim();
                nameToSearch = removeTags(nameToSearch.trim(), " ").trim();

                return nameFromList.equalsIgnoreCase(nameToSearch);
            }

            return false;
        };
    }

    public <T> Set<String> getReadableValuesFromSet(Set<T> set, Function<T, String> conversionFunction) {
        Set<String> readableValues = new HashSet<>();
        if(set != null && conversionFunction != null) {
            readableValues.addAll(set.stream()
                    .filter(Objects::nonNull)
                    .map(conversionFunction)
                    .collect(Collectors.toList()));
        }
        return readableValues;
    }

}

