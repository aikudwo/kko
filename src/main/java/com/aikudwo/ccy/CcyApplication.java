package com.aikudwo.ccy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRetry
@EnableAsync
@EnableScheduling
@ServletComponentScan //添加过滤器扫描
@SpringBootApplication
public class CcyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CcyApplication.class, args);
	}

}
