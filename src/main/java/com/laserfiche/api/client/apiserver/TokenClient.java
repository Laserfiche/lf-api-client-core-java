// Copyright (c) Laserfiche
// Licensed under the MIT License. See LICENSE in the project root for license information.
package com.laserfiche.api.client.apiserver;

import com.laserfiche.api.client.model.CreateConnectionRequest;
import com.laserfiche.api.client.model.SessionKeyInfo;

/**
 * The Laserfiche Self-Hosted token route API client.
 */
public interface TokenClient extends AutoCloseable {

    /**
     * Gets a Laserfiche Self-Hosted access token.
     * @param repositoryId Repository ID.
     * @param body   Request body used to get a Laserfiche Self-Hosted access token.
     * @return A {@link SessionKeyInfo} object that contains an access token.
     */
    SessionKeyInfo createAccessToken(String repositoryId, CreateConnectionRequest body);

    /**
     * An implementation of {@link AutoCloseable} that does not throw any checked exceptions.
     * {@inheritDoc}
     */
    @Override
    void close();
}
