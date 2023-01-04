package com.laserfiche.api.client.oauth;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.laserfiche.api.client.deserialization.OffsetDateTimeDeserializer;
import com.laserfiche.api.client.deserialization.TokenClientObjectMapper;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;
import org.threeten.bp.OffsetDateTime;

public abstract class OAuthClient implements AutoCloseable {
    protected ObjectMapper objectMapper;
    protected final UnirestInstance httpClient;

    protected OAuthClient() {
        httpClient = Unirest.spawnInstance();
        httpClient
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

    @Override
    public void close() {
        httpClient.close();
    }
}
