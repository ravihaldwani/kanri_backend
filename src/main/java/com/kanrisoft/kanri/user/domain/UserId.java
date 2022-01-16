package com.kanrisoft.kanri.user.domain;

import lombok.Data;

@Data(staticConstructor = "of")
public class UserId {
    final Long id;
}
