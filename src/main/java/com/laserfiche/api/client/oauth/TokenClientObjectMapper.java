package com.laserfiche.api.client.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import kong.unirest.GenericType;
import kong.unirest.ObjectMapper;

import java.io.IOException;

public class TokenClientObjectMapper implements ObjectMapper {
    private com.fasterxml.jackson.databind.ObjectMapper jacksonMapper;

    public TokenClientObjectMapper() {
        jacksonMapper = JsonMapper
                .builder()
                .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();
    }

    @Override
    public <T> T readValue(String s, Class<T> aClass) {
        try {
            return jacksonMapper.readValue(s, aClass);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T readValue(String value, GenericType<T> genericType) {
        return null;
    }

    @Override
    public String writeValue(Object o) {
        try {
            return jacksonMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
