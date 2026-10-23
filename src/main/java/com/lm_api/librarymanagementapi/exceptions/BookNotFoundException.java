package com.lm_api.librarymanagementapi.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(Long id) {
        super("Book with id "+ id +" is not found");
    }
}
