package com.waseel.pbm.authentication.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.waseel.pbm.authentication.clients.models.SsoUser;

@FeignClient(name = "SsoClient", url = "${clients.sso.url}")
public interface SsoClient {

    @GetMapping("/validate")
    public ResponseEntity<SsoUser> validate(@RequestHeader(HttpHeaders.AUTHORIZATION) String bearerToken);

}
