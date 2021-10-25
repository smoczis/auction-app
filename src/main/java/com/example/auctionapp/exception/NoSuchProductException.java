package com.example.auctionapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NoSuchProductException extends Exception {

    private static final long serialVersionUID = 1L;

    public NoSuchProductException(String message) {
        super(message);
    }
}
