package com.laserfiche.api.client.httphandlers;

/**
 * Represents an HTTP request.
 */
public interface Request {
    /**
     * Full URL of the request.
     */
    String url();

    /**
     * Headers associated with this request.
     */
    Headers headers();
}
