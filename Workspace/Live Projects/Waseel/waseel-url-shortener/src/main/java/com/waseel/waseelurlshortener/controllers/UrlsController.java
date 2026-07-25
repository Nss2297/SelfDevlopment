package com.waseel.waseelurlshortener.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.waseel.waseelurlshortener.services.ShortUrlService;

@RestController
public class UrlsController {

    @Autowired
    private ShortUrlService shortUrlService;

    @GetMapping("/{key}")
    public ResponseEntity<Void> redirectToLongLink(@PathVariable String key) throws URISyntaxException {
        URI uri = new URI(shortUrlService.getLongLink(key));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.PERMANENT_REDIRECT);
    }

}