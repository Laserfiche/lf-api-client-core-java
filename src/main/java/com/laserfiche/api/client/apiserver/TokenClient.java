package com.laserfiche.api.client.apiserver;

import com.laserfiche.api.client.model.CreateConnectionRequest;
import com.laserfiche.api.client.model.SessionKeyInfo;

import java.util.concurrent.CompletableFuture;

public interface TokenClient extends AutoCloseable {

    /**
     * @param repoId Repository name
     * @param body   Request body that contains username, password and grant type
     * @return Create an access token successfully.
     */
    CompletableFuture<SessionKeyInfo> createAccessToken(String repoId, CreateConnectionRequest body);

    /**
     * Overrides close() in AutoCloseable as no meaningful exception can be handled.
     */
    @Override
    void close();
}
