package com.laserfiche.api.client.httphandlers;

public class ResponseImpl implements Response {
    private short status;

    public ResponseImpl(short status) {
        this.status = status;
    }

    @Override
    public short status() {
        return status;
    }
}
