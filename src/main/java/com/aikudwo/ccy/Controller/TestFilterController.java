package com.aikudwo.ccy.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wls
 * @date 2019-05-14 19:33
 * 测试过滤器方法
 */
@RestController
@RequestMapping("/test")
public class TestFilterController {

    @PostMapping("testFilter")
    public void TestFilter1(){
        System.out.println("666666666666");
    }
}
