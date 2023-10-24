// Copyright (c) Laserfiche.
// Licensed under the MIT License. See LICENSE in the project root for license information.
package com.laserfiche.api.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The response from the Laserfiche Cloud OAuth 2.0 token endpoint.
 */
public class GetAccessTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("scope")
    private String scope;

    /**
     * Returns the Laserfiche Cloud OAuth 2.0 access token.
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets the Laserfiche Cloud OAuth 2.0 access token.
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Returns the lifetime in seconds of the access token.
     */
    public Integer getExpiresIn() {
        return expiresIn;
    }

    /**
     * Sets the lifetime in seconds of the access token.
     */
    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
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
     * Returns an optional refresh token used to get a new Laserfiche Cloud OAuth 2.0 access token.
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Sets a refresh token used to get a new Laserfiche Cloud OAuth 2.0 access token.
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * Returns the scope configured on the access token.
     */
    public String getScope() {
        return scope;
    }

    /**
     * Sets the scope configured on the access token.
     */
    public void setScope(String scope) {
        this.scope = scope;
    }
}
