package com.lm_api.librarymanagementapi.repository;

import com.lm_api.librarymanagementapi.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    List<UserEntity> findByNameContainingIgnoreCase(String name);
    Optional<UserEntity> findByName(String name);
    Boolean existsByName(String name);
}
