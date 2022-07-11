package com.laserfiche.api.client.httphandlers;

/**
 * Represents an HTTP response.
 */
public interface Response {

    /**
     * @return Status code.
     */
    short status();

    /**
     * @return The corresponding request object.
     */
    Request request();
}
