package com.laserfiche.api.client.httphandlers;

public class BeforeSendResult {

    /**
     * @return Laserfiche Cloud regional domain.
     */
    public String getRegionalDomain() {
        return regionalDomain;
    }

    /**
     * @param regionalDomain Laserfiche Cloud regional domain.
     */
    public void setRegionalDomain(String regionalDomain) {
        this.regionalDomain = regionalDomain;
    }

    private String regionalDomain;
}
