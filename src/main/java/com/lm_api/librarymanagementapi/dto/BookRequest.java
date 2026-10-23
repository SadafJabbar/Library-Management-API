package com.lm_api.librarymanagementapi.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record BookRequest(
        @NotBlank(message = "title cannot be null")
        @Size(max = 40,min = 5,message = "title must be between 5 to 40 characters")
         String title,

        @NotBlank(message = "author cannot be null")
        @Size(max = 40,min = 5,message = "author must be between 5 to 40 characters")
        String author,

     int stock,
     @NotNull(message = "please enter published year for the book")
     Long publishedYear,

        Long categoryId
     ){

}
