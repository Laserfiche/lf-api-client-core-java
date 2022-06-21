package com.laserfiche.api.client.integration;

import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.laserfiche.api.client.oauth.TokenClient;
import com.laserfiche.api.client.oauth.TokenClientImpl;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class TokenClientImplTest extends BaseTest {
    @Test
    public void getAccessTokenFromServicePrincipal_Success() throws ExecutionException, InterruptedException {
        TokenClient client = new TokenClientImpl(accessKey.getDomain());
        CompletableFuture<GetAccessTokenResponse> future = client.getAccessTokenFromServicePrincipal(spKey, accessKey);
        GetAccessTokenResponse response = future.get();

        assertNotEquals(null, response);
        assertNotEquals(null, response.getAccessToken());
    }

    @Test
    public void getAccessTokenFromServicePrincipal_IoError() throws ExecutionException, InterruptedException {
        String incorrectDomain = accessKey.getDomain().replace("laserfiche", "lf");
        TokenClient client = new TokenClientImpl(incorrectDomain);
        CompletableFuture<GetAccessTokenResponse> future = client.getAccessTokenFromServicePrincipal(spKey, accessKey);

        Exception exception = assertThrows(ExecutionException.class, future::get);

        assertTrue(IOException.class.isAssignableFrom(exception.getCause().getCause().getClass()));
    }

    /*@Test
    public void getAccessTokenFromCode_400Error() throws ExecutionException, InterruptedException {
        TokenClient client = new TokenClientImpl(accessKey.getDomain());
        CompletableFuture<GetAccessTokenResponse> future = client.getAccessTokenFromCode(null, null, null, null, null);
        GetAccessTokenResponse response = future.get();
        System.out.println(response);
    }*/
}
