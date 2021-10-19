package com.example.auctionapp.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtUserDetailsService implements UserDetailsService {
    private final UserDAORepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public JwtUserDetailsService(UserDAORepository userDAORepository) {
        this.repository = userDAORepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authBuilder) throws Exception {
        authBuilder.userDetailsService(this).passwordEncoder(this.encoder);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDao user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        } else {
            return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
        }
    }

    public UserDao save(UserDTO userDTO) throws Exception {
        UserDao foundUser = repository.findByUsername(userDTO.getUsername());
        if (foundUser == null) {
            UserDao newUser = new UserDao();
            newUser.setUsername(userDTO.getUsername());
            newUser.setPassword(encoder.encode(userDTO.getPassword()));
            return repository.save(newUser);
        }
        throw new UserAlreadyExistsException("USER_ALREADY_EXISTS");
    }

    public UserDao findUserByUsername(String username) {
        return this.repository.findByUsername(username);
    }
}
