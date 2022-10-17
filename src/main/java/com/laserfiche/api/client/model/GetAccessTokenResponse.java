package com.laserfiche.api.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2022-10-17T14:51:42.906908300-04:00[America/New_York]")
public class GetAccessTokenResponse {
    @JsonProperty("access_token")
    private String accessToken = null;

    @JsonProperty("expires_in")
    private Integer expiresIn = null;

    @JsonProperty("token_type")
    private String tokenType = null;

    @JsonProperty("refresh_token")
    private String refreshToken = null;

    public GetAccessTokenResponse accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    @Schema(description = "")
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public GetAccessTokenResponse expiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    @Schema(description = "")
    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public GetAccessTokenResponse tokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    @Schema(description = "")
    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public GetAccessTokenResponse refreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    @Schema(description = "")
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GetAccessTokenResponse getAccessTokenResponse = (GetAccessTokenResponse) o;
        return Objects.equals(this.accessToken, getAccessTokenResponse.accessToken) &&
                Objects.equals(this.expiresIn, getAccessTokenResponse.expiresIn) &&
                Objects.equals(this.tokenType, getAccessTokenResponse.tokenType) &&
                Objects.equals(this.refreshToken, getAccessTokenResponse.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, expiresIn, tokenType, refreshToken);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class GetAccessTokenResponse {\n");

        sb.append("    accessToken: ").append(toIndentedString(accessToken)).append("\n");
        sb.append("    expiresIn: ").append(toIndentedString(expiresIn)).append("\n");
        sb.append("    tokenType: ").append(toIndentedString(tokenType)).append("\n");
        sb.append("    refreshToken: ").append(toIndentedString(refreshToken)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
