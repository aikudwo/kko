package com.aikudwo.ccy.controller;


import com.aikudwo.ccy.customize.configuration.CacheConfig;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author wls
 * @date 2019-05-14 19:33
 * 测试过滤器方法
 */
@RestController
@RequestMapping("/test")
public class TestFilterController {

    @Cacheable(value = CacheConfig.CacheNames.CACHE_TEST_CACHE_NAME)
    @PostMapping("testFilter")
    public String TestFilter1(){
        System.out.println("666666666666");
        String s = "66666666666666";
        return s;
    }
}
