package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;

public interface TokenApiClient {

    /**
     * Gets an access token given the service principal key and the app access key.
     * These values can be exported from the Laserfiche Developer Console.
     * This is the client credentials flow that applies to service applications.
     * @param servicePrincipalKey
     * @param accessKey
     * @return GetAccessTokenResponse
     */
    GetAccessTokenResponse getAccessToken(String servicePrincipalKey, AccessKey accessKey);
}
