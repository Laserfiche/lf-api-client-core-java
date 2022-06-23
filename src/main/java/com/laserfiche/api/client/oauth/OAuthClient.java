package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.model.GetAccessTokenResponse;
import retrofit2.http.*;

import java.util.concurrent.CompletableFuture;

/**
 * NOTE: This interface is an implementation detail and could change in any time. Do not directly use it.
 * Specifies how the underlying HTTP client should work to request access token. See <a href="https://square.github.io/retrofit/">...</a>
 * for more details.
 */
public interface OAuthClient {
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("Token")
    CompletableFuture<GetAccessTokenResponse> getAccessToken(@Field("grant_type") String grantType, @Header("Authorization") String authorization);
    
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("Token")
    CompletableFuture<GetAccessTokenResponse> getAccessTokenFromCode(@Field("client_id") String clientId, @Field("grant_type") String grantType, @Field("code") String code, @Field("redirect_uri") String redirectUri, @Field("code_verifier") String codeVerifier, @Header("Content-Type") String contentType, @Header("Authorization") String authorization);

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("Token")
    CompletableFuture<GetAccessTokenResponse> refreshAccessToken(@Field("grant_type") String grantType, @Field("refresh_token") String refreshToken, @Header("Content-Type") String contentType, @Header("Authorization") String authorization);
}
