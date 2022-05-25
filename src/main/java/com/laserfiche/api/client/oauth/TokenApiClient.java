package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.GetAccessTokenResponse;

public interface TokenApiClient {
    GetAccessTokenResponse getAccessToken(String servicePrincipalKey, AccessKey accessKey) {

    }
}
