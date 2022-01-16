package com.kanrisoft.kanri.user.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class UserId {
    Long id;
}
