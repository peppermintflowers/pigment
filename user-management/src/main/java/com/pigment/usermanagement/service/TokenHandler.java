package com.pigment.usermanagement.service;

import com.pigment.usermanagement.model.Role;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/** TOKENHANDLER
 * This service interface defines methods used to handle logic related to jwt tokens
 */
@Service
public interface TokenHandler {
    public String generateToken(UserDetails userDetails);
}
