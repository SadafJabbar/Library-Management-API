package com.lm_api.librarymanagementapi.controller;


import com.lm_api.librarymanagementapi.dto.ApiResponse;
import com.lm_api.librarymanagementapi.dto.UserRequest;
import com.lm_api.librarymanagementapi.dto.UserResponse;
import com.lm_api.librarymanagementapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(final UserService userService){
        this.userService=userService;
    }

    @Operation(summary = "Get a user by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getById(@PathVariable  Long id){
        UserResponse userResponse= userService.getUserById(id);
        ApiResponse<UserResponse> apiResponse= ApiResponse.<UserResponse>builder()
                .success(true).message("user record fetched successfully").data(userResponse).build();
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Get all users")
    @GetMapping
    public Page<UserResponse> getAll(Pageable pageable){
        return userService.getAllUsers(pageable);
    }

    @Operation(summary = "Get users by name")
    @GetMapping("/name")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getByName(@RequestParam String name){
        List<UserResponse> userResponses= userService.getUserByName(name);
        ApiResponse<List<UserResponse>> apiResponse= ApiResponse.<List<UserResponse>>builder()
                .success(true).message("records fetched successfully by name").data(userResponses).build();
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Create a new user")
    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest userRequest){
        UserResponse userResponse= userService.userCreation(userRequest);
        ApiResponse<UserResponse> apiResponse= ApiResponse.<UserResponse>builder()
                .success(true).message("user record created successfully").data(userResponse).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

    }

    @Operation(summary = "Update a user")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id,@Valid @RequestBody UserRequest userRequest){
        UserResponse userResponse= userService.userUpdation(id,userRequest);
        ApiResponse<UserResponse> apiResponse= ApiResponse.<UserResponse>builder()
                .success(true).message("user record updated successfully").data(userResponse).build();
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Delete a user")    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> delUser(@PathVariable Long id){
        UserResponse userResponse= userService.userDeletion(id);
        ApiResponse<UserResponse> apiResponse= ApiResponse.<UserResponse>builder()
                .success(true).message("user record deleted successfully").data(userResponse).build();
        return ResponseEntity.ok(apiResponse);
    }
}
