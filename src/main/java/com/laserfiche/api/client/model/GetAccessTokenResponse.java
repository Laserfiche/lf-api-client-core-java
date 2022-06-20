package com.laserfiche.api.client.model;

import com.google.gson.annotations.SerializedName;

public class GetAccessTokenResponse {
  @SerializedName("access_token")
  private String accessToken = null;

  @SerializedName("expires_in")
  private Integer expiresIn = null;

  @SerializedName("token_type")
  private String tokenType = null;

  @SerializedName("refresh_token")
  private String refreshToken = null;

   /**
   * Get accessToken
   * @return accessToken
  **/
  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

   /**
   * Get expiresIn
   * @return expiresIn
  **/
  public Integer getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(Integer expiresIn) {
    this.expiresIn = expiresIn;
  }

   /**
   * Get tokenType
   * @return tokenType
  **/
  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

   /**
   * Get refreshToken
   * @return refreshToken
  **/
  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
