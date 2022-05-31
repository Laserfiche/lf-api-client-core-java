package com.laserfiche.api.client.httphandlers;

import com.laserfiche.api.client.model.GetAccessTokenResponse;

import java.util.concurrent.CompletableFuture;

public interface HttpRequestHandler {
    CompletableFuture<GetAccessTokenResponse> beforeSendAsync(HttpRequest request);

    CompletableFuture<Boolean> afterSendAsync(HttpResponse response);
}
