package com.lm_api.librarymanagementapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserRequest(
        @NotBlank(message = "name cannot be null")
        @Size(max = 20,min = 5,message = "title must be between 5 to 20 characters")
        String name,

         @Email
         @NotBlank(message = "please enter a valid email address")
         String email) {
}
