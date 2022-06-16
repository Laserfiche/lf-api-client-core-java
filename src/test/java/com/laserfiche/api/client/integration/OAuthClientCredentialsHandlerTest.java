package com.laserfiche.api.client.integration;

import com.laserfiche.api.client.httphandlers.*;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OAuthClientCredentialsHandlerTest extends BaseTest {
    @Test
    public void beforeSendAsync_Success() throws ExecutionException, InterruptedException {
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
    public void afterSendAsync_ShouldRetry() {
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
