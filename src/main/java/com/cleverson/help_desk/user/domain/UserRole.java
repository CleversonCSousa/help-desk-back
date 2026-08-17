package com.cleverson.help_desk.user.domain;

public enum UserRole {

    ADMIN("admin"),
    TECHNICIAN("technician"),
    CUSTOMER("customer");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
