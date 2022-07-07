package com.laserfiche.api.client.integration;

import com.laserfiche.api.client.httphandlers.*;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OAuthClientCredentialsHandlerTest extends BaseTest {
    @Test
    void beforeSendAsync_Success() throws ExecutionException, InterruptedException {
        HttpRequestHandler handler = new OAuthClientCredentialsHandler(spKey, accessKey);
        Request request = new RequestImpl();

        // Request access token
        CompletableFuture<BeforeSendResult> future = handler.beforeSendAsync(request);
        BeforeSendResult result = future.get();

        assertNotEquals(null, result.getRegionalDomain());
        assertNotEquals(null, request.headers().get("Authorization"));
        assertNotEquals("", request.headers().get("Authorization"));
    }

    @Test
    void beforeSendAsync_CallTwiceShouldStillSucceed() throws ExecutionException, InterruptedException {
        HttpRequestHandler handler = new OAuthClientCredentialsHandler(spKey, accessKey);
        Request request1 = new RequestImpl();

        // First time to request access token
        CompletableFuture<BeforeSendResult> future1 = handler.beforeSendAsync(request1);
        BeforeSendResult result1 = future1.get();

        // First request should work
        assertNotEquals(null, result1.getRegionalDomain());
        assertNotEquals(null, request1.headers().get("Authorization"));
        assertNotEquals("", request1.headers().get("Authorization"));

        // Subsequent request should also work
        Request request2 = new RequestImpl();
        CompletableFuture<BeforeSendResult> future2 = handler.beforeSendAsync(request2);
        BeforeSendResult result2 = future2.get();

        assertNotEquals(null, result2.getRegionalDomain());
        assertNotEquals(null, request2.headers().get("Authorization"));
        assertNotEquals("", request2.headers().get("Authorization"));
        assertEquals(request1.headers().get("Authorization"), request2.headers().get("Authorization"));
    }

    @Test
    void afterSendAsync_ShouldRetry() {
        HttpRequestHandler handler = new OAuthClientCredentialsHandler(spKey, accessKey);
        Response mockedResponse = mock(Response.class);
        when(mockedResponse.status()).thenReturn((short)401);

        // Request access token then simulate a 401
        CompletableFuture<BeforeSendResult> tokenFuture = handler.beforeSendAsync(new RequestImpl());
        tokenFuture.thenCompose(beforeSendResult -> handler.afterSendAsync(mockedResponse)).thenApply((shouldRetry) -> {
            assertEquals(true, shouldRetry);
            return null;
        });
    }
}
