package com.kanrisoft.kanri.user.model;

import lombok.Value;

import java.io.Serializable;

@Value(staticConstructor = "of")
public class UserId implements Serializable {
    Long id;
}
