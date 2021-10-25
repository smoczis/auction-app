package com.example.auctionapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PAYMENT_REQUIRED)
public class NoSufficientBidsException extends Exception {

    private static final long serialVersionUID = 1L;

    public NoSufficientBidsException(String message) {
        super(message);
    }
}
