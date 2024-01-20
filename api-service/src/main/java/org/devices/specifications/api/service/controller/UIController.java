package org.devices.specifications.api.service.controller;

import org.devices.specifications.api.common.utils.Utils;
import org.devices.specifications.api.service.services.ConsumerService;
import org.devices.specifications.api.service.services.UIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@Controller
public class UIController {

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private UIService uiService;

    @Autowired
    private Utils utils;

    @GetMapping("/")
    public String homepage(
            @RequestParam(value = "brandName", required = false) String brandName,
            @RequestParam(value = "modelName", required = false) String modelName,
            Model model
    ) {
        model.addAttribute("brands", consumerService.getSupportedBrands());
        boolean isValidBrand = isValidString(brandName);
        boolean isValidModel = isValidString(modelName);
        if(isValidBrand) {
            Set<String> modelNames = utils.getReadableValuesFromSet(consumerService.getAllModelsByBrandName(brandName), _model -> _model.getModelName());
            model.addAttribute("brandSelected", brandName);
            model.addAttribute("models", modelNames);
        }
        if(isValidModel) {
            model.addAttribute("modelSelected", modelName);
        }
        if(isValidBrand && isValidModel) {
            model.addAttribute("itemDetails", uiService.getDetailSpecifications(brandName, modelName));
        }
        return "index";
    }

    private boolean isValidString(String string) {
        return string != null && !string.trim().isEmpty();
    }

}
