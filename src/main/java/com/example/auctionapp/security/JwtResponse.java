package com.example.auctionapp.security;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -12345678L;
    private final String userToken;

    public JwtResponse(String userToken) {
        this.userToken = userToken;
    }
}
