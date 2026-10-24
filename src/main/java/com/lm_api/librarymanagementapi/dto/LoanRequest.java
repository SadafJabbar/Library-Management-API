package com.lm_api.librarymanagementapi.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record LoanRequest(
        @NotNull(message = "user id is compulsory")
        Long userId,
        @NotNull(message = "Book id is compulsory")
        Long bookId
) {
}
