package com.lm_api.librarymanagementapi.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "refresh_token")
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refreshTokenId;

    private String refreshToken;
    private String validAccessToken;
    private Instant expiresAt;
    @Column(nullable = false)
    private Boolean isRevoked=false;

    @OneToOne
    @JoinColumn(name = "user_Id",nullable = false,unique = true)
    private UserEntity userEntity;


}