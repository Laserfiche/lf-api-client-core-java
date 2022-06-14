package com.laserfiche.api.client.integration;

import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.laserfiche.api.client.oauth.TokenClient;
import com.laserfiche.api.client.oauth.TokenClientImpl;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TokenClientImplTest extends BaseTest {
    @Test
    public void getAccessTokenFromServicePrincipal_Success() throws ExecutionException, InterruptedException {
        TokenClient client = new TokenClientImpl("ca");
        CompletableFuture<GetAccessTokenResponse> future = client.getAccessTokenFromServicePrincipal(spKey, accessKey);
        GetAccessTokenResponse response = future.get();

        assertNotEquals(null, response);
        assertNotEquals(null, response.getAccessToken());
    }
}
