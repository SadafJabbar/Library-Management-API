package com.lm_api.librarymanagementapi.controller;


import com.lm_api.librarymanagementapi.service.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/refreshToken")
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;
    @Autowired
    public RefreshTokenController (
            RefreshTokenService refreshTokenService){
        System.out.println(">>> CONTROLLER METHOD REACHED <<<");
        this.refreshTokenService=refreshTokenService;}

    @Operation(summary = "Refresh Access Token")
    @GetMapping
    public String refreshAccessToken(@RequestHeader ("Authorization") String refreshToken){
        return refreshTokenService.refreshAccessToken(refreshToken);
    }

}
