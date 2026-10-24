package com.lm_api.librarymanagementapi.exceptions;

public class UsernameNotFoundException extends RuntimeException {
    public UsernameNotFoundException(String username) {
        super("not user with username " +username+ " is found");
    }
}
