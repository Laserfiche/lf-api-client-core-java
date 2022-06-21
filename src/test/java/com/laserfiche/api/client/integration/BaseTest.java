package com.laserfiche.api.client.integration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laserfiche.api.client.model.AccessKey;
import com.nimbusds.jose.jwk.JWK;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.internal.matchers.Null;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

public class BaseTest {
    protected static String spKey;
    protected static AccessKey accessKey;

    @BeforeAll
    public static void setUp() throws DotenvException, NullPointerException {
        // Load environment variables
        try {
            Dotenv dotenv = Dotenv
                    .configure()
                    .filename("TestConfig.env").load();
            // Read env variables
            Gson gson = new GsonBuilder().registerTypeAdapter(JWK.class, new JwkDeserializer()).create();
            String accessKeyBase64 = dotenv.get("ACCESS_KEY");
            //System.out.println(accessKeyBase64);
            String accessKeyStr = decodeBase64(accessKeyBase64);
            // Gson doesn't escape forward slash https://github.com/google/gson/issues/356
            accessKeyStr = accessKeyStr.replace("\\\"", "\"");
            accessKey = gson.fromJson(accessKeyStr, AccessKey.class);
            spKey = dotenv.get("SERVICE_PRINCIPAL_KEY");
        } catch (DotenvException | NullPointerException e) {
            if (e instanceof DotenvException) {
                System.out.println("File not found");
            }
            if (e instanceof NullPointerException) {
                System.out.println("Environment variables are not loaded properly from the Testconfig.env file");
            } else {
                throw new RuntimeException("uncaught", e);
            }
        }
    }

    private static String decodeBase64(String encoded) {
        byte[] decodedBytes = Base64.getDecoder().decode(encoded);
        return new String(decodedBytes);
    }
}
