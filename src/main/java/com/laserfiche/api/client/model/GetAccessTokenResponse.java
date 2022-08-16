package com.laserfiche.api.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetAccessTokenResponse {
  @JsonProperty("access_token")
  public String accessToken;

  @JsonProperty("expires_in")
  public Integer expiresIn;

  @JsonProperty("token_type")
  public String tokenType;

  @JsonProperty("refresh_token")
  public String refreshToken;
}
