package com.laserfiche.api.client.integration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laserfiche.api.client.model.AccessKey;
import com.nimbusds.jose.jwk.JWK;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;

import java.util.Base64;

public class BaseTest {
    protected static String spKey;
    protected static AccessKey accessKey;

    @BeforeAll
    public static void setUp() {
        // Load environment variables
        Dotenv dotenv = Dotenv
                .configure()
                .filename(".env")
                .load();

        // Read env variables
        Gson gson = new GsonBuilder().registerTypeAdapter(JWK.class, new JwkDeserializer()).create();

        String accessKeyBase64 = dotenv.get("ACCESS_KEY");
        String accessKeyStr = decodeBase64(accessKeyBase64);
        // Gson doesn't escape forward slash https://github.com/google/gson/issues/356
        accessKeyStr = accessKeyStr.replace("\\\"", "\"");

        accessKey = gson.fromJson(accessKeyStr, AccessKey.class);
        spKey = dotenv.get("SERVICE_PRINCIPAL_KEY");
    }

    private static String decodeBase64(String encoded) {
        byte[] decodedBytes = Base64.getDecoder().decode(encoded);
        return new String(decodedBytes);
    }
}
