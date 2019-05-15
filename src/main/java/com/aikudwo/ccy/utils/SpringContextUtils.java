package com.aikudwo.ccy.utils;

import org.apache.catalina.core.ApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;

public class SpringContextUtils implements ApplicationContextAware {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static ApplicationContext applicationContext;

    @Override
    synchronized public void setApplicationContext(@NonNull org.springframework.context.ApplicationContext applicationContext) throws BeansException {
        if (SpringContextUtils.applicationContext == null) {
            SpringContextUtils.applicationContext = (ApplicationContext) applicationContext;
        }
        logger.info("setApplicationContext Success!");
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
