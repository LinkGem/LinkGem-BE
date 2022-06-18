package com.linkgem.presentation;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class HealthCheckController {

    private final Map<String, String> applicationInfo =
        Collections.singletonMap("message", "LinkGem backend API application");

    @GetMapping
    public Map<String, String> health() {
        return applicationInfo;
    }
}
