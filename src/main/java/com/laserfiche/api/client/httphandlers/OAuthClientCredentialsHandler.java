// Copyright (c) Laserfiche.
// Licensed under the MIT License. See LICENSE in the project root for license information.
package com.laserfiche.api.client.httphandlers;

import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.laserfiche.api.client.oauth.TokenClient;
import com.laserfiche.api.client.oauth.TokenClientImpl;

/**
 * Laserfiche Cloud OAuth client credentials HTTP handler.
 */
public class OAuthClientCredentialsHandler implements HttpRequestHandler {
    private String accessToken;
    private final String servicePrincipalKey;
    private final AccessKey accessKey;
    private final String scope;
    private final TokenClient client;

    /**
     * Creates a new Laserfiche Cloud OAuth client credentials HTTP handler.
     * @param servicePrincipalKey The service principal key created for the service principal from the Laserfiche Account Administration.
     * @param accessKey The access key exported from the Laserfiche Developer Console.
     */
    public OAuthClientCredentialsHandler(String servicePrincipalKey, AccessKey accessKey) {
        this(servicePrincipalKey, accessKey, null);
    }

    /**
     * Creates a new Laserfiche Cloud OAuth client credentials HTTP handler.
     * @param servicePrincipalKey The service principal key created for the service principal from the Laserfiche Account Administration.
     * @param accessKey The access key exported from the Laserfiche Developer Console.
     * @param scope The requested space-delimited scopes for the access token.
     */
    public OAuthClientCredentialsHandler(String servicePrincipalKey, AccessKey accessKey, String scope) {
        this.servicePrincipalKey = servicePrincipalKey;
        this.accessKey = accessKey;
        this.scope = scope;
        client = new TokenClientImpl(accessKey.getDomain());
    }

    @Override
    public BeforeSendResult beforeSend(com.laserfiche.api.client.httphandlers.Request request) {
        BeforeSendResult result = new BeforeSendResult();
        if (accessToken == null || accessToken.equals("")) {
            GetAccessTokenResponse tokenResponse  = client.getAccessTokenFromServicePrincipal(servicePrincipalKey, accessKey, scope);
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
