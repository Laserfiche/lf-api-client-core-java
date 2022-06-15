package com.laserfiche.api.client.integration;

import com.laserfiche.api.client.httphandlers.*;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OAuthClientCredentialsHandlerTest extends BaseTest {
    @Test
    public void beforeSendAsync_Success() throws ExecutionException, InterruptedException {
        HttpRequestHandler handler = new OAuthClientCredentialsHandler(spKey, accessKey);
        Request request = new RequestImpl();

        CompletableFuture<BeforeSendResult> future = handler.beforeSendAsync(request);
        BeforeSendResult result = future.get();

        assertNotEquals(null, result.getRegionalDomain());
        assertNotEquals(null, request.headers().get("Authorization"));
        assertNotEquals("", request.headers().get("Authorization"));
    }
}
