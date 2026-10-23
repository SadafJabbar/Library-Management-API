package com.lm_api.librarymanagementapi.repository;

import com.lm_api.librarymanagementapi.entities.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<LoanEntity,Long> {
    int countByUserId(Long id);
    Boolean existsByUserIdAndBookId(Long UserId,Long bookId);

}
