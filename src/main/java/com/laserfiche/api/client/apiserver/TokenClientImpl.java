package com.laserfiche.api.client.apiserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.laserfiche.api.client.model.ApiException;
import com.laserfiche.api.client.model.CreateConnectionRequest;
import com.laserfiche.api.client.model.ProblemDetails;
import com.laserfiche.api.client.model.SessionKeyInfo;
import com.laserfiche.api.client.oauth.BaseTokenClient;
import kong.unirest.HttpResponse;
import kong.unirest.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.laserfiche.api.client.oauth.TokenClientUtils.getHeadersMap;

/**
 * The Laserfiche Self-Hosted token route API client.
 */
public class TokenClientImpl extends BaseTokenClient implements TokenClient {
    private final String baseUrl;

    /**
     * Creates a new Laserfiche Self-Hosted token route API client.
     * @param baseUrl APIServer Base Url e.g. https://{APIServerName}/LFRepositoryAPI.
     */
    public TokenClientImpl(String baseUrl) {
        super();
        this.baseUrl = baseUrl;
    }

    @Override
    public SessionKeyInfo createAccessToken(String repositoryId, CreateConnectionRequest body) {
        Map<String, Object> pathParameters = getNonNullParameters(new String[]{"repoId"},
                new Object[]{repositoryId});

        HttpResponse<Object> httpResponse = httpClient
                .post(baseUrl + "/v1/Repositories/{repoId}/Token")
                .routeParam(pathParameters)
                .header("Accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("grant_type", "password")
                .field("username", body
                        .getUsername())
                .field("password", body
                        .getPassword())
                .asObject(Object.class);

        if (httpResponse.getStatus() == 200) {
            try {
                String jsonString = new JSONObject(httpResponse.getBody()).toString();
                return objectMapper.readValue(jsonString, SessionKeyInfo.class);
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

    private Map<String, Object> getNonNullParameters(String[] parameterNames, Object[] parameters) {
        if (parameterNames == null || parameters == null) {
            throw new IllegalArgumentException("Input cannot be null.");
        }
        if (parameterNames.length != parameters.length) {
            throw new IllegalArgumentException("The array for parameter name and value should have the same length.");
        }
        Map<String, Object> paramKeyValuePairs = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i] != null) {
                paramKeyValuePairs.put(parameterNames[i],
                        parameters[i] instanceof String ? parameters[i] : String.valueOf(parameters[i]));
            }
        }
        return paramKeyValuePairs;
    }
}
