package org.devices.specifications.api.controller.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface ApplicationClient {
    @GetMapping("/status")
    ResponseEntity<String> status();
}
