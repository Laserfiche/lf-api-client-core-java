package com.laserfiche.api.client.apiserver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.laserfiche.api.client.deserialization.OffsetDateTimeDeserializer;
import com.laserfiche.api.client.deserialization.TokenClientObjectMapper;
import com.laserfiche.api.client.httphandlers.HeadersImpl;
import com.laserfiche.api.client.model.ApiException;
import com.laserfiche.api.client.model.CreateConnectionRequest;
import com.laserfiche.api.client.model.ProblemDetails;
import com.laserfiche.api.client.model.SessionKeyInfo;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.threeten.bp.OffsetDateTime;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TokenClientImpl extends ApiServer implements TokenClient {
    private String baseUrl;

    public TokenClientImpl(String baseUrl) {
        super();
        this.baseUrl = baseUrl;
    }

    @Override
    public CompletableFuture<SessionKeyInfo> createAccessToken(String repoId, CreateConnectionRequest body) {
        Map<String, Object> pathParameters = getNonNullParameters(new String[]{"repoId"},
                new Object[]{repoId});
        return Unirest
                .post(baseUrl + "/v1/Repositories/{repoId}/Token")
                .routeParam(pathParameters)
                .header("Accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("grant_type", "password")
                .field("username", body
                        .getUsername())
                .field("password", body
                        .getPassword())
                .asObjectAsync(Object.class)
                .thenApply(httpResponse -> {
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
                });
    }

}
