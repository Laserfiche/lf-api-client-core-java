// Copyright (c) Laserfiche
// Licensed under the MIT License. See LICENSE in the project root for license information.
package com.laserfiche.api.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The response from the Laserfiche Self-Hosted token endpoint.
 */
public class SessionKeyInfo {
    @JsonProperty("access_token")
    private String accessToken = null;

    @JsonProperty("token_type")
    private String tokenType = null;

    @JsonProperty("expire_in")
    private int expireIn;

    /**
     * Returns the access token that can be used to authenticate with the repository apis.
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the access token that can be used to authenticate with the repository apis.
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Returns the token type that provides how to utilize the access token.
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Sets the token type that provides how to utilize the access token.
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * Returns the lifetime in seconds of the access token.
     */
    public int getExpireIn() {
        return expireIn;
    }

    /**
     * Sets the lifetime in seconds of the access token.
     */
    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

}
