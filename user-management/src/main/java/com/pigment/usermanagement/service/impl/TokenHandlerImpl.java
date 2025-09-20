package com.pigment.usermanagement.service.impl;

import com.pigment.usermanagement.service.TokenHandler;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

/**
 * TOKENHANDLERIMPL
 * This service handles logic related to jwt tokens
 */
@Service
public class TokenHandlerImpl implements TokenHandler {
    private final JwtEncoder jwtEncoder;

    public TokenHandlerImpl(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    /**
     * Used to generate jwt token
     * @param userDetails
     * @return encoded string with jwt details
     */
    @Override
    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("user-management")
                .issuedAt(now)
                .expiresAt(now.plus(Duration.ofMinutes(30)))
                .subject(userDetails.getUsername())
                .build();

        JwsHeader header = JwsHeader.with(SignatureAlgorithm.RS256)
                .build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();
    }
}
