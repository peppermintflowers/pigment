package com.pigment.usermanagement.model;

/**
 * LOGINRESPONSE
 * This class is used to model the response carrying jwt token that is returned to user after authentication
 */
public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
