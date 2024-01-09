package com.example.jpa.user.model;

public enum UserRole {

    NORMAL("NORMAL"), ADMIN("ADMIN");
    String value;
    UserRole(String role) {
        this.value = role;
    }
    public String getValue() {
        return this.value;
    }
}
