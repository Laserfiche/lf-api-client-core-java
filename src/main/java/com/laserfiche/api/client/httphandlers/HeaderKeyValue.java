// Copyright (c) Laserfiche.
// Licensed under the MIT License. See LICENSE in the project root for license information.
package com.laserfiche.api.client.httphandlers;

/**
 * Represents an HTTP header.
 */
public interface HeaderKeyValue {
    /**
     * Returns the name of the HTTP header.
     */
    String headerName();

    /**
     * Returns the value of the HTTP header.
     */
    String header();
}
