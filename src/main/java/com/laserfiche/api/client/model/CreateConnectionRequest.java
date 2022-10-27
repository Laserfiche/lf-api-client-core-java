package com.laserfiche.api.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateConnectionRequest {
    @JsonProperty("grant_type")
    private String grantType = null;

    @JsonProperty("username")
    private String username = null;

    @JsonProperty("password")
    private String password = null;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
