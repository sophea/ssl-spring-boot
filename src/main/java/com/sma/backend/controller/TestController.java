package com.sma.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/***
 * @Author Sophea Mak sopheamak@gmail.com
 * @Since March-2020
 */
@RestController
@RequestMapping(value = "/api")
public class TestController {
    @RequestMapping(value = "/test/data", method = RequestMethod.GET)
    public String getData() {
        System.out.println("Returning data method");
        return "Hello from data method";
    }
}