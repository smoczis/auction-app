package com.example.auctionapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuctionAlreadyFinishedException extends Exception {

    private static final long serialVersionUID = 1L;
    public AuctionAlreadyFinishedException(String message) {
        super(message);
    }
}
