package com.laserfiche.api.client.integration;

import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.laserfiche.api.client.oauth.TokenApiClient;
import com.laserfiche.api.client.oauth.TokenApiImpl;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TokenApiImplTest extends BaseTest {
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

        assertNotEquals(null, resp);
        assertNotEquals(null, resp.getAccessToken());
        assertNotEquals("", resp.getAccessToken());
        assertEquals(43200, resp.getExpiresIn());
    }
}
