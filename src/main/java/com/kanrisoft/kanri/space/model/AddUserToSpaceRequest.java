package com.kanrisoft.kanri.space.model;

import java.util.Objects;

public record AddUserToSpaceRequest(Long userId) {
    public AddUserToSpaceRequest {
        Objects.requireNonNull(userId);
    }
}
