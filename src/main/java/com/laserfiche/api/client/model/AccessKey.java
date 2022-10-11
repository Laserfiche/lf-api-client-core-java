package com.laserfiche.api.client.model;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.nimbusds.jose.jwk.JWK;

import java.io.IOException;

import static com.laserfiche.api.client.oauth.OAuthUtil.decodeBase64;

public class AccessKey {
    public String customerId;
    public String domain;
    public String clientId;
    public JWK jwk;

    private static ObjectMapper mapper;

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
            throw new RuntimeException(String.format("Invalid access key.%nDetail:%s", e));
        }
        return accessKey;
    }
}
