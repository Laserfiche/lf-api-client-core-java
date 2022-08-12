package com.laserfiche.api.client.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jose.jwk.JWK;

import static com.laserfiche.api.client.oauth.OAuthUtil.decodeBase64;

public class AccessKey {
    public String customerId;
    public String domain;
    public String clientId;
    public JWK jwk;

    private static Gson gson = new GsonBuilder().registerTypeAdapter(JWK.class, new JwkDeserializer()).create();

    public static AccessKey CreateFromBase64EncodedAccessKey(String base64EncodedAccessKey) {
        String accessKeyStr = decodeBase64(base64EncodedAccessKey);
        accessKeyStr = accessKeyStr.replace("\\\"", "\"");
        AccessKey accessKey = gson.fromJson(accessKeyStr, AccessKey.class);
        return accessKey;
    }
}
