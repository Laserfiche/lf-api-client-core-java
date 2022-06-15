package com.laserfiche.api.client.httphandlers;

import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.laserfiche.api.client.oauth.TokenClient;
import com.laserfiche.api.client.oauth.TokenClientImpl;

import java.util.concurrent.CompletableFuture;

public class OAuthClientCredentialsHandler implements HttpRequestHandler{
    private String accessToken;
    private final String spKey;
    private final AccessKey accessKey;
    private final TokenClient client;

    public OAuthClientCredentialsHandler(String servicePrincipalKey, AccessKey accessKey) {
        spKey = servicePrincipalKey;
        this.accessKey = accessKey;
        client = new TokenClientImpl(accessKey.getDomain());
    }

    @Override
    public CompletableFuture<BeforeSendResult> beforeSendAsync(com.laserfiche.api.client.httphandlers.Request request) {
        CompletableFuture<GetAccessTokenResponse> future = new CompletableFuture<>();
        BeforeSendResult result = new BeforeSendResult();
        if (accessToken == null || accessToken.equals("")) {
            future = client.getAccessTokenFromServicePrincipal(spKey, accessKey);
        }
        future.thenApply(tokenResponse -> {
            accessToken = tokenResponse.getAccessToken();
            request.headers().append("Authorization", accessToken);
            return null;
        });
        return future.thenApply(tokenResponse -> {
            result.setRegionalDomain(accessKey.getDomain());
            return result;
        });
    }

    @Override
    public CompletableFuture<Boolean> afterSendAsync(com.laserfiche.api.client.httphandlers.Response response) {
        boolean shouldRetry;
        if (response.status() == 401) {
            accessToken = null; // In case exception happens when getting the access token
            shouldRetry = true;
        } else {
            shouldRetry = false;
        }
        return CompletableFuture.supplyAsync(() -> shouldRetry);
    }
}
