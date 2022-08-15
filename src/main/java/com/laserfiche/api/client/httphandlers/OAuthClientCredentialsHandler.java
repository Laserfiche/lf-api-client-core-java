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
        client = new TokenClientImpl(accessKey.domain);
    }

    @Override
    public CompletableFuture<BeforeSendResult> beforeSendAsync(com.laserfiche.api.client.httphandlers.Request request) {
        CompletableFuture<GetAccessTokenResponse> future;
        BeforeSendResult result = new BeforeSendResult();
        if (accessToken == null || accessToken.equals("")) {
            future = client.getAccessTokenFromServicePrincipal(spKey, accessKey);
            return future.thenApply(tokenResponse -> {
                accessToken = tokenResponse.accessToken;
                request.headers().append("Authorization", "Bearer " + accessToken);
                result.setRegionalDomain(accessKey.domain);
                return result;
            });
        } else {
            request.headers().append("Authorization", "Bearer " + accessToken);
            result.setRegionalDomain(accessKey.domain);
            return CompletableFuture.completedFuture(result);
        }
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
