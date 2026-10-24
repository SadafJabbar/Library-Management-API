package com.lm_api.librarymanagementapi.entities;

import com.lm_api.librarymanagementapi.dto.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private String membershipStatus;//granted

    @PrePersist
    public void  create(){
        createdAt= LocalDateTime.now();
    }}