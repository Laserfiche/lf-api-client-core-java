// Copyright (c) Laserfiche.
// Licensed under the MIT License. See LICENSE in the project root for license information.
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
