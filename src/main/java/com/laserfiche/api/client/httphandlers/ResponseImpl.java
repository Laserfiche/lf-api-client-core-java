package com.laserfiche.api.client.httphandlers;

public class ResponseImpl implements Response {
    private short status;
    private Request request;

    public ResponseImpl(short status) {
        initialize(status, null);
    }

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
