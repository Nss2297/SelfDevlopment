package com.waseel.prescription.model.url_shortening_service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShortenLinkRequest {

    private String link;
    @JsonProperty("isSecure")
    private boolean isSecure;
    private Long timeToLive;

    public ShortenLinkRequest() {
        this.isSecure = true;
    }

    public ShortenLinkRequest(String link, Long timeToLive) {
        this.link = link;
        this.isSecure = true;
        this.timeToLive = timeToLive;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean getIsSecure() {
        return isSecure;
    }

    public void setIsSecure(boolean isSecure) {
        this.isSecure = isSecure;
    }

    public Long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Long timeToLive) {
        this.timeToLive = timeToLive;
    }

}
