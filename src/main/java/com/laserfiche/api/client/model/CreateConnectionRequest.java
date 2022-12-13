package com.laserfiche.api.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The request used with the "password" grant type to get a Laserfiche Self-Hosted access token.
 */
public class CreateConnectionRequest {
    @JsonProperty("grant_type")
    private String grantType = null;

    @JsonProperty("username")
    private String username = null;

    @JsonProperty("password")
    private String password = null;

    /**
     * Returns the grant type.
     */
    public String getGrantType() {
        return grantType;
    }

    /**
     * Sets the grant type
     */
    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    /**
     * Returns the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
