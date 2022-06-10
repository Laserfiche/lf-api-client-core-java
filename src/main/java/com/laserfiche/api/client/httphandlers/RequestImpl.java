package com.laserfiche.api.client.httphandlers;

public class RequestImpl implements Request {
    private String url;
    private Headers headers;

    public void Request() {
        headers = new HeadersImpl();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public Headers getHeaders() {
        return headers;
    }
}
