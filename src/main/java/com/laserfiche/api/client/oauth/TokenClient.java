package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;

import java.util.concurrent.CompletableFuture;

public interface TokenClient {
    /**
     * Gets an OAuth access token given a Laserfiche cloud service principal key and an OAuth service application access key.
     * @param spKey Laserfiche cloud service principal key
     * @param accessKey OAuth service application access key
     * @return
     */
    CompletableFuture<GetAccessTokenResponse> getAccessTokenFromServicePrincipal(String spKey, AccessKey accessKey);

    /**
     * Gets an OAuth access token given an OAuth code.
     * @param code Authorization code
     * @param redirectUri Authorization endpoint redirect URI
     * @param clientId OAuth application client ID
     * @param codeVerifier PKCE code verifier
     * @return
     */
    CompletableFuture<GetAccessTokenResponse> getAccessTokenFromCode(String code, String redirectUri, String clientId, String codeVerifier);

    /**
     * Gets a refreshed access token given a refresh token.
     * @param refreshToken Refresh token
     * @param clientId OAuth application client ID
     * @return
     */
    CompletableFuture<GetAccessTokenResponse> refreshAccessToken(String refreshToken, String clientId);
}
