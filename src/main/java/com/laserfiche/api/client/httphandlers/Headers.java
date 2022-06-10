package com.laserfiche.api.client.httphandlers;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Headers {
    void append(String name, String value);

    void delete(String name);

    Map.Entry<String, String> entries();

    String get(String name);

    boolean has(String name);

    Set<String> keys();

    void set(String name, String value);

    Collection<String> values();
}
