package com.pigment.usermanagement.service.impl;

import com.pigment.usermanagement.model.User;
import com.pigment.usermanagement.repository.UserRepository;
import com.pigment.usermanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
/** USERSERVICEIMPL
 * This class implements the methods of the UserService and UserDetailsService interface.
 */
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * registerUser handles the logic for registering a new customer
     * @param user : Data model with details of the customer to be registered
     * @return : Returns the User object if registration successful, else returns empty
     */
    @Override
    public Optional<User> registerUser(User user) {
        Optional<User> existingUser = userRepository.findUserByEmail(user.getUsername());
        if(existingUser.isPresent()){
            return Optional.empty();
        }
        else {
            User persistUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()),user.getPhoneNumber(),user.getAddress());
            String userId = generateNextId();
            persistUser.setId(userId);

            try {
                return Optional.of(userRepository.save(persistUser));
            }
            catch(Exception e){
                log.error(
                        "Unable to create user Exception:{}",e.toString()
                );
                return Optional.empty();
            }
        }
    }

    /**
     * This method generates the new Id for the user to be registered
     * @return new Id for the user as a string
     */
    private String generateNextId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Called when authenticating user
     * @param username
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(username)
                );

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority("USER"))
        );
    }
}
