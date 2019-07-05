package com.aikudwo.ccy.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author wls
 * @date 2019-06-18 15:25
 */
@Component
public class DemoPublisher {
    @Autowired
    ApplicationContext applicationContext;

    public void publish(String message){
        applicationContext.publishEvent(new DemoEvent(this,message));
    }
}
