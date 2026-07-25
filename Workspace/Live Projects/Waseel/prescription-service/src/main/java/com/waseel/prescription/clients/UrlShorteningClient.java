package com.waseel.prescription.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.waseel.prescription.model.url_shortening_service.ShortenLinkRequest;
import com.waseel.prescription.model.url_shortening_service.ShortenLinkResponse;

@FeignClient(name = "UrlShorteningClient", url = "${url-shortening-service.url}")
public interface UrlShorteningClient {
    

    @PostMapping("/links")
    public ShortenLinkResponse shortenLink(@RequestHeader(name = "Authorization") String authorizationHeader, @RequestBody ShortenLinkRequest request);
}
