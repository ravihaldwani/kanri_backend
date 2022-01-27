package com.kanrisoft.kanri.space.domain;

public record SpaceId(Long id) {
    public static SpaceId of(Long id) {
        return new SpaceId(id);
    }
}
