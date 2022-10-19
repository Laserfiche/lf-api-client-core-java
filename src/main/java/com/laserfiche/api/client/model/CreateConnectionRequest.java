package com.laserfiche.api.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateConnectionRequest {
    @JsonProperty("grant_type")
    private String grantType = null;

    @JsonProperty("username")
    private Object username = null;

    @JsonProperty("password")
    private Object password = null;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

}
