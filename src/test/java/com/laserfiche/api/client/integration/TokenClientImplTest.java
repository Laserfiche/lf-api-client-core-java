package com.laserfiche.api.client.integration;

import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.laserfiche.api.client.oauth.TokenClient;
import com.laserfiche.api.client.oauth.TokenClientImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Cloud")
class TokenClientImplTest extends BaseTest {
    private TokenClient client;

    @BeforeEach
    void setUpHttpRequestHandler() {
        client = new TokenClientImpl(accessKey.getDomain());
    }

    @AfterEach
    void tearDownHttpRequestHandler() {
        client.close();
    }

    @Test
    void getAccessTokenFromServicePrincipal_Success() {
        CompletableFuture<GetAccessTokenResponse> future = client.getAccessTokenFromServicePrincipal(spKey, accessKey);
        GetAccessTokenResponse response = future.join();

        assertNotEquals(null, response);
        assertNotEquals(null, response.getAccessToken());
    }

    @Test
    void getAccessTokenFromServicePrincipal_InvalidAccessKey() {
        accessKey.setClientId("wrong client ID");
        CompletableFuture<GetAccessTokenResponse> future = client.getAccessTokenFromServicePrincipal(spKey, accessKey);

        Exception exception = assertThrows(RuntimeException.class, future::join);
        assertNotNull(exception);
    }

    @Test
    void getAccessTokenFromServicePrincipal_IoError() {
        String incorrectDomain = accessKey.getDomain().replace("laserfiche", "lf");

        try (TokenClient client = new TokenClientImpl(incorrectDomain)) {
            CompletableFuture<GetAccessTokenResponse> future = client.getAccessTokenFromServicePrincipal(spKey, accessKey);

            Exception exception = assertThrows(ExecutionException.class, future::get);
            assertTrue(IOException.class.isAssignableFrom(exception.getCause().getClass()));
        }
    }
}
