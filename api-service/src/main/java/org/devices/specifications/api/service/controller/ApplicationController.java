package org.devices.specifications.api.service.controller;

import org.devices.specifications.api.service.controller.client.ApplicationClient;
import org.devices.specifications.api.service.services.impl.ApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController implements ApplicationClient {

    @Autowired
    private ApplicationServiceImpl applicationServiceImpl;

    @Override
    public ResponseEntity<String> status() {
        return getResponseEntity("alive");
    }

    @Override
    public ResponseEntity<String> initSelfUrl(final String selfUrl, final String password) {
        if (applicationServiceImpl.isPasswordCorrect(password)) {
            if(selfUrl != null && !selfUrl.trim().isEmpty()) {
                applicationServiceImpl.setSelfUrl(selfUrl);
                return getResponseEntity("selfUrl initialized");
            } else {
                return getResponseEntity("Invalid selfUrl");
            }
        }
        return getResponseEntity("Invalid Password");
    }

    private <T> ResponseEntity<T> getResponseEntity(T data) {
        return new ResponseEntity<T>(data, HttpStatus.OK);
    }
}
