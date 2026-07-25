package com.waseel.waseelurlshortener.services;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import com.google.common.hash.Hashing;
import com.waseel.waseelurlshortener.models.URLShorteningRequest;
import com.waseel.waseelurlshortener.models.URLShorteningResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;

@Service
public class ShortUrlService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${generic-base-url}")
    private String genericBaseURL;
    @Value("${e-prescirption-base-url}")
    private String ePrescriptionBaseURL;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public URLShorteningResponse shorten(@Valid URLShorteningRequest urlShorteningRequest) {
        String key = "";
        int secureHashBitsSize = 128;
        int insecureHashBitsSize = 32;
        int numberOfTries = 0;
        do {
            if (numberOfTries > 10) {
                log.warn("Couldn't find unique hash using {} bits length. is secure? [{}]",
                        urlShorteningRequest.getIsSecure() ? secureHashBitsSize : insecureHashBitsSize,
                        urlShorteningRequest.getIsSecure());
                secureHashBitsSize++;
                insecureHashBitsSize++;
                numberOfTries = 0;
            }
            key = Hashing.goodFastHash(urlShorteningRequest.getIsSecure() ? secureHashBitsSize : insecureHashBitsSize)
                    .hashString(urlShorteningRequest.getLink() + UUID.randomUUID().toString(), StandardCharsets.UTF_8)
                    .toString();
            numberOfTries++;
        } while (redisTemplate.opsForValue().get(key) != null);

        if (urlShorteningRequest.getTimeToLive() > 0) {
            redisTemplate.opsForValue().set(key, urlShorteningRequest.getLink(), urlShorteningRequest.getTimeToLive(),
                    TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, urlShorteningRequest.getLink());
        }

        return new URLShorteningResponse(this.getBaseUrl() + "/" + key);
    }

    public String getLongLink(String key) {
        Optional<String> url = Optional.ofNullable(redisTemplate.opsForValue().get(key));
        if (url.isPresent()) {
            return url.get();
        }
        throw new NotFoundException("Link was not found.");
    }

    private String getBaseUrl() {
        BearerTokenAuthentication authentication = ((BearerTokenAuthentication) SecurityContextHolder.getContext()
                .getAuthentication());
        if (authentication.getTokenAttributes().get("preferred_username") != null
                && authentication.getTokenAttributes().get("preferred_username") instanceof String) {
            String username = (String) authentication.getTokenAttributes().get("preferred_username");

            if (username.equals("service-account-pbm-prescription-service")) {
                return this.ePrescriptionBaseURL;
            }
        }
        return this.genericBaseURL;
    }

}
