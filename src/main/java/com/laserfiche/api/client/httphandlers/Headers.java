// Copyright (c) Laserfiche.
// Licensed under the MIT License. See LICENSE in the project root for license information.
package com.laserfiche.api.client.httphandlers;

import java.util.Collection;
import java.util.Set;

/**
 * Represents a collection of HTTP headers.
 */
public interface Headers {
    /**
     * Append the value of the header. If there's existing header under the same name, both values are retained.
     * @param name Name of the header.
     * @param value Value of the header.
     */
    void append(String name, String value);

    /**
     * Remove the header under this name.
     * @param name Name of the header.
     */
    void delete(String name);

    /**
     * Returns a collection of header key value pairs. If a header has multiple values, they are concatenated by a comma.
     */
    Collection<HeaderKeyValue> entries();

    /**
     * Returns the header value. If a header has multiple values, they are concatenated by a comma.
     * @param name Name of the header.
     */
    String get(String name);

    /**
     * Check if the header is set.
     * @param name Name of the header.
     * @return A boolean indicating whether the specified header has been set.
     */
    boolean has(String name);

    /**
     * Return all header names (that are set).
     */
    Set<String> keys();

    /**
     * Set the header.
     * @param name Name of the header.
     * @param value Value of the header.
     */
    void set(String name, String value);

    /**
     * Returns a collection of header values. If a header has multiple values, they are concatenated by a comma.
     */
    Collection<String> values();
}
