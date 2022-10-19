package com.laserfiche.api.client.httphandlers;

import com.laserfiche.api.client.model.CreateConnectionRequest;

import java.util.concurrent.CompletableFuture;

public class UsernamePasswordHandler implements HttpRequestHandler {
    private String accessToken;
    private final String GRANTTYPE = "password";
    private String repoId;
    private String username;
    private String password;
    private String baseUrl;

    private CreateConnectionRequest request;

    public UsernamePasswordHandler(String repoId, String username, String password, String baseUrl){
        this.username = username;
        this.password = password;
        this.baseUrl = baseUrl;
        this.repoId = repoId;
        request = new CreateConnectionRequest();
        request.setPassword(this.password);
        request.setUsername(this.username);
        request.setGrantType(GRANTTYPE);
    }
    @Override
    public CompletableFuture<BeforeSendResult> beforeSendAsync(Request request) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> afterSendAsync(Response response) {
        return null;
    }
}
