package org.devices.specifications.api.service.controller.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/application")
public interface ApplicationClient {
    @GetMapping("/status")
    ResponseEntity<String> status();

    @PostMapping("/selfUrl")
    ResponseEntity<String> initSelfUrl(final @RequestBody String selfUrl, final @RequestHeader String password);
}
