package com.kanrisoft.kanri.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role {
    public final static Role USER = new Role("USER");
    public final static Role ADMIN = new Role("ADMIN");

    private String name;

    Role(String name) {
        this.name = name;
    }

}
