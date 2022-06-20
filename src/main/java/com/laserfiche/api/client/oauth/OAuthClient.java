package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.model.GetAccessTokenResponse;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Specifies how the underlying HTTP client should work to request access token. See https://square.github.io/retrofit/
 * for more details.
 */
public interface OAuthClient {
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("Token")
    Call<GetAccessTokenResponse> getAccessToken(@Field("grant_type") String grantType, @Header("Authorization") String authorization);
}
