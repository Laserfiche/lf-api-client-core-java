package com.laserfiche.api.client.httphandlers;

import kong.unirest.HttpResponse;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Represents an HTTP header.
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
     * @return A collection of header key value pairs. If a header has multiple values, they are concatenated by a comma.
     */
    Collection<HeaderKeyValue> entries();

    /**
     * @param name Name of the header.
     * @return The header under name.
     */
    String get(String name);

    /**
     * Check if the header is set.
     * @param name Name of the header.
     * @return A boolean indicating whether the specified header has been set.
     */
    boolean has(String name);

    /**
     * @return Return all header names (that are set).
     */
    Set<String> keys();

    /**
     * Set the header.
     * @param name Name of the header.
     * @param value Value of the header.
     */
    void set(String name, String value);

    /**
     * @return A collection of header values.
     */
    Collection<String> values();

}
