package com.laserfiche.api.client.apiserver;

import com.laserfiche.api.client.model.CreateConnectionRequest;
import com.laserfiche.api.client.model.SessionKeyInfo;

/**
 * The Laserfiche Self-Hosted token route API client.
 */
public interface TokenClient extends AutoCloseable {

    /**
     * Gets a Laserfiche Self-Hosted access token.
     * @param repoId Repository name.
     * @param body   Request body used to get a Laserfiche Self-Hosted access token.
     * @return A {@link SessionKeyInfo} object that contains an access token.
     */
    SessionKeyInfo createAccessToken(String repoId, CreateConnectionRequest body);

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
