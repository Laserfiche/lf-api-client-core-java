package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.model.GetAccessTokenResponse;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.concurrent.CompletableFuture;

/**
 * Specifies how the underlying HTTP client should work to request access token. See https://square.github.io/retrofit/
 * for more details.
 */
public interface OAuthClient {
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("Token")
    Call<GetAccessTokenResponse> getAccessToken(@Field("grant_type") String grantType, @Header("Authorization") String authorization);

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("Token")
    CompletableFuture<GetAccessTokenResponse> getAccessTokenFromCode(@Field("client_id") String clientId, @Field("grant_type") String grantType, @Field("code") String code, @Field("redirect_uri") String redirectUri, @Field("code_verifier") String codeVerifier, @Header("Content-Type") String contentType, @Header("Authorization") String authorization);

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("Token")
    CompletableFuture<GetAccessTokenResponse> refreshAccessToken(@Field("grant_type") String grantType, @Field("refresh_token") String refreshToken, @Header("Content-Type") String contentType, @Header("Authorization") String authorization);
}
