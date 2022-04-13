package com.race.racewebapp.model;

public enum Permission {
    RACES_READ("races:read"),
    RACES_WRITE("races:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
