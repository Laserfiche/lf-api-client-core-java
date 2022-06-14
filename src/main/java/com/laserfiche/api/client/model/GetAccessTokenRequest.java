package com.laserfiche.api.client.model;

import com.google.gson.annotations.SerializedName;

public class GetAccessTokenRequest {
  @SerializedName("client_id")
  private Object clientId = null;

  @SerializedName("grant_type")
  private String grantType = null;

  @SerializedName("code")
  private Object code = null;

  @SerializedName("redirect_uri")
  private Object redirectUri = null;

  @SerializedName("scope")
  private Object scope = null;

  @SerializedName("refresh_token")
  private Object refreshToken = null;

  @SerializedName("code_verifier")
  private Object codeVerifier = null;

   /**
   * The Client ID returned when the application was registered.
   * @return clientId
  **/
  public Object getClientId() {
    return clientId;
  }

  public void setClientId(Object clientId) {
    this.clientId = clientId;
  }

   /**
   * The value MUST be either of \&quot;authorization_code\&quot;, \&quot;refresh_token\&quot;, or \&quot;client_credentials\&quot;.
   * @return grantType
  **/
  public String getGrantType() {
    return grantType;
  }

  public void setGrantType(String grantType) {
    this.grantType = grantType;
  }

   /**
   * The authorization code returned by the authorization server in the first step of the authorization code flow.
   * @return code
  **/
  public Object getCode() {
    return code;
  }

  public void setCode(Object code) {
    this.code = code;
  }

   /**
   * This should be equal to the Redirect URI sent to /authorize endpoint in the first step of the authorization code flow.
   * @return redirectUri
  **/
  public Object getRedirectUri() {
    return redirectUri;
  }

  public void setRedirectUri(Object redirectUri) {
    this.redirectUri = redirectUri;
  }

   /**
   * The scope of the requested access token.
   * @return scope
  **/
  public Object getScope() {
    return scope;
  }

  public void setScope(Object scope) {
    this.scope = scope;
  }

   /**
   * The refresh token, which can be used to obtain new access tokens.
   * @return refreshToken
  **/
  public Object getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(Object refreshToken) {
    this.refreshToken = refreshToken;
  }

   /**
   * A code verifier, used to verify the code_challenge sent to /authorize endpoint in the first step of the authorization code flow.
   * @return codeVerifier
  **/
  public Object getCodeVerifier() {
    return codeVerifier;
  }

  public void setCodeVerifier(Object codeVerifier) {
    this.codeVerifier = codeVerifier;
  }
}