package com.laserfiche.api.client.APIServer;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SessionKeyInfo {
    @JsonProperty("access_token")
    private String accessToken = null;

    @JsonProperty("token_type")
    private String tokenType = null;

    @JsonProperty("expire_in")
    private int expireIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

}
