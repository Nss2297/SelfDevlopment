package com.waseel.waseelurlshortener.services;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public boolean hasShortenUrlsRole(Authentication authentication) {
        return ((Map<String, ArrayList<String>>) ((BearerTokenAuthentication) authentication).getTokenAttributes()
                .get("realm_access")).get("roles").contains("shorten-urls");
    }
}
