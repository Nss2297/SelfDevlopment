package com.waseel.emailservice.service.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public boolean hasEmailScope(Authentication authentication) {

        

        return false;
    }
}
