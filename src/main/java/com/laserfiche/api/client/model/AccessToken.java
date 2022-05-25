package com.laserfiche.api.client.model;

import com.nimbusds.jose.jwk.JWK;

public class AccessToken {
    private String customerId;
    private String domain;
    private String clientId;
    private JWK jwk;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public JWK getJwk() {
        return jwk;
    }

    public void setJwk(JWK jwk) {
        this.jwk = jwk;
    }
}
