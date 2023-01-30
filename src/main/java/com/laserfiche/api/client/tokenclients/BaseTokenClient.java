package com.laserfiche.api.client.tokenclients;

import com.laserfiche.api.client.deserialization.TokenClientObjectMapper;
import kong.unirest.ObjectMapper;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;

public abstract class BaseTokenClient implements AutoCloseable {
    protected ObjectMapper objectMapper;
    protected final UnirestInstance httpClient;

    protected BaseTokenClient() {
        objectMapper = new TokenClientObjectMapper();
        httpClient = Unirest.spawnInstance();
        httpClient
                .config()
                .setObjectMapper(objectMapper);
    }

    @Override
    public void close() {
        httpClient.close();
    }
}
