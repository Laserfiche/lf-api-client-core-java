package com.laserfiche.api.client.httphandlers;

import com.laserfiche.api.client.APIServer.SessionKeyInfo;
import com.laserfiche.api.client.APIServer.TokenClient;
import com.laserfiche.api.client.APIServer.TokenClientImpl;
import com.laserfiche.api.client.model.CreateConnectionRequest;

import java.util.concurrent.CompletableFuture;

public class UsernamePasswordHandler implements HttpRequestHandler {
    private String accessToken;
    private final String GRANTTYPE = "password";
    private String repositoryId;
    private String baseUrl;
    private TokenClient client;
    private CreateConnectionRequest request;

    /**
     * Creates a username and password authorization handler for self hosted API server
     *
     * @param repositoryId Repository name
     * @param username     The username used with "password" grant type.
     * @param password     The password used with "password" grant type.
     * @param baseUrl      APIServer Base Url e.g. https://example.com/LFRepositoryAPI
     * @param client       OPTIONAL
     */
    public UsernamePasswordHandler(String repositoryId, String username, String password, String baseUrl,
            TokenClient client) {
        this.baseUrl = baseUrl;
        this.repositoryId = repositoryId;
        request = new CreateConnectionRequest();
        request.setPassword(password);
        request.setUsername(username);
        request.setGrantType(GRANTTYPE);
        if (client == null) {
            this.client = new TokenClientImpl(this.baseUrl);
        } else {
            this.client = client;
        }
    }

    @Override
    public CompletableFuture<BeforeSendResult> beforeSendAsync(Request request) {
        BeforeSendResult result = new BeforeSendResult();
        if (accessToken == null) {
            CompletableFuture<SessionKeyInfo> future = client.createAccessToken(repositoryId, this.request);
            return future.thenApply(tokenResponse -> {
                accessToken = tokenResponse.getAccessToken();
                request
                        .headers()
                        .append("Authorization", "Bearer " + accessToken);
                return result;
            });
        }
        if (accessToken != null) {
            request
                    .headers()
                    .append("Authorization", "Bearer " + accessToken);
        }
        return CompletableFuture.completedFuture(result);
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
        return CompletableFuture.completedFuture(shouldRetry);
    }
}
