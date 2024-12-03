package org.kevin.garrett.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api") // Base URL for all endpoints in this controller
public class TestController {

    @GetMapping("/test") // Maps HTTP GET requests to this method
    public String testEndpoint() {
        return "Hello, World!";
    }
}
