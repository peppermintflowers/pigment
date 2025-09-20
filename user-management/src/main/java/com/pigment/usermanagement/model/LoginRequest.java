package com.pigment.usermanagement.model;

import lombok.Getter;

/**
 * LOGINREQUEST
 * This class is used to model the request details sent when logging in
 */
@Getter
public class LoginRequest {
    private String email;
    private String password;
    public LoginRequest(String email,String password){
        this.email=email;
        this.password=password;
    }
}
