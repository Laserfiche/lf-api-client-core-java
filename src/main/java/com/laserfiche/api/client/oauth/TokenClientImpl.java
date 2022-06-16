package com.laserfiche.api.client.oauth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.laserfiche.api.client.oauth.OAuthUtil.createBearer;
import static com.laserfiche.api.client.oauth.OAuthUtil.getOAuthApiBaseUri;


public class TokenClientImpl implements TokenClient {
    private final OAuthClient client;

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
        return CompletableFuture.supplyAsync(() -> {
            Call<GetAccessTokenResponse> call = client.getAccessToken("client_credentials", bearer);
            Response<GetAccessTokenResponse> response;
            try {
                response = call.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return response.body();
        });
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> getAccessTokenFromCode(String code, String redirectUri, String clientId, String codeVerifier) {
        throw new UnsupportedOperationException("Authorization code flow is currently not supported.");
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> refreshAccessToken(String refreshToken, String clientId) {
        throw new UnsupportedOperationException("Authorization code flow is currently not supported.");
    }
}
