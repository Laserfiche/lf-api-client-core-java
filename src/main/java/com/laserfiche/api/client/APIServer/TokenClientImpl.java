package com.laserfiche.api.client.APIServer;

import com.laserfiche.api.client.model.CreateConnectionRequest;
import com.laserfiche.api.client.oauth.TokenClientObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.util.concurrent.CompletableFuture;

import static com.laserfiche.api.client.oauth.OAuthUtil.getOAuthApiBaseUri;

public class TokenClientImpl implements TokenClient {
    private String baseUrl;

    public TokenClientImpl(String baseUrl) {
        this.baseUrl = baseUrl;
        Unirest
                .config()
                .setObjectMapper(new TokenClientObjectMapper());
    }

    @Override
    public CompletableFuture<SessionKeyInfo> createAccessToken(String repoId, CreateConnectionRequest body) {
        CompletableFuture<HttpResponse<SessionKeyInfo>> future = Unirest
                .post(baseUrl + "Token")
                .header("Accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("grant_type", "password")
                .field("username", (String) body.getUsername())
                .field("password", (String) body.getPassword())
                .asObjectAsync(SessionKeyInfo.class);
        return future.thenApply(httpResponse -> {
            if (httpResponse.getStatus() != 200) {
                throw new RuntimeException(httpResponse.getStatusText());
            }
            return httpResponse.getBody();
        });
    }
}
