package com.lm_api.librarymanagementapi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "User_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "Book_id")
    private BookEntity book;

    private LocalDate borrowDate;
    private LocalDate returnDate;
    @PrePersist
    public void prePersist() {
        this.borrowDate = LocalDate.now();
        this.returnDate = borrowDate.plusDays(14);
    }
}
