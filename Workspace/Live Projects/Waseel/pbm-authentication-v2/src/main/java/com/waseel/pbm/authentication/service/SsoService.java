package com.waseel.pbm.authentication.service;

import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.waseel.pbm.authentication.clients.SsoClient;
import com.waseel.pbm.authentication.clients.models.SsoUser;
import com.waseel.pbm.authentication.model.JwtResponse;

import feign.FeignException.FeignClientException;
import feign.FeignException.FeignServerException;

@Service
public class SsoService {

    @Autowired
    private SsoClient ssoClient;

    @Autowired
    private JwtService jwtService;

    public JwtResponse verifyAndSignIn(String bearerToken, HttpServletResponse response)
            throws NotFoundException, InterruptedException, ExecutionException {

        try {
            ResponseEntity<SsoUser> validateResponse = this.ssoClient.validate(bearerToken);
            SsoUser ssoUser = validateResponse.getBody();
            return jwtService.signInUser(ssoUser.getPreferred_username(), response);
        } catch (FeignClientException | FeignServerException ex) {
            throw new ResponseStatusException(HttpStatus.valueOf(ex.status()), ex.getMessage());
        }

    }

}
