package com.example.auctionapp.user;

import com.example.auctionapp.exception.NoSuchUserException;

import java.util.List;

public interface UserService {

    User save(UserDTO userDTO) throws Exception;

    void save(User user);

    User findUserByUsername(String username);

    User findUserById(String id) throws NoSuchUserException;

    void updateUserBalance(String username, Integer deposit);

    List<User> findAll();

}
