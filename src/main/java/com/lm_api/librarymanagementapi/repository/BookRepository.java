package com.lm_api.librarymanagementapi.repository;

import com.lm_api.librarymanagementapi.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity,Long> {
    public List<BookEntity> findAllByStatusContainingIgnoreCase(String  status);
    public List<BookEntity> findAllByTitleContainingIgnoreCase(String  status);
    public List<BookEntity> findAllByAuthorContainingIgnoreCase(String  status);
    public List<BookEntity> findAllByCategoryId(Long id);
    public Long countByCategoryId(Long id);


}
