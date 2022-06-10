package com.laserfiche.api.client.httphandlers;

public class ResponseImpl implements Response {
    private short status;

    public void Response(short status) {
        this.status = status;
    }

    @Override
    public short status() {
        return status;
    }
}
