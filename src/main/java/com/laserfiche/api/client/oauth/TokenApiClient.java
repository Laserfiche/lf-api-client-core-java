package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;

import java.util.concurrent.CompletableFuture;

public interface TokenApiClient {
    /**
     * Gets an access token given the service principal key and the app access key asynchronously.
     * These values can be exported from the Laserfiche Developer Console.
     * This is the client credentials flow that applies to service applications.
     * @param servicePrincipalKey
     * @param accessKey
     * @return CompletableFuture<GetAccessTokenResponse>
     */
    // TODO: a bunch of renaming https://github.com/Laserfiche/lf-api-client-core-js/blob/1.x/lib/OAuth/TokenClient.ts#L13
    // TODO: server returns a status code (not 200), the api should throw an api exception in this case
    // TODO: service is unreachable (like network down), throws a non-apiexception
    CompletableFuture<GetAccessTokenResponse> getAccessTokenAsync(String servicePrincipalKey, AccessKey accessKey) throws ApiException;
}
