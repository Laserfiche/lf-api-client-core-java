package com.laserfiche.api.client.apiserver;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.laserfiche.api.client.deserialization.OffsetDateTimeDeserializer;
import com.laserfiche.api.client.deserialization.TokenClientObjectMapper;
import kong.unirest.Header;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.threeten.bp.OffsetDateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiServer {
    protected ObjectMapper objectMapper;

    protected ApiServer(){
        Unirest
                .config()
                .setObjectMapper(new TokenClientObjectMapper());
        SimpleModule module = new SimpleModule();
        module.addDeserializer(OffsetDateTime.class, new OffsetDateTimeDeserializer());
        this.objectMapper = JsonMapper
                .builder()
                .addModule(module)
                .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .build();
    }

    protected static Map<String, String> getHeadersMap(HttpResponse httpResponse) {
        return httpResponse
                .getHeaders()
                .all()
                .stream()
                .collect(Collectors.toMap(Header::getName, Header::getValue));
    }

    protected Map<String, Object> getNonNullParameters(String[] parameterNames, Object[] parameters) {
        if (parameterNames == null || parameters == null) {
            throw new IllegalArgumentException("Input cannot be null.");
        }
        if (parameterNames.length != parameters.length) {
            throw new IllegalArgumentException("The array for parameter name and value should have the same length.");
        }
        Map<String, Object> paramKeyValuePairs = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] != null) {
                paramKeyValuePairs.put(parameterNames[i],
                        parameters[i] instanceof String ? parameters[i] : String.valueOf(parameters[i]));
            }
        }
        return paramKeyValuePairs;
    }
}
