package com.laserfiche.api.client.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jose.jwk.JWK;

import java.util.Base64;

public class AccessKey {
    private String customerId;
    private String domain;
    private String clientId;
    private JWK jwk;

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

    public static AccessKey CreateFromBase64EncodedAccessKey(String base64EncodedAccessKey) {
        String accessKeyStr = decodeBase64(base64EncodedAccessKey);
        accessKeyStr = accessKeyStr.replace("\\\"", "\"");
        Gson gson = new GsonBuilder().registerTypeAdapter(JWK.class, new JwkDeserializer()).create();
        AccessKey accessKey = gson.fromJson(accessKeyStr, AccessKey.class);
        return accessKey;
    }

    private static String decodeBase64(String encoded) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encoded);
        return new String(decodedBytes);
    }
}
