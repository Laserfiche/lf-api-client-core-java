package com.laserfiche.api.client.integration;

import com.laserfiche.api.client.JSON;
import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.laserfiche.api.client.oauth.TokenApiClient;
import com.laserfiche.api.client.oauth.TokenApiImpl;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TokenApiImplTest {
    private static Dotenv dotenv;
    private static String spKey;
    private static AccessKey accessKey;

    @BeforeAll
    public static void setUp() {
        // Load environment variables
        Dotenv dotenv = Dotenv
                .configure()
                .filename("TestConfig.env")
                .load();

        JSON json = new JSON();
        accessKey = json.deserialize(dotenv.get("DEV_CA_PUBLIC_USE_TESTOAUTHSERVICEPRINCIPAL_ACCESS_KEY"), AccessKey.class);
        spKey = dotenv.get("DEV_CA_PUBLIC_USE_TESTOAUTHSERVICEPRINCIPAL_SERVICE_PRINCIPAL_KEY");
    }

    @Test
    public void testGetTokenAsync() {
        TokenApiClient client = new TokenApiImpl();
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
    public void testGetToken() {
        TokenApiClient client = new TokenApiImpl();
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
