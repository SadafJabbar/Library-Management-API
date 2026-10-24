package com.lm_api.librarymanagementapi.service;

import com.lm_api.librarymanagementapi.entities.RefreshTokenEntity;
import com.lm_api.librarymanagementapi.entities.UserEntity;
import com.lm_api.librarymanagementapi.exceptions.UsernameNotFoundException;
import com.lm_api.librarymanagementapi.repository.RefreshTokenRepository;
import com.lm_api.librarymanagementapi.repository.UserRepository;
import com.lm_api.librarymanagementapi.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class RefreshTokenService {

    @Value("${jwt.refresh-expiration}")
    private Long refreshExpiration;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenBlackListService tokenBlackListService;
    private final JwtUtil jwtUtil;
    @Autowired
    public RefreshTokenService(UserRepository userRepository,
                               RefreshTokenRepository refreshTokenRepository,
                               TokenBlackListService tokenBlackListService,
                               JwtUtil jwtUtil){
        this.userRepository=userRepository;
        this.refreshTokenRepository=refreshTokenRepository;
        this.tokenBlackListService=tokenBlackListService;
        this.jwtUtil=jwtUtil;
    }

    public void createRefreshTokenEntity(String access,String refresh,String username) {
        UserEntity user = userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException(username));
        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByUserEntity_Name(username).orElseGet(RefreshTokenEntity::new);
        refreshTokenEntity.setRefreshToken(refresh);
        refreshTokenEntity.setValidAccessToken(access);
        refreshTokenEntity.setExpiresAt(new Date(System.currentTimeMillis() + refreshExpiration).toInstant());
        refreshTokenEntity.setUserEntity(user);
        refreshTokenRepository.save(refreshTokenEntity);
    }


        public String refreshAccessToken(String refreshToken) {
            System.err.println(">>> SERVICE METHOD ENTERED <<<");

            var result = refreshTokenRepository.findByRefreshToken(refreshToken);

            System.out.println("STEP 2: Lookup completed");
            System.out.println("STEP 3: Token found = " + result.isPresent());

            RefreshTokenEntity refreshTokenEntity = result
                    .orElseThrow(() -> new RuntimeException(
                            "This refresh token was not found in the repo."
                    ));

            System.out.println("STEP 4: Entity retrieved");
        if (!jwtUtil.validateJwtToken(refreshToken)) {
            throw new RuntimeException("invalid or expired refresh token");
        }

        if (tokenBlackListService.isRefreshRevoked(refreshToken)) {
            throw new RuntimeException("this refresh token is revoked");
        }

        if (refreshTokenEntity.getExpiresAt().isBefore(Instant.now())) {
            throw new RuntimeException("this refresh token has already expired");
        }

        String username = refreshTokenEntity.getUserEntity().getName();
        String invalidAccessToken = refreshTokenEntity.getValidAccessToken();

        tokenBlackListService.revokeAccessToken(invalidAccessToken);

        String newValidAccessToken = jwtUtil.generateAccessToken(username);

        refreshTokenEntity.setValidAccessToken(newValidAccessToken);
        refreshTokenRepository.save(refreshTokenEntity);

        return newValidAccessToken;
    }

}
