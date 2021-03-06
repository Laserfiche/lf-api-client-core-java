package com.laserfiche.api.client.oauth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.laserfiche.api.client.oauth.OAuthUtil.*;


public class TokenClientImpl implements TokenClient {
    private final OAuthClient client;
    private static final String CONTENT_TYPE_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";

    public TokenClientImpl(String regionalDomain) {
        String baseAddress = getOAuthApiBaseUri(regionalDomain);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(300, TimeUnit.SECONDS)
                .connectTimeout(300, TimeUnit.SECONDS)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseAddress)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
        client = retrofit.create(OAuthClient.class);
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> getAccessTokenFromServicePrincipal(String spKey, AccessKey accessKey) {
        String bearer = createBearer(spKey, accessKey);
        return client.getAccessToken("client_credentials", bearer);
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> getAccessTokenFromCode(String code, String redirectUri, String clientId, String clientSecret, String codeVerifier) {
        String finalAuthorization = createBasic(clientId, clientSecret);
        return client.getAccessTokenFromCode(clientId, "authorization_code", code, redirectUri, codeVerifier, CONTENT_TYPE_WWW_FORM_URLENCODED, finalAuthorization);
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> refreshAccessToken(String refreshToken, String clientId, String clientSecret) {
        String finalAuthorization = createBasic(clientId, clientSecret);
        return client.refreshAccessToken(clientId, "refresh_token", refreshToken, CONTENT_TYPE_WWW_FORM_URLENCODED, finalAuthorization);
    }
}
