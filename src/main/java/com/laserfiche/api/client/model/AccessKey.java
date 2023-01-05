package com.laserfiche.api.client.model;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.laserfiche.api.client.deserialization.JwkDeserializer;
import com.nimbusds.jose.jwk.JWK;

import java.io.IOException;

import static com.laserfiche.api.client.tokenclients.TokenClientUtils.decodeBase64;

/**
 * The access key exported from the Laserfiche Developer Console.
 */
public class AccessKey {
    private String customerId;
    private String domain;
    private String clientId;
    private JWK jwk;

    private static ObjectMapper mapper;

    /**
     * Returns the Laserfiche customer id the app is registered in.
     */
    public String getCustomerId() {
        return customerId;
    }

    /**
     * Sets the Laserfiche customer id the app is registered in.
     */
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    /**
     * Returns the Laserfiche domain the app belongs to, e.g. laserfiche.com.
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the Laserfiche domain the app belongs to, e.g. laserfiche.com.
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * Returns the app's client id.
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * Sets the app's client id.
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    /**
     * Returns the app's json web key.
     */
    public JWK getJwk() {
        return jwk;
    }

    /**
     * Sets the app's json web key.
     */
    public void setJwk(JWK jwk) {
        this.jwk = jwk;
    }

    /**
     * Creates an AccessKey given a base-64 encoded access key.
     * @param base64EncodedAccessKey The base-64 encoded access key exported from the Laserfiche Developer Console.
     * @return {@link AccessKey}
     */
    public static AccessKey createFromBase64EncodedAccessKey(String base64EncodedAccessKey) {
        if (mapper == null) {
            SimpleModule module = new SimpleModule();
            module.addDeserializer(JWK.class, new JwkDeserializer());

            mapper = JsonMapper
                    .builder()
                    .disable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS)
                    .addModule(module)
                    .build();
        }
        base64EncodedAccessKey = base64EncodedAccessKey.trim();
        if (base64EncodedAccessKey.length() == 0) {
            throw new IllegalArgumentException("Input cannot be empty or null");
        }
        String accessKeyStr = decodeBase64(base64EncodedAccessKey);
        AccessKey accessKey = null;
        try {
            accessKey = mapper.readValue(accessKeyStr, AccessKey.class);
        } catch (IOException e) {
            throw new RuntimeException("Invalid access key. Detail: " + e, e);
        }
        return accessKey;
    }
}
