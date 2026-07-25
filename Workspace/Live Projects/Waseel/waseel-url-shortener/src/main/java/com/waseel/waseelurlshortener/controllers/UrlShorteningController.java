package com.waseel.waseelurlshortener.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waseel.waseelurlshortener.models.URLShorteningRequest;
import com.waseel.waseelurlshortener.models.URLShorteningResponse;
import com.waseel.waseelurlshortener.services.ShortUrlService;

@RestController
@RequestMapping("/links")
public class UrlShorteningController {

    @Autowired
    private ShortUrlService shortUrlService;

    @PostMapping()
    @PreAuthorize("@securityService.hasShortenUrlsRole(authentication)")
    public ResponseEntity<URLShorteningResponse> generateShortLink(
            @RequestBody @Valid URLShorteningRequest urlShorteningRequest) {
        return ResponseEntity.ok(this.shortUrlService.shorten(urlShorteningRequest));
    }

}
