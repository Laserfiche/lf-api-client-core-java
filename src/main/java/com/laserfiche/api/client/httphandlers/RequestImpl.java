package com.laserfiche.api.client.httphandlers;

public class RequestImpl implements Request {
    private String url;
    private final Headers headers;

    public RequestImpl() {
        headers = new HeadersImpl();
    }

    @Override
    public String url() {
        return url;
    }

    @Override
    public Headers headers() {
        return headers;
    }
}
