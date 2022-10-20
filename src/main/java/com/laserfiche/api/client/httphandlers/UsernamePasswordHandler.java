package com.laserfiche.api.client.httphandlers;

import com.laserfiche.api.client.APIServer.SessionKeyInfo;
import com.laserfiche.api.client.APIServer.TokenClient;
import com.laserfiche.api.client.APIServer.TokenClientImpl;
import com.laserfiche.api.client.model.CreateConnectionRequest;

import java.util.concurrent.CompletableFuture;

public class UsernamePasswordHandler implements HttpRequestHandler {
    private String accessToken;
    private final String GRANTTYPE = "password";
    private String repoId;
    private String username;
    private String password;
    private String baseUrl;
    private TokenClient client;
    private CreateConnectionRequest request;

    public UsernamePasswordHandler(String repoId, String username, String password, String baseUrl,
            TokenClient client) {
        this.username = username;
        this.password = password;
        this.baseUrl = baseUrl;
        this.repoId = repoId;
        request = new CreateConnectionRequest();
        request.setPassword(this.password);
        request.setUsername(this.username);
        request.setGrantType(GRANTTYPE);
        if (client == null) {
            this.client = new TokenClientImpl(this.baseUrl);
        } else {
            this.client = client;
        }
    }

    @Override
    public CompletableFuture<BeforeSendResult> beforeSendAsync(Request request) {
        CompletableFuture<SessionKeyInfo> future;
        BeforeSendResult result = new BeforeSendResult();
        if (accessToken == null || accessToken.equals("")) {
            future = client.createAccessToken(repoId, this.request);
            return future.thenApply(tokenResponse -> {
                accessToken = tokenResponse.getAccessToken();
                request
                        .headers()
                        .append("Authorization", "Bearer " + accessToken);
                return result;
            });
        } else {
            request
                    .headers()
                    .append("Authorization", "Bearer " + accessToken);
            return CompletableFuture.completedFuture(result);
        }
    }

    @Override
    public CompletableFuture<Boolean> afterSendAsync(Response response) {
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
