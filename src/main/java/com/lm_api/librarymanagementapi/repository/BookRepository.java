package com.lm_api.librarymanagementapi.repository;

import com.lm_api.librarymanagementapi.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity,Long> {
    public List<BookEntity> findAllByStatusContainingIgnoreCase(String  status);
    public List<BookEntity> findAllByTitleContainingIgnoreCase(String  status);
    public List<BookEntity> findAllByAuthorContainingIgnoreCase(String  status);
    public List<BookEntity> findAllByCategoryId(Long id);
    public Long countByCategoryId(Long id);


}
