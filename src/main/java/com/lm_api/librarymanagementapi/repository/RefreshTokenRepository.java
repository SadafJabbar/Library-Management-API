package com.lm_api.librarymanagementapi.repository;

import com.lm_api.librarymanagementapi.entities.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity ,Long> {
    Optional<RefreshTokenEntity> findByUserEntity_Name(String Username);
    Optional<RefreshTokenEntity> findByRefreshToken(String token);
}
