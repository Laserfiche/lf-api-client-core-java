package com.laserfiche.api.client.httphandlers;

public class HeaderKeyValueImpl implements HeaderKeyValue {
    private final String headerName;
    private final String header;

    public HeaderKeyValueImpl(String headerName, String header) {
        this.header = header;
        this.headerName = headerName;
    }

    @Override
    public String getHeaderName() {
        return headerName;
    }

    @Override
    public String getHeader() {
        return header;
    }
}
