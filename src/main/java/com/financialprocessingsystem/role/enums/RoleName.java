package com.financialprocessingsystem.role.enums;

public enum RoleName {
    ROLE_CUSTOMER("Customer"),
    ROLE_ADMIN("Administrator"),
    ROLE_MANAGER("Manager");

    private final String description;

    RoleName(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
