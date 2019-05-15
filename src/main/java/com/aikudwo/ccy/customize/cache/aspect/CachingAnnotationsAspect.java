package com.aikudwo.ccy.customize.cache.aspect;

import com.aikudwo.ccy.customize.cache.CacheSupport;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.aikudwo.ccy.utils.AspectUtils.getMethodAnnotations;
import static com.aikudwo.ccy.utils.AspectUtils.getSpecification;


@Aspect
@Component
public class CachingAnnotationsAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CacheSupport cacheSupport;

    @Autowired(required = false)
    public CachingAnnotationsAspect(CacheSupport cacheSupport) {
        this.cacheSupport = cacheSupport;
    }


    @Pointcut(value = "@annotation(org.springframework.cache.annotation.Cacheable)")
    public void pointcut(){}

    @Around(value = "pointcut()")
    public Object registerInvocation(ProceedingJoinPoint joinPoint) throws Throwable{

        Method method = getSpecification(joinPoint);

        List<Cacheable> annotations=getMethodAnnotations(method,Cacheable.class);

        Set<String> cacheSet = new HashSet<>();
        assert annotations != null;
        for (Cacheable cacheables : annotations) {
            cacheSet.addAll(Arrays.asList(cacheables.value()));
        }
        cacheSupport.registerInvocation(joinPoint.getTarget(), method, joinPoint.getArgs(), cacheSet);
        return joinPoint.proceed();

    }

}
