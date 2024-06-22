package com.braincon.models;

public enum Role {
    STUDENT("ROLE_STUDENT"),
    MENTOR("ROLE_MENTOR"),
    GUEST("ROLE_GUEST");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }
}