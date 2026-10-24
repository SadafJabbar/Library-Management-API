package com.lm_api.librarymanagementapi.service;

import com.lm_api.librarymanagementapi.dto.AuthResponse;
import com.lm_api.librarymanagementapi.entities.RefreshTokenEntity;
import com.lm_api.librarymanagementapi.repository.RefreshTokenRepository;
import com.lm_api.librarymanagementapi.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final TokenBlackListService tokenBlackListService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil,
                       RefreshTokenService refreshTokenService,
                       TokenBlackListService tokenBlackListService,
                       RefreshTokenRepository refreshTokenRepository){
        this.authenticationManager=authenticationManager;
        this.jwtUtil=jwtUtil;
        this.refreshTokenService=refreshTokenService;
        this.tokenBlackListService=tokenBlackListService;
        this.refreshTokenRepository=refreshTokenRepository;

    }

    public AuthResponse login(String username, String password){
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    username,password));
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        String accessToken= jwtUtil.generateAccessToken(userDetails.getUsername());
        String refreshToken=jwtUtil.generateRefreshToken(userDetails.getUsername());
        refreshTokenService.createRefreshTokenEntity(accessToken,refreshToken,userDetails.getUsername());
        AuthResponse authResponse=AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        return authResponse;
    }


    public String logout(String accessToken){
            String username = SecurityContextHolder.getContext()
                    .getAuthentication().getName();
            RefreshTokenEntity refreshTokenEntity =
                    refreshTokenRepository.findByUserEntity_Name(username)
                            .orElseThrow(() -> new RuntimeException(
                                    "Refresh token not found"));
            String access = accessToken.substring(7);
            if (!access.equals(refreshTokenEntity.getValidAccessToken())) {
                throw new RuntimeException("Access token does not match");
            }
            String refresh = refreshTokenEntity.getRefreshToken();
            tokenBlackListService.revokeAccessToken(access);
            tokenBlackListService.revokeRefreshToken(refresh);
            return "You have logged out successfully";
        }

}
