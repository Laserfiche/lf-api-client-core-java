package com.laserfiche.api.client.httphandlers;

import java.util.concurrent.CompletableFuture;

public interface HttpRequestHandler {
    CompletableFuture<BeforeSendResult> beforeSendAsync(Request request);

    CompletableFuture<Boolean> afterSendAsync(Response response);
}
