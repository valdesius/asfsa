package com.braincon.dto.responses;

import com.braincon.services.auth.MyCustomUserDetails;

public class AuthResponse {

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String token;
    private String role;
    private int userId;
    private String username; // Это email пользователя
    private String firstName;
    private String lastName;
    // END OF NO ARGS CONSTRUCTOR.

    public AuthResponse(String token, MyCustomUserDetails userDetails) {
        this.token = token;
        this.role = userDetails.getAuthorities().isEmpty() ? null :
                userDetails.getAuthorities().iterator().next().getAuthority();
        this.userId = userDetails.getUserId();
        this.username = userDetails.getUsername();
        this.firstName = userDetails.getFirstName();
        this.lastName = userDetails.getLastName();
    }


}
// END OF AUTH RESPONSE CLASS.
