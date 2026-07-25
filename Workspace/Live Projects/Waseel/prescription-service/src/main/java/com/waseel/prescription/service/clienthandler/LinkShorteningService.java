package com.waseel.prescription.service.clienthandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.waseel.prescription.clients.SsoClient;
import com.waseel.prescription.clients.UrlShorteningClient;
import com.waseel.prescription.model.authentication.JwtResponse;
import com.waseel.prescription.model.authentication.SsoTokenRequest;
import com.waseel.prescription.model.url_shortening_service.ShortenLinkRequest;
import com.waseel.prescription.model.url_shortening_service.ShortenLinkResponse;

@Service
public class LinkShorteningService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkShorteningService.class);

    @Autowired
    private SsoClient ssoClient;

    @Autowired
    private UrlShorteningClient urlShorteningClient;

    @Value("${sso.client_id}")
    private String clientId;

    @Value("${sso.client_secret}")
    private String clientSecret;

    public String shortenLink(String link, Long timeToLiveInSeconds) {

        SsoTokenRequest ssoTokenRequest = new SsoTokenRequest(clientId, clientSecret);
        JwtResponse jwtResponse = null;
        try {
            LOGGER.info("getting access token before shortening patient link...");
            jwtResponse = ssoClient.fetchAccessToken(ssoTokenRequest);
        } catch (Exception e) {
            LOGGER.error("Could not get sso access token to generate short link", e);
        }
        if (jwtResponse != null) {
            ShortenLinkRequest shortenLinkRequest = new ShortenLinkRequest(link, timeToLiveInSeconds);

            try {
                LOGGER.info("sending link shortening request...");
                ShortenLinkResponse response = urlShorteningClient.shortenLink("Bearer " + jwtResponse.getAccessToken(),
                        shortenLinkRequest);
                LOGGER.info("link was shortened successfully...");
                return response.getShortLink();
            } catch (Exception e) {
                LOGGER.error("Could not shorten link for patient", e);
            }
        }
        return "";
    }

}
