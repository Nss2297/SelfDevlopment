package com.waseel.waseelurlshortener.models;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;

import com.fasterxml.jackson.annotation.JsonAlias;

public class URLShorteningRequest {

    @URL
    @NotBlank
    @JsonAlias({ "link" })
    private String link;

    @JsonAlias({ "isSecure", "secured" })
    private Boolean isSecure = false;

    @JsonAlias({ "timeToLive", "ttl", "TTL" })
    private Long timeToLive = -1l;

    public URLShorteningRequest() {
    }

    public URLShorteningRequest(String link, Boolean isSecure, Long timeToLive) {
        this.link = link;
        this.isSecure = isSecure;
        this.timeToLive = timeToLive;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getIsSecure() {
        return this.isSecure;
    }

    public void setIsSecure(Boolean isSecure) {
        this.isSecure = isSecure;
    }

    public Long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(Long timeToLive) {
        this.timeToLive = timeToLive;
    }

}
