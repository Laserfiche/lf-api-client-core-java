package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.deserialization.TokenClientObjectMapper;
import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.util.concurrent.CompletableFuture;

import static com.laserfiche.api.client.oauth.OAuthUtil.createBearer;
import static com.laserfiche.api.client.oauth.OAuthUtil.getOAuthApiBaseUri;


public class TokenClientImpl implements TokenClient {
    private String baseUrl;
    public TokenClientImpl(String regionalDomain) {
        baseUrl = getOAuthApiBaseUri(regionalDomain);
        Unirest.config().setObjectMapper(new TokenClientObjectMapper());
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> getAccessTokenFromServicePrincipal(String spKey, AccessKey accessKey) {
        String bearer = createBearer(spKey, accessKey);
        CompletableFuture<HttpResponse<GetAccessTokenResponse>> future = Unirest
                .post(baseUrl + "Token")
                .header("Authorization", bearer)
                .header("Accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("grant_type", "client_credentials")
                .asObjectAsync(GetAccessTokenResponse.class);
        return future.thenApply(httpResponse -> {
            if (httpResponse.getStatus() != 200) {
                throw new RuntimeException(httpResponse.getStatusText());
            }
            return httpResponse.getBody();
        });
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> getAccessTokenFromCode(String code, String redirectUri, String clientId, String clientSecret, String codeVerifier) {
        return null;
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> refreshAccessToken(String refreshToken, String clientId, String clientSecret) {
        return null;
    }
}
