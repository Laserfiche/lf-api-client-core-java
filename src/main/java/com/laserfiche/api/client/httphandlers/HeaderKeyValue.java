package com.laserfiche.api.client.httphandlers;

public interface HeaderKeyValue {
    /**
     * @return Name of the HTTP header.
     */
    String headerName();

    /**
     * @return HTTP header.
     */
    String header();
}
