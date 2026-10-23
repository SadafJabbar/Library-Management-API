package com.lm_api.librarymanagementapi.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("User with id: "+id+" is not found");
    }
}
