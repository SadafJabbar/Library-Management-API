package com.lm_api.librarymanagementapi.entities;

import jakarta.persistence.*;
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

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private String membershipStatus;//granted

    @PrePersist
    public void  create(){
        createdAt= LocalDateTime.now();}


}
