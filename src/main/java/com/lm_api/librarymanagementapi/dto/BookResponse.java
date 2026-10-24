package com.lm_api.librarymanagementapi.dto;

import com.lm_api.librarymanagementapi.entities.CategoryEntity;
import lombok.Builder;

@Builder
public record BookResponse(
        Long id,
        String title,
        String author,
        String status,
        Long publishedYear,
        int stock,
        CategoryEntity category) {
}
