package com.laserfiche.api.client.model;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public static AccessKey createFromBase64EncodedAccessKey(String base64EncodedAccessKey) throws IOException {
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.configure(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS, false);

            SimpleModule module = new SimpleModule();
            module.addDeserializer(JWK.class, new JwkDeserializer());

            mapper.registerModule(module);
        }
        base64EncodedAccessKey = base64EncodedAccessKey.trim();
        if (base64EncodedAccessKey.length() == 0){
            throw new IOException("Input cannot be empty or null");
        }
        String accessKeyStr = decodeBase64(base64EncodedAccessKey);
        AccessKey accessKey = null;
        try {
            accessKey = mapper.readValue(accessKeyStr, AccessKey.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return accessKey;
    }
}
