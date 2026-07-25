package com.secure.notes.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String helloApi() {
        return "Hello";
    }

    @GetMapping("/hi")
    public String hiApi() {
        return "Hi";
    }

    @GetMapping("/contact")
    public String contactApi() {
        return "Contact";
    }
}
