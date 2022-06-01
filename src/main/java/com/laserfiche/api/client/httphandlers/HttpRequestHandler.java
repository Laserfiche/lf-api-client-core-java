package com.laserfiche.api.client.httphandlers;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.concurrent.CompletableFuture;

public interface HttpRequestHandler {
    CompletableFuture<BeforeSendResult> beforeSendAsync(Request.Builder request);

    CompletableFuture<Boolean> afterSendAsync(Response response);
}
