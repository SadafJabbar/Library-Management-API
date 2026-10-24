package com.lm_api.librarymanagementapi.entities;


import jakarta.persistence.*;
import lombok.*;
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long book_count;

}

