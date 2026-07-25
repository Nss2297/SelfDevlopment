package com.waseel.pbm.authentication.model;

public class SsoRequest {
    
    private String bearerToken;

    public SsoRequest() {
    }

    public SsoRequest(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public String getBearerToken() {
        return bearerToken;
    }

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    
}
