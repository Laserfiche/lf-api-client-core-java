package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.concurrent.CompletableFuture;

import static com.laserfiche.api.client.oauth.OAuthUtil.createBearer;
import static com.laserfiche.api.client.oauth.OAuthUtil.getOAuthApiBaseUri;


public class TokenClientImpl implements TokenClient {
    private String baseUrl;
    public TokenClientImpl(String regionalDomain) {
        baseUrl = getOAuthApiBaseUri(regionalDomain);
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> getAccessTokenFromServicePrincipal(String spKey, AccessKey accessKey) {
        String bearer = createBearer(spKey, accessKey);
        return null;
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> getAccessTokenFromCode(String code, String redirectUri, String clientId, String clientSecret, String codeVerifier) {
        throw new NotImplementedException();
    }

    @Override
    public CompletableFuture<GetAccessTokenResponse> refreshAccessToken(String refreshToken, String clientId, String clientSecret) {
        throw new NotImplementedException();
    }
}
