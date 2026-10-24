package com.lm_api.librarymanagementapi.exceptions;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(Long id) {
        super("Loan with id: " +id+ "is not found");
    }
}
