package com.aikudwo.ccy.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * @author wls
 * @date 2019-06-03 16:52
 */
@Aspect
@Component
public class RequsetLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(RequsetLogAspect.class);

    //定义切点
    @Pointcut(value = "@within(org.springframework.stereotype.Controller)||@within(org.springframework.web.bind.annotation.RestController)")
    public void pointCut(){

    }

    //在切点之前执行方法
    @Before("pointCut()")
    public void doBefore(JoinPoint joinPoint){
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        Assert.state(attrs instanceof ServletRequestAttributes, "No current ServletRequestAttributes");
        HttpServletRequest request = ((ServletRequestAttributes) attrs).getRequest();
        logger.warn("IntranetFilter - url :" + request.getRequestURI() );
    }
}
