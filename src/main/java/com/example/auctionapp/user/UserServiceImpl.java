package com.example.auctionapp.user;

import com.example.auctionapp.exception.NoSuchUserException;
import com.example.auctionapp.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
        this.encoder = new BCryptPasswordEncoder();
    }

    public User findUserByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    public void updateUserBalance(String username, Integer deposit) {
        User user = this.repository.findByUsername(username);
        Integer currentBalance = user.getBids();
        user.setBids(currentBalance + deposit);
        this.repository.save(user);
    }

    public void save(User user) {
        this.repository.save(user);
    }

    public User save(UserDTO userDTO) throws Exception {
        User foundUser = repository.findByUsername(userDTO.getUsername());
        if (foundUser == null) {
            User newUser = new User();
            newUser.setUsername(userDTO.getUsername());
            newUser.setPassword(encoder.encode(userDTO.getPassword()));
            newUser.setBids(0);
            return repository.save(newUser);
        }
        throw new UserAlreadyExistsException("USER_ALREADY_EXISTS");
    }

    public User findUserById(String id) throws NoSuchUserException {
        Optional<User> foundUser = repository.findById(id);
        return foundUser.orElseThrow(() -> new NoSuchUserException("USER WITH GIVEN ID DOES NOT EXIST"));
    }

    public List<User> findAll() {
        return this.repository.findAll();
    }
}
