package com.laserfiche.api.client.httphandlers;

/**
 * Represents an HTTP header.
 */
public class HeaderKeyValueImpl implements HeaderKeyValue {
    private final String headerName;
    private final String header;

    /**
     * Creates a new HTTP Header.
     * @param headerName Name of the HTTP header.
     * @param header Value of the HTTP header.
     */
    public HeaderKeyValueImpl(String headerName, String header) {
        this.header = header;
        this.headerName = headerName;
    }

    @Override
    public String headerName() {
        return headerName;
    }

    @Override
    public String header() {
        return header;
    }
}
