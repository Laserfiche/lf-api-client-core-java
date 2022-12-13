package com.laserfiche.api.client.httphandlers;

/**
 * Represents an HTTP request.
 */
public class RequestImpl implements Request {
    private String url;
    private final Headers headers;

    /**
     * Creates a new HTTP Request.
     */
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
