package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.model.GetAccessTokenResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface OAuthClient {
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("Token")
    Call<GetAccessTokenResponse> getAccessToken(@Field("grant_type") String grantType, @Header("Authorization") String authorization);
}
