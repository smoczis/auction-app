package com.example.auctionapp.user;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class UserResponse implements Serializable {
    private static final long serialVersionUID = 1234L;
    private final String username;
    private final Integer currentBalance;
    private final String userToken;

    public UserResponse(String username, Integer balance, String token) {
        this.username = username;
        this.currentBalance = balance;
        this.userToken = token;
    }

}
