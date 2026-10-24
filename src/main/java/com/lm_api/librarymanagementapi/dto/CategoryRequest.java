package com.lm_api.librarymanagementapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CategoryRequest(
        @NotBlank(message = "title cannot be null")
        @Size(max = 40,min = 5,message = "title must be between 5 to 40 characters")
        String name
         ) {
}
