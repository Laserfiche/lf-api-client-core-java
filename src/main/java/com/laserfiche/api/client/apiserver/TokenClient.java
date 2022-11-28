package com.laserfiche.api.client.apiserver;

import com.laserfiche.api.client.model.CreateConnectionRequest;
import com.laserfiche.api.client.model.SessionKeyInfo;

public interface TokenClient extends AutoCloseable {

    /**
     * @param repoId Repository name
     * @param body   Request body that contains username, password and grant type
     * @return A SessionKeyInfo object that contains an access token.
     */
    SessionKeyInfo createAccessToken(String repoId, CreateConnectionRequest body);

    /**
     * Since the underlying resource (the HTTP client) won't throw any exception during its close() invocation.
     * We override the signature of the close() to not include any checked exception.
     */
    @Override
    void close();
}
