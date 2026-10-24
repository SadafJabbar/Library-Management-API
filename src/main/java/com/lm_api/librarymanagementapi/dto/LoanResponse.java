package com.lm_api.librarymanagementapi.dto;

import lombok.Builder;

import java.time.LocalDate;
@Builder
public record LoanResponse(
        Long id,
        Long UserId,
        Long bookId,
        LocalDate borrowDate,
        LocalDate returnDate
) {
}
