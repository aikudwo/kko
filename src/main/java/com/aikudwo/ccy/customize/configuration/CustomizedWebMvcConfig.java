package com.aikudwo.ccy.customize.configuration;

import com.aikudwo.ccy.customize.converter.StringToDateConverter;
import com.aikudwo.ccy.customize.resolver.JsonHandlerMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CustomizedWebMvcConfig implements WebMvcConfigurer {

    private final JsonHandlerMethodArgumentResolver jsonHandlerMethodArgumentResolver;

    @Autowired
    public CustomizedWebMvcConfig(JsonHandlerMethodArgumentResolver jsonHandlerMethodArgumentResolver) {
        super();
        this.jsonHandlerMethodArgumentResolver = jsonHandlerMethodArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(jsonHandlerMethodArgumentResolver);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToDateConverter());
    }

}



