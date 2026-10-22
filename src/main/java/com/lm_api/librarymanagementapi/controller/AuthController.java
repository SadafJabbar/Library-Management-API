package com.lm_api.librarymanagementapi.controller;

import com.lm_api.librarymanagementapi.dto.AuthResponse;
import com.lm_api.librarymanagementapi.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    @Autowired
    public AuthController(AuthService authService){
        this.authService=authService;
    }

    @Operation(summary = "login")
    @GetMapping("/login")
    public AuthResponse login(@RequestParam String username, @RequestParam String password){
         return authService.login(username,password);
    }

    @Operation(summary = "logout")
    @GetMapping("/logout")
    public String logout(@RequestHeader("Authorization") String accessToken){
        return authService.logout(accessToken);
    }

}
