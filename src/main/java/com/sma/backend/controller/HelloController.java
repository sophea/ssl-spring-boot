package com.sma.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is test controller.
 *
 * @author Sophea Mak sopheamak@gmail.com
 * @since March-2020
 */
@RestController
public class HelloController {

    @GetMapping("/api/test/data")
    public String getData() {
        System.out.println("Returning data method");
        return "Hello from data method";
    }
}
