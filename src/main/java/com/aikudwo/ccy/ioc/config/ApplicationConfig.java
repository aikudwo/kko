package com.aikudwo.ccy.ioc.config;

import com.aikudwo.ccy.ioc.entity.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wls
 * @date 2019-06-03 15:32
 */
@Configuration
public class ApplicationConfig {
    @Bean(name ="Person")
    public Person initPerson(){
        Person person = new Person();
        person.setId(2L);
        person.setName("aikudwo");
        return person;
    }
}
