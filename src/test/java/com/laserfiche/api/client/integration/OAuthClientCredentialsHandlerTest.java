package com.laserfiche.api.client.integration;

import com.laserfiche.api.client.httphandlers.BeforeSendResult;
import com.laserfiche.api.client.httphandlers.HttpRequestHandler;
import com.laserfiche.api.client.httphandlers.OAuthClientCredentialsHandler;
import com.squareup.okhttp.Request;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class OAuthClientCredentialsHandlerTest extends BaseTest {
    public void beforeSendAsync_Success() throws ExecutionException, InterruptedException {
        HttpRequestHandler handler = new OAuthClientCredentialsHandler(spKey, accessKey);
        Request.Builder request = new Request.Builder();
        CompletableFuture<BeforeSendResult> future = handler.beforeSendAsync(request);
        BeforeSendResult result = future.get();

        assertNotEquals(null, result);
        assertNotEquals(null, result.getRegionalDomain());
    }
}
