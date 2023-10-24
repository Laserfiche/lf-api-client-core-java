// Copyright (c) Laserfiche
// Licensed under the MIT License. See LICENSE in the project root for license information.
package com.laserfiche.api.client.httphandlers;

/**
 * Contains information about the HTTP request before it is sent.
 */
public class BeforeSendResult {

    /**
     * Returns the Laserfiche Cloud regional domain, e.g. laserfiche.com.
     */
    public String getRegionalDomain() {
        return regionalDomain;
    }

    /**
     * Sets the Laserfiche Cloud regional domain, e.g. laserfiche.com.
     */
    public void setRegionalDomain(String regionalDomain) {
        this.regionalDomain = regionalDomain;
    }

    private String regionalDomain;
}
