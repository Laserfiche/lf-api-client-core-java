package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;

/**
 * The Laserfiche Cloud token route API client.
 */
public interface TokenClient extends AutoCloseable {
    /**
     * Gets an OAuth access token given a Laserfiche Cloud service principal key and an OAuth service application access key.
     * These values can be exported from the Laserfiche Developer Console. This is the client credentials flow that applies
     * to service applications.
     *
     * @param servicePrincipalKey The service principal key created for the service principal from the Laserfiche Account Administration.
     * @param accessKey The access key exported from the Laserfiche Developer Console.
     * @return A response that contains an access token
     */
    GetAccessTokenResponse getAccessTokenFromServicePrincipal(String servicePrincipalKey, AccessKey accessKey);

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

    /*
     * Since the underlying resource (the HTTP client) won't throw any exception during its close() invocation.
     * We override the signature of the close() to not include any checked exception.
     */
    /**
     * An implementation of {@link AutoCloseable} that does not throw any checked exceptions.
     * {@inheritDoc}
     */
    @Override
    void close();
}
