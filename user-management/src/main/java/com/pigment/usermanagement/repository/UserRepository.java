package com.pigment.usermanagement.repository;

import com.pigment.usermanagement.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This repository interface defines methods to manage data for Users
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    public Optional<User> findUserByEmail(String email);
}
