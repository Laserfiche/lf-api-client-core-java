package com.laserfiche.api.client.httphandlers;

import com.laserfiche.api.client.apiserver.TokenClient;
import com.laserfiche.api.client.apiserver.TokenClientImpl;
import com.laserfiche.api.client.model.CreateConnectionRequest;
import com.laserfiche.api.client.model.SessionKeyInfo;

/**
 * Username password HTTP handler for Laserfiche Self-Hosted API Server.
 */
public class UsernamePasswordHandler implements HttpRequestHandler {
    private String accessToken;
    private final String grantType = "password";
    private final String repositoryId;
    private final TokenClient client;
    private final CreateConnectionRequest request;

    /**
     * Creates a username and password authorization handler for the Laserfiche Self-Hosted API server
     *
     * @param repositoryId Repository ID.
     * @param username     The username used with "password" grant type.
     * @param password     The password used with "password" grant type.
     * @param baseUrl      APIServer Base Url e.g. https://{APIServerName}/LFRepositoryAPI.
     * @param client       OPTIONAL
     */
    public UsernamePasswordHandler(String repositoryId, String username, String password, String baseUrl,
            TokenClient client) {
        if (baseUrl == null) {
            throw new NullPointerException();
        }
        baseUrl = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        this.repositoryId = repositoryId;
        request = new CreateConnectionRequest();
        request.setPassword(password);
        request.setUsername(username);
        request.setGrantType(grantType);
        if (client == null) {
            this.client = new TokenClientImpl(baseUrl);
        } else {
            this.client = client;
        }
    }

    @Override
    public BeforeSendResult beforeSend(Request request) {
        BeforeSendResult result = new BeforeSendResult();
        if (accessToken == null && !nullOrEmpty(this.request.getUsername()) && !nullOrEmpty(
                this.request.getPassword())) {
            SessionKeyInfo tokenResponse = client.createAccessToken(repositoryId, this.request);
            accessToken = tokenResponse.getAccessToken();
            request
                    .headers()
                    .append("Authorization", "Bearer " + accessToken);
            return result;
        } else if (accessToken != null) {
            request
                    .headers()
                    .append("Authorization", "Bearer " + accessToken);
        }
        return result;
    }

    @Override
    public boolean afterSend(Response response) {
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

    private static boolean nullOrEmpty(String str) {
        return str == null || str.length() == 0;
    }
}
