package com.laserfiche.api.client.selfhosted;

import com.laserfiche.api.client.model.CreateConnectionRequest;
import com.laserfiche.api.client.model.SessionKeyInfo;

import java.util.concurrent.CompletableFuture;

public interface TokenClient {

    /**
     * @param repoId Repository name
     * @param body   Request body that contains username, passward and grant type
     * @return Create an access token successfuly.
     */
    CompletableFuture<SessionKeyInfo> createAccessToken(String repoId, CreateConnectionRequest body);
}
