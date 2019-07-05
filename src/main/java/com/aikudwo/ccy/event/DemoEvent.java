package com.aikudwo.ccy.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wls
 * @date 2019-06-18 15:22
 */
public class DemoEvent extends ApplicationEvent {

    private static final long serialVersionUID = 767352958358520268L;

    private String message;

    public DemoEvent(Object source,String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
