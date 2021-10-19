package com.example.auctionapp.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAORepository extends JpaRepository<UserDao, Long> {
    UserDao findByUsername(String username);
}
