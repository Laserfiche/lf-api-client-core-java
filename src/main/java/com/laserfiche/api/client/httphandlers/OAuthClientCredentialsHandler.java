package com.laserfiche.api.client.httphandlers;

import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.laserfiche.api.client.oauth.TokenClient;
import com.laserfiche.api.client.oauth.TokenClientImpl;

public class OAuthClientCredentialsHandler implements HttpRequestHandler {
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
    public BeforeSendResult beforeSend(com.laserfiche.api.client.httphandlers.Request request) {
        BeforeSendResult result = new BeforeSendResult();
        if (accessToken == null || accessToken.equals("")) {
            GetAccessTokenResponse tokenResponse  = client.getAccessTokenFromServicePrincipal(spKey, accessKey);
            accessToken = tokenResponse.getAccessToken();
        }
        request.headers().append("Authorization", "Bearer " + accessToken);
        result.setRegionalDomain(accessKey.getDomain());
        return result;
    }

    @Override
    public boolean afterSend(com.laserfiche.api.client.httphandlers.Response response) {
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
