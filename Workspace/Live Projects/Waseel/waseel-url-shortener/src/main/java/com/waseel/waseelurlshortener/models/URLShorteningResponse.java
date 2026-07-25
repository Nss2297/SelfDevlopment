package com.waseel.waseelurlshortener.models;

public class URLShorteningResponse {

    private String shortLink;

    public URLShorteningResponse() {
    }

    public URLShorteningResponse(String shortLink) {
        this.shortLink = shortLink;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

}
