package com.waseel.prescription.model.url_shortening_service;

public class ShortenLinkResponse {

    private String shortLink;

    public ShortenLinkResponse() {
    }

    public ShortenLinkResponse(String shortLink) {
        this.shortLink = shortLink;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

}
