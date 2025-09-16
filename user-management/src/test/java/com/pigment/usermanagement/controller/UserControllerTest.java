package com.pigment.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pigment.usermanagement.config.ApplicationConfig;
import com.pigment.usermanagement.model.Address;
import com.pigment.usermanagement.model.User;
import com.pigment.usermanagement.repository.UserRepository;
import com.pigment.usermanagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class tests UserController class's functionality.
 */
@WebMvcTest(controllers = UserController.class)
@Import(ApplicationConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;


    private final String email = "test@example.com";
    private final String password = "password";
    private final String phoneNumber = "1234567890";
    private final Address address = new Address("1 Street","Test City", "Test State", "111111", "Test Island");

    /**
     * Happy path test for when POST for user is successful
     */
    @Test
    void testRegister_success() throws Exception {
        User user = new User(email, password, phoneNumber , address );

        Mockito.when(userService.registerUser(Mockito.any(User.class)))
                .thenReturn(Optional.of(user));

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    /**
     * Tests that "Unable to create user" is returned when POST user fails.
     */
    @Test
    void testRegister_conflict() throws Exception {
        User user = new User(email, password, phoneNumber, address );

        Mockito.when(userService.registerUser(Mockito.any(User.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Unable to create user"));
    }
}
