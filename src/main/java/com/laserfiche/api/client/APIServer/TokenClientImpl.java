package com.laserfiche.api.client.APIServer;

import com.laserfiche.api.client.model.CreateConnectionRequest;
import com.laserfiche.api.client.oauth.TokenClientObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class TokenClientImpl implements TokenClient {
    private String baseUrl;

    public TokenClientImpl(String baseUrl) {
        this.baseUrl = baseUrl;
        Unirest
                .config()
                .setObjectMapper(new TokenClientObjectMapper());
    }

    @Override
    public CompletableFuture<SessionKeyInfo> createAccessToken(String repoId, CreateConnectionRequest body) {
        Map<String, Object> pathParameters = getNonNullParameters(new String[]{"repoId"},
                new Object[]{repoId});
        CompletableFuture<HttpResponse<SessionKeyInfo>> future = Unirest
                .post(baseUrl + "/v1/Repositories/{repoId}/Token")
                .routeParam(pathParameters)
                .header("Accept", "application/json")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .field("grant_type", "password")
                .field("username", body
                        .getUsername()
                        .toString())
                .field("password", body
                        .getPassword()
                        .toString())
                .asObjectAsync(SessionKeyInfo.class);
        return future.thenApply(httpResponse -> {
            if (httpResponse.getStatus() != 200) {
                throw new RuntimeException(httpResponse.getStatusText());
            }
            return httpResponse.getBody();
        });
    }

    protected Map<String, Object> getNonNullParameters(String[] parameterNames, Object[] parameters) {
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
