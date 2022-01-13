package com.kanrisoft.kanri.board;

import java.util.Set;

public class BoardPermissions {
    private final Set<Permission> permissions;

    public BoardPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    void addPermission(Permission permission) {
        permissions.add(permission);
    }

}
