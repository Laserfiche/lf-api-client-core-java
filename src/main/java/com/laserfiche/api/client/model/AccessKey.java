package com.laserfiche.api.client.model;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.laserfiche.api.client.deserialization.JwkDeserializer;
import com.nimbusds.jose.jwk.JWK;

import java.io.IOException;

import static com.laserfiche.api.client.oauth.OAuthUtil.decodeBase64;

public class AccessKey {
    private String customerId;
    private String domain;
    private String clientId;
    private JWK jwk;

    private static ObjectMapper mapper;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public JWK getJwk() {
        return jwk;
    }

    public void setJwk(JWK jwk) {
        this.jwk = jwk;
    }

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
