// Copyright (c) Laserfiche
// Licensed under the MIT License. See LICENSE in the project root for license information.
package com.laserfiche.api.client.oauth;

import com.laserfiche.api.client.deserialization.ProblemDetailsDeserializer;
import com.laserfiche.api.client.model.AccessKey;
import com.laserfiche.api.client.model.ApiException;
import com.laserfiche.api.client.model.GetAccessTokenResponse;
import com.laserfiche.api.client.model.ProblemDetails;
import com.laserfiche.api.client.tokenclients.BaseTokenClient;
import kong.unirest.HttpResponse;
import kong.unirest.json.JSONObject;

import java.util.Map;

import static com.laserfiche.api.client.tokenclients.TokenClientUtils.*;

/**
 * The Laserfiche Cloud token route API client.
 */
public class TokenClientImpl extends BaseTokenClient implements TokenClient {
    private final String baseUrl;

    /**
     * Creates a new Laserfiche Cloud token route API client.
     * @param regionalDomain Laserfiche Cloud domain associated with the access key, e.g. laserfiche.com.
     */
    public TokenClientImpl(String regionalDomain) {
        super();
        baseUrl = getOAuthApiBaseUri(regionalDomain);
    }

    @Override
    public GetAccessTokenResponse getAccessTokenFromServicePrincipal(String servicePrincipalKey,
            AccessKey accessKey) {
        return getAccessTokenFromServicePrincipal(servicePrincipalKey, accessKey, null);
    }

    @Override
    public GetAccessTokenResponse getAccessTokenFromServicePrincipal(String servicePrincipalKey,
            AccessKey accessKey, String scope) {
        String bearer = createBearer(servicePrincipalKey, accessKey);
        HttpResponse<Object> httpResponse = httpClient
                .post(baseUrl + "Token")
                .header("Authorization", bearer)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("grant_type", "client_credentials")
                .field("scope", scope == null ? "" : scope)
                .asObject(Object.class);
        Map<String, String> headersMap = getHeadersMap(httpResponse);
        if (httpResponse.getStatus() == 200) {
            try {
                String jsonString = new JSONObject(httpResponse.getBody()).toString();
                return objectMapper.readValue(jsonString, GetAccessTokenResponse.class);
            } catch (Exception e) {
                throw ApiException.create(httpResponse.getStatus(), headersMap, null, e);
            }
        } else {
            ProblemDetails problemDetails;
            try {
                String jsonString = new JSONObject(httpResponse.getBody()).toString();
                problemDetails = ProblemDetailsDeserializer.deserialize(objectMapper, jsonString);
            } catch (Exception e) {
                throw ApiException.create(httpResponse.getStatus(), headersMap, null, e);
            }
            throw ApiException.create(httpResponse.getStatus(), headersMap, problemDetails, null);
        }
    }
}
