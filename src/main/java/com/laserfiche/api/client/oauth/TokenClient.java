// Copyright (c) Laserfiche
// Licensed under the MIT License. See LICENSE in the project root for license information.
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
     * Gets an OAuth access token given a Laserfiche Cloud service principal key and an OAuth service application access key.
     * These values can be exported from the Laserfiche Developer Console. This is the client credentials flow that applies
     * to service applications.
     *
     * @param servicePrincipalKey The service principal key created for the service principal from the Laserfiche Account Administration.
     * @param accessKey The access key exported from the Laserfiche Developer Console.
     * @param scope The requested space-delimited scopes for the access token.
     * @return A response that contains an access token
     */
    GetAccessTokenResponse getAccessTokenFromServicePrincipal(String servicePrincipalKey, AccessKey accessKey, String scope);

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
