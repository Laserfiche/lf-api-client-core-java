// Copyright (c) Laserfiche.
// Licensed under the MIT License. See LICENSE in the project root for license information.
package com.laserfiche.api.client.httphandlers;

/**
 * Provides a way to modify an HTTP request and to handle the response.
 */
public interface HttpRequestHandler extends AutoCloseable {

    /**
     * Invoked before an HTTP request is sent. The authorization header containing an access token will be added to the request.
     * @param request The HTTP request.
     * @return {@link BeforeSendResult}
     */
    BeforeSendResult beforeSend(Request request);

    /**
     * Invoked after an HTTP response is received and will determine if a new access token should be retrieved.
     * @param response The HTTP response.
     * @return True, if the request should be retried.
     */
    boolean afterSend(Response response);

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
