package com.pigment.usermanagement.controller;

import com.pigment.usermanagement.model.LoginRequest;
import com.pigment.usermanagement.model.LoginResponse;
import com.pigment.usermanagement.model.User;
import com.pigment.usermanagement.service.TokenHandler;
import com.pigment.usermanagement.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
/** USERCONTROLLER
 * This controller class handles CRUD operations related to the User.
 * */
@RestController
@RequestMapping("api/v1/auth")
public class UserController {

    private final UserService userService;
    private final TokenHandler tokenHandler;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService,AuthenticationManager authenticationManager, TokenHandler tokenHandler) {
        this.userService=userService;
        this.authenticationManager = authenticationManager;
        this.tokenHandler = tokenHandler;
    }
    /**
     * postUser handles POST operations for a User with User role
     * @param user : The data model containing the details of the new user to be registered
     * @return: ResponseEntity indicating whether User was created or was unable to be created.
     */
    @PostMapping("/register")
    public ResponseEntity<?> postUser(@Valid @RequestBody User user){
        Optional<User> optionalUser = userService.registerUser(user);
        return optionalUser.<ResponseEntity<?>>map(value -> new ResponseEntity<>(value, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>("Unable to create user", HttpStatus.CONFLICT));
    }

    /**
     * loginUser handles login for Users
     * @param request: the data model for login request containing email and password
     * @return: LoginResponse carrying jwt token if authentication is successful.
     */
    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest request) {
        Authentication auth = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        String token = tokenHandler.generateToken((UserDetails) auth.getPrincipal());
        return new LoginResponse(token);
    }
}
