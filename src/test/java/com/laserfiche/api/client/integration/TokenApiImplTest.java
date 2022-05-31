package com.laserfiche.api.client.integration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.laserfiche.api.client.oauth.TokenApiClient;
import com.laserfiche.api.client.oauth.TokenApiImpl;
import com.nimbusds.jose.jwk.JWK;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TokenApiImplTest {
    private static String spKey;
    private static AccessKey accessKey;

    @BeforeAll
    public static void setUp() {
        // Load environment variables
        Dotenv dotenv = Dotenv
                .configure()
                .filename("TestConfig.env")
                .load();

        // Read env variables
        Gson gson = new GsonBuilder().registerTypeAdapter(JWK.class, new JwkDeserializer()).create();

        String accessKeyStr = dotenv.get("DEV_CA_PUBLIC_USE_INTEGRATION_TEST_ACCESS_KEY");
        if (accessKeyStr == null) {
            throw new RuntimeException("DEV_CA_PUBLIC_USE_INTEGRATION_TEST_ACCESS_KEY environment variable is not set");
        }
        // Gson doesn't escape forward slash https://github.com/google/gson/issues/356
        accessKeyStr = accessKeyStr.replace("\\\"", "\"");

        accessKey = gson.fromJson(accessKeyStr, AccessKey.class);
        spKey = dotenv.get("DEV_CA_PUBLIC_USE_TESTOAUTHSERVICEPRINCIPAL_SERVICE_PRINCIPAL_KEY");
    }

    @Test
    public void testGetAccessTokenAsync() {
        TokenApiClient client = new TokenApiImpl(accessKey.getDomain());
        CompletableFuture<GetAccessTokenResponse> respFuture = null;

        try {
            respFuture = client.getAccessTokenAsync(spKey, accessKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotEquals(respFuture, null);

        GetAccessTokenResponse resp = null;
        try {
            resp = respFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        assertNotEquals(resp, null);
        assertNotEquals(resp.getAccessToken(), null);
        assertNotEquals(resp.getAccessToken(), "");
        assertEquals(resp.getExpiresIn(), 3600);
    }

    @Test
    public void testGetAccessToken() {
        TokenApiClient client = new TokenApiImpl(accessKey.getDomain());
        GetAccessTokenResponse resp = null;

        try {
            resp = client.getAccessToken(spKey, accessKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotEquals(resp, null);

        assertNotEquals(resp.getAccessToken(), null);
        assertNotEquals(resp.getAccessToken(), "");
        assertEquals(resp.getExpiresIn(), 3600);
    }
}
