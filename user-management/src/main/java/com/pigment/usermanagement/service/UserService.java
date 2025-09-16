package com.pigment.usermanagement.service;

import com.pigment.usermanagement.model.User;
import org.springframework.stereotype.Service;

import java.util.Optional;
/** USERSERVICE
 * This service interface defines methods used to handle business logic related to User - like registering users.
 */
@Service
public interface UserService {
    public Optional<User> registerUser(User user);
}
