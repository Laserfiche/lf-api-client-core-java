package com.laserfiche.api.client.httphandlers;

import com.laserfiche.api.client.apiserver.TokenClient;
import com.laserfiche.api.client.apiserver.TokenClientImpl;
import com.laserfiche.api.client.model.CreateConnectionRequest;
import com.laserfiche.api.client.model.SessionKeyInfo;

public class UsernamePasswordHandler implements HttpRequestHandler {
    private String accessToken;
    private final String grantType = "password";
    private final String repositoryId;
    private final String baseUrl;
    private final TokenClient client;
    private final CreateConnectionRequest request;

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
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.repositoryId = repositoryId;
        request = new CreateConnectionRequest();
        request.setPassword(password);
        request.setUsername(username);
        request.setGrantType(grantType);
        if (client == null) {
            this.client = new TokenClientImpl(this.baseUrl);
        } else {
            this.client = client;
        }
    }

    @Override
    public BeforeSendResult beforeSendAsync(Request request) {
        BeforeSendResult result = new BeforeSendResult();
        if (accessToken == null) {
            SessionKeyInfo tokenResponse = client.createAccessToken(repositoryId, this.request);
            accessToken = tokenResponse.getAccessToken();
            request
                    .headers()
                    .append("Authorization", "Bearer " + accessToken);
            return result;
        } else {
            request
                    .headers()
                    .append("Authorization", "Bearer " + accessToken);
        }
        return result;
    }

    @Override
    public boolean afterSendAsync(Response response) {
        boolean shouldRetry = (response.status() == 401);
        if (shouldRetry) {
            accessToken = null; // In case exception happens when getting the access token
        }
        return shouldRetry;
    }

    @Override
    public void close() {
        client.close();
    }
}
