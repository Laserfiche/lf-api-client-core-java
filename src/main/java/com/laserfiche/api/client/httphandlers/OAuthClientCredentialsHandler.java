package com.laserfiche.api.client.httphandlers;

import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.laserfiche.api.client.oauth.TokenApiClient;
import com.laserfiche.api.client.oauth.TokenApiImpl;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.concurrent.CompletableFuture;

public class OAuthClientCredentialsHandler implements HttpRequestHandler{
    private String accessToken;
    private String spKey;
    private AccessKey accessKey;
    private TokenApiClient client;

    private static int HTTP_STATUS_UNAUTHORIZED = 401;

    public OAuthClientCredentialsHandler(String servicePrincipalKey, AccessKey accessKey) {
        spKey = servicePrincipalKey;
        this.accessKey = accessKey;
        client = new TokenApiImpl(accessKey.getDomain());
    }

    @Override
    public CompletableFuture<BeforeSendResult> beforeSendAsync(com.laserfiche.api.client.httphandlers.Request request) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> afterSendAsync(com.laserfiche.api.client.httphandlers.Response response) {
        return null;
    }

    public CompletableFuture<BeforeSendResult> beforeSendAsync(Request.Builder request) {
        CompletableFuture<GetAccessTokenResponse> future = new CompletableFuture<>();
        BeforeSendResult result = new BeforeSendResult();
        if (accessToken == null || accessToken.equals("")) {
            try {
                future = client.getAccessTokenAsync(spKey, accessKey);
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
            future.thenApply(tokenResponse -> {
                accessToken = tokenResponse.getAccessToken();
                request.addHeader("Authorization", accessToken);
                return null;
            });
            return future.thenApply(tokenResponse -> {
                result.setRegionalDomain(accessKey.getDomain());
                return result;
            });
        }
        return CompletableFuture.supplyAsync(() -> {
            result.setRegionalDomain(accessKey.getDomain());
            return result;
        });
    }

    public CompletableFuture<Boolean> afterSendAsync(Response response) {
        boolean shouldRetry;
        if (response.code() == HTTP_STATUS_UNAUTHORIZED) {
            accessToken = null; // In case exception happens when getting the access token
            shouldRetry = true;
        } else {
            shouldRetry = false;
        }
        return CompletableFuture.supplyAsync(() -> shouldRetry);
    }
}
