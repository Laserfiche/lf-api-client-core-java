package com.laserfiche.api.client.httphandlers;

import com.laserfiche.api.client.APIServer.SessionKeyInfo;
import com.laserfiche.api.client.APIServer.TokenClient;
import com.laserfiche.api.client.APIServer.TokenClientImpl;
import com.laserfiche.api.client.model.CreateConnectionRequest;

import java.util.concurrent.CompletableFuture;

public class UsernamePasswordHandler implements HttpRequestHandler {
    private String _accessToken;
    private final String GRANTTYPE = "password";
    private String _repoId;
    private String _baseUrl;
    private TokenClient _client;
    private CreateConnectionRequest _request;

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
        _baseUrl = baseUrl;
        _repoId = repositoryId;
        _request = new CreateConnectionRequest();
        _request.setPassword(password);
        _request.setUsername(username);
        _request.setGrantType(GRANTTYPE);
        if (client == null) {
            _client = new TokenClientImpl(_baseUrl);
        } else {
            _client = client;
        }
    }

    @Override
    public CompletableFuture<BeforeSendResult> beforeSendAsync(Request request) {
        BeforeSendResult result = new BeforeSendResult();
        if (_accessToken == null) {
            CompletableFuture<SessionKeyInfo> future = _client.createAccessToken(_repoId, _request);
            return future.thenApply(tokenResponse -> {
                _accessToken = tokenResponse.getAccessToken();
                request
                        .headers()
                        .append("Authorization", "Bearer " + _accessToken);
                return result;
            });
        }
        if (_accessToken != null) {
            request
                    .headers()
                    .append("Authorization", "Bearer " + _accessToken);
        }
        return CompletableFuture.completedFuture(result);
    }

    @Override
    public CompletableFuture<Boolean> afterSendAsync(Response response) {
        boolean shouldRetry;
        if (response.status() == 401) {
            _accessToken = null; // In case exception happens when getting the access token
            shouldRetry = true;
        } else {
            shouldRetry = false;
        }
        return CompletableFuture.completedFuture(shouldRetry);
    }
}
