package com.example.auctionapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ProductAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 1L;

    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}
