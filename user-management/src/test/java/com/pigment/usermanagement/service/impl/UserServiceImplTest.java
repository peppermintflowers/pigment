package com.pigment.usermanagement.service.impl;

import com.pigment.usermanagement.model.Address;
import com.pigment.usermanagement.repository.UserRepository;
import com.pigment.usermanagement.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * This class tests UserServiceImpl class's functionality.
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    private User user;
    private final String email = "test@example.com";
    private final String password = "password";
    private final String phoneNumber = "1234567890";
    private final Address address = new Address("1 Street","Test City", "Test State", "111111", "Test Island");

    @BeforeEach
    void setUp() {
        user = new User(email,password,phoneNumber,address);
    }

    /**
     * Tests happy path for user registration
     * Verifies save method from repository is called, User object is returned, and its id is set.
     */
    @Test
    void testRegisterUser_happy(){
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<User> result = userService.registerUser(user);

        assertTrue(result.isPresent());
        assertNotNull(result.get().getId());
        verify(userRepository).save(any(User.class));
    }

    /**
     * Tests scenario when user registration is attempted but user with same email exists.
     * Expects empty to be returned.
     */
    @Test
    void testRegisterUser_notCreatedExisting(){
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Optional<User> result = userService.registerUser(user);

        assertTrue(result.isEmpty());
        verify(userRepository, never()).save(any(User.class));

    }

    /**
     * Tests scenario when user persistence is failed and expects empty is returned.
     */
    @Test
    void testRegisterUser_failPersist(){
        doThrow(new RuntimeException("Persisting Error")).when(userRepository).save(any(User.class));

        Optional<User> result = userService.registerUser(user);

        assertTrue(result.isEmpty());
        verify(userRepository).save(any(User.class));

    }
}
