package com.kanrisoft.kanri.user.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class UserId {
    Long id;
}
