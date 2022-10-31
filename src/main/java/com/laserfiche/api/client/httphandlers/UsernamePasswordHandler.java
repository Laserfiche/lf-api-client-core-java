package com.laserfiche.api.client.httphandlers;

import com.laserfiche.api.client.apiserver.TokenClient;
import com.laserfiche.api.client.apiserver.TokenClientImpl;
import com.laserfiche.api.client.model.CreateConnectionRequest;
import com.laserfiche.api.client.model.SessionKeyInfo;

import java.util.concurrent.CompletableFuture;

public class UsernamePasswordHandler implements HttpRequestHandler {
    private String accessToken;
    private final String GRANT_TYPE = "password";
    private final String REPOSITORYID;
    private final String BASEURL;
    private final TokenClient CLIENT;
    private final CreateConnectionRequest REQUEST;

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
        this.BASEURL = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.lastIndexOf("/")) : baseUrl;
        this.REPOSITORYID = repositoryId;
        REQUEST = new CreateConnectionRequest();
        REQUEST.setPassword(password);
        REQUEST.setUsername(username);
        REQUEST.setGrantType(GRANT_TYPE);
        if (client == null) {
            this.CLIENT = new TokenClientImpl(this.BASEURL);
        } else {
            this.CLIENT = client;
        }
    }

    @Override
    public CompletableFuture<BeforeSendResult> beforeSendAsync(Request request) {
        BeforeSendResult result = new BeforeSendResult();
        if (accessToken == null) {
            CompletableFuture<SessionKeyInfo> future = CLIENT.createAccessToken(REPOSITORYID, this.REQUEST);
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
