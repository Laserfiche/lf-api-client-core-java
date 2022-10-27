package com.laserfiche.api.client.integration;

import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.laserfiche.api.client.oauth.TokenClient;
import com.laserfiche.api.client.oauth.TokenClientImpl;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Cloud")
class TokenClientImplTest extends BaseTest {
    @Test
    void getAccessTokenFromServicePrincipal_Success() {
        TokenClient client = new TokenClientImpl(accessKey.getDomain());
        CompletableFuture<GetAccessTokenResponse> future = client.getAccessTokenFromServicePrincipal(spKey, accessKey);
        GetAccessTokenResponse response = future.join();

        assertNotEquals(null, response);
        assertNotEquals(null, response.getAccessToken());
    }

    @Test
    void getAccessTokenFromServicePrincipal_InvalidAccessKey() {
        TokenClient client = new TokenClientImpl(accessKey.getDomain());
        accessKey.setClientId("wrong client ID");
        CompletableFuture<GetAccessTokenResponse> future = client.getAccessTokenFromServicePrincipal(spKey, accessKey);

        Exception exception = assertThrows(RuntimeException.class, future::join);
        assertNotNull(exception);
    }

    @Test
    void getAccessTokenFromServicePrincipal_IoError() {
        String incorrectDomain = accessKey.getDomain().replace("laserfiche", "lf");
        TokenClient client = new TokenClientImpl(incorrectDomain);
        CompletableFuture<GetAccessTokenResponse> future = client.getAccessTokenFromServicePrincipal(spKey, accessKey);

        Exception exception = assertThrows(ExecutionException.class, future::get);
        assertTrue(IOException.class.isAssignableFrom(exception.getCause().getClass()));
    }
}
