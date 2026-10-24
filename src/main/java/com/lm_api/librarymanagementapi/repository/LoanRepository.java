package com.lm_api.librarymanagementapi.repository;

import com.lm_api.librarymanagementapi.entities.LoanEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
@Repository
public interface LoanRepository extends JpaRepository<LoanEntity,Long> {
    int countByUserId(Long id);
    Boolean existsByUserIdAndBookId(Long UserId,Long bookId);
    Page<LoanEntity> findAllByUser_Id(Long userId,Pageable pageable);

}
