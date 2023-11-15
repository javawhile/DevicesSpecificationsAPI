package org.devices.specifications.api.service.controller;

import org.devices.specifications.api.service.controller.client.ApplicationClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController implements ApplicationClient {

    @Override
    public ResponseEntity<String> status() {
        return getResponseEntity("alive");
    }

    private <T> ResponseEntity<T> getResponseEntity(T data) {
        return new ResponseEntity<T>(data, HttpStatus.OK);

    }
}
