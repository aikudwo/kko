package com.aikudwo.ccy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wls
 * @date 2019-06-03 16:45
 */
@RestController
public class HelloController {
    @GetMapping("hello")
    public String hello(){
        return "hello word";
    }
}
