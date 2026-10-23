package com.lm_api.librarymanagementapi.repository;

import com.lm_api.librarymanagementapi.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
}
