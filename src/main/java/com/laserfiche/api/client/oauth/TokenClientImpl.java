package com.laserfiche.api.client.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
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
        String bearer = createBearer(servicePrincipalKey, accessKey);
        HttpResponse<Object> httpResponse = httpClient
                .post(baseUrl + "Token")
                .header("Authorization", bearer)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("grant_type", "client_credentials")
                .asObject(Object.class);

        if (httpResponse.getStatus() == 200) {
            try {
                String jsonString = new JSONObject(httpResponse.getBody()).toString();
                return objectMapper.readValue(jsonString, GetAccessTokenResponse.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            ProblemDetails problemDetails;
            try {
                String jsonString = new JSONObject(httpResponse.getBody()).toString();
                problemDetails = objectMapper.readValue(jsonString, ProblemDetails.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
            Map<String, String> headersMap = getHeadersMap(httpResponse);
            if (httpResponse.getStatus() == 400)
                throw new ApiException("Invalid or bad request.", httpResponse.getStatus(),
                        httpResponse.getStatusText(), headersMap, problemDetails);
            else if (httpResponse.getStatus() == 401)
                throw new ApiException("Access token is invalid or expired.", httpResponse.getStatus(),
                        httpResponse.getStatusText(), headersMap, problemDetails);
            else if (httpResponse.getStatus() == 403)
                throw new ApiException("Access denied for the operation.", httpResponse.getStatus(),
                        httpResponse.getStatusText(), headersMap, problemDetails);
            else if (httpResponse.getStatus() == 404)
                throw new ApiException("Not found.", httpResponse.getStatus(), httpResponse.getStatusText(),
                        headersMap, problemDetails);
            else if (httpResponse.getStatus() == 429)
                throw new ApiException("Rate limit is reached.", httpResponse.getStatus(),
                        httpResponse.getStatusText(), headersMap, problemDetails);
            else
                throw new RuntimeException(httpResponse.getStatusText());
        }
    }
}
