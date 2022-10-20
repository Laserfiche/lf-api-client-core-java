package com.laserfiche.api.client.APIServer;

import com.laserfiche.api.client.model.CreateConnectionRequest;

import java.util.concurrent.CompletableFuture;

public interface TokenClient {

    CompletableFuture<SessionKeyInfo> createAccessToken(String repoId, CreateConnectionRequest body);
}
