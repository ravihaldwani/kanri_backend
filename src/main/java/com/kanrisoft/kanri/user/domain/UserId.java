package com.kanrisoft.kanri.user.domain;

public record UserId(Long id) {
    public static UserId of(Long id) {
        return new UserId(id);
    }
}