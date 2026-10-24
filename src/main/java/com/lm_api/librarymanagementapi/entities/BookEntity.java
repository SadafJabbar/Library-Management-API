package com.lm_api.librarymanagementapi.entities;

import jakarta.persistence.*;
import lombok.*;
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String status;
    private  Long publishedYear;
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private CategoryEntity category;

}
