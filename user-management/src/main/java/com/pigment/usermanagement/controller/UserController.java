package com.pigment.usermanagement.controller;

import com.pigment.usermanagement.model.User;
import com.pigment.usermanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
/** USERCONTROLLER
 * This controller class handles CRUD operations related to the User.
 * */
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class UserController {

    private final UserService userService;

    /**
     * postUser handles POST operations for a User with Customer role
     * @param user : The data model containing the details of the new user to be registered
     * @return: ResponseEntity indicating whether User was created or was unable to be created.
     */
    @PostMapping("/register")
    public ResponseEntity<?> postUser(@Valid @RequestBody User user){
        Optional<User> optionalUser = userService.registerUser(user);
        return optionalUser.<ResponseEntity<?>>map(value -> new ResponseEntity<>(value, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>("Unable to create user", HttpStatus.CONFLICT));
    }

}
