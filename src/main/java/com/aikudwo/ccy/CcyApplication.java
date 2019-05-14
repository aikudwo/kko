package com.aikudwo.ccy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan //添加过滤器扫描
@SpringBootApplication
public class CcyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CcyApplication.class, args);
	}

}
