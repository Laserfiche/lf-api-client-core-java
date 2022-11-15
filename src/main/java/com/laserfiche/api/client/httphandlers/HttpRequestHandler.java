package com.laserfiche.api.client.httphandlers;

import java.util.concurrent.CompletableFuture;

public interface HttpRequestHandler extends AutoCloseable {

    /**
     * Invoked before an HTTP request with the request message and cancellation token.
     * @param request The request that will have the authorization header set.
     * @return The request that has the authorization header already set.
     */
    CompletableFuture<BeforeSendResult> beforeSendAsync(Request request);

    /**
     * Invoked after an HTTP request with the response message and cancellation token.
     * @param response The HTTP response.
     * @return True if the request should be retried.
     */
    CompletableFuture<Boolean> afterSendAsync(Response response);

    /**
     * Since the underlying resource (the HTTP client) won't throw any exception during its close() invocation.
     * We override the signature of the close() to not include any checked exception.
     */
    @Override
    void close();
}
