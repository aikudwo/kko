package com.aikudwo.ccy.ioc.entity;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wls
 * @date 2019-06-03 15:31
 */
public class Person {
    private Long id;
    private String name;

    @Autowired
    private Pet pet;

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void call(){
        pet.move();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
