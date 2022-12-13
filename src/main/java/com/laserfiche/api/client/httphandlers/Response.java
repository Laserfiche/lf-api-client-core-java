package com.laserfiche.api.client.httphandlers;

/**
 * Represents an HTTP response.
 */
public interface Response {

    /**
     * Status code.
     */
    short status();

    /**
     * The corresponding request object.
     */
    Request request();
}
