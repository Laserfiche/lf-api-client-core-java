// Copyright (c) Laserfiche
// Licensed under the MIT License. See LICENSE in the project root for license information.
package com.laserfiche.api.client.httphandlers;

/**
 * Represents an HTTP response.
 */
public class ResponseImpl implements Response {
    private short status;
    private Request request;

    /**
     * Creates a new HTTP response.
     * @param status The HTTP status code.
     */
    public ResponseImpl(short status) {
        initialize(status, null);
    }

    /**
     * Creates a new HTTP response.
     * @param status The HTTP status code.
     * @param request The HTTP request.
     */
    public ResponseImpl(short status, Request request) {
        initialize(status, request);
    }

    // Constructor delegation
    private void initialize(short status, Request request) {
        this.status = status;
        this.request = request;
    }

    @Override
    public short status() {
        return status;
    }

    @Override
    public Request request() {
        return request;
    }
}
