package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;

public interface TokenClient extends AutoCloseable {
    /**
     * Gets an OAuth access token given a Laserfiche cloud service principal key and an OAuth service application access key.
     *
     * @param spKey     Laserfiche cloud service principal key
     * @param accessKey OAuth service application access key
     * @return A response that contains an access token
     */
    GetAccessTokenResponse getAccessTokenFromServicePrincipal(String spKey, AccessKey accessKey);

    /**
     * Gets an OAuth access token given an OAuth code.
     *
     * @param code         Authorization code
     * @param redirectUri  Authorization endpoint redirect URI
     * @param clientId     OAuth application client ID
     * @param clientSecret OPTIONAL OAuth application client secret. Required for web apps.
     * @param codeVerifier OPTIONAL PKCE code verifier. Required for SPA apps.
     * @return A response that contains an access token
     */
    GetAccessTokenResponse getAccessTokenFromCode(String code, String redirectUri, String clientId, String clientSecret, String codeVerifier);

    /**
     * Gets a refreshed access token given a refresh token.
     *
     * @param refreshToken Refresh token
     * @param clientId     OAuth application client ID
     * @param clientSecret OPTIONAL OAuth application client secret. Required for web apps.
     * @return A response that contains an access token
     */
    GetAccessTokenResponse refreshAccessToken(String refreshToken, String clientId, String clientSecret);

    /**
     * Since the underlying resource (the HTTP client) won't throw any exception during its close() invocation.
     * We override the signature of the close() to not include any checked exception.
     */
    @Override
    void close();
}
