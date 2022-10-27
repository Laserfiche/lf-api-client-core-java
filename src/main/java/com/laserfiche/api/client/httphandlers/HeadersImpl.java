package com.laserfiche.api.client.httphandlers;

import kong.unirest.Header;
import kong.unirest.HttpResponse;

import java.util.*;
import java.util.stream.Collectors;

public class HeadersImpl implements Headers {
    private final Map<String, List<String>> headers;

    public HeadersImpl() {
        headers = new HashMap<>();
    }

    @Override
    public void append(String name, String value) {
        if (value == null) {
            return;
        }
        headers.computeIfAbsent(name, key -> new ArrayList<>());
        headers.get(name).add(value);
    }

    @Override
    public void delete(String name) {
        if (!headers.containsKey(name)) {
            return;
        }
        headers.remove(name);
    }

    @Override
    public Collection<HeaderKeyValue> entries() {
        return headers.entrySet().stream().map(entry ->
           new HeaderKeyValueImpl(entry.getKey(), String.join(", ", entry.getValue()))
        ).collect(Collectors.toList());
    }

    @Override
    public String get(String name) {
        if (!headers.containsKey(name)) {
            return null;
        }
        List<String> headerList = headers.get(name);
        return String.join(", ", headerList);
    }

    @Override
    public boolean has(String name) {
        return headers.containsKey(name);
    }

    @Override
    public Set<String> keys() {
        return headers.keySet();
    }

    @Override
    public void set(String name, String value) {
        headers.computeIfAbsent(name, key -> new ArrayList<>());
        headers.get(name).add(value);
    }

    @Override
    public Collection<String> values() {
        return headers.values().stream().map(headerList -> String.join(", ", headerList)).collect(Collectors.toList());
    }

    public static Map<String, String> getHeadersMap(HttpResponse httpResponse) {
        return httpResponse
                .getHeaders()
                .all()
                .stream()
                .collect(Collectors.toMap(Header::getName, Header::getValue));
    }
}
