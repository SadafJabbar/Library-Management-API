package com.lm_api.librarymanagementapi.service;


import com.lm_api.librarymanagementapi.dto.CategoryRequest;
import com.lm_api.librarymanagementapi.entities.CategoryEntity;
import com.lm_api.librarymanagementapi.repository.BookRepository;
import com.lm_api.librarymanagementapi.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    public static final String NAME="Programming";
    public static final Long C_Id=4L;
    public static final Long COUNT=9L;

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private CategoryService categoryService;


    @Test
    public void method_should_return_categoryById(){
        CategoryEntity category=CategoryEntity.builder().
                id(C_Id).name(NAME).book_count(COUNT).build();
        when(categoryRepository.findById(C_Id)).thenReturn(Optional.of(category));
        CategoryEntity actualCategory=categoryService.getCategoryById(C_Id);

        assertAll(()-> assertEquals(NAME,actualCategory.getName()),
                ()-> assertEquals(COUNT,actualCategory.getBook_count()),
                ()-> assertEquals(C_Id,actualCategory.getId()));

        verify(categoryRepository).findById(C_Id);}


    @Test
    public void method_should_return_allCategories(){
        CategoryEntity category=CategoryEntity.builder().
                id(C_Id).name(NAME).book_count(COUNT).build();
        Pageable pageable= PageRequest.of(0,10);
        Page<CategoryEntity> page=new PageImpl(List.of(category),pageable,1);


        when(categoryRepository.findAll(pageable)).thenReturn(page);
        Page<CategoryEntity> categoryEntities=categoryService.getAllCategories(pageable);
        assertNotNull(categoryEntities);
        assertEquals(1,categoryEntities.getTotalElements());
        assertEquals(category,categoryEntities.getContent().get(0));
        verify(categoryRepository).findAll(pageable);}



    @Test
    public void method_should_return_categoryCreation(){
        CategoryRequest categoryRequest= CategoryRequest.builder().name(NAME).build();

        CategoryEntity savedCategory=CategoryEntity.builder().
                id(C_Id).name(NAME).book_count(COUNT).build();

        when(categoryRepository.save(any(CategoryEntity.class)))
                .thenReturn(savedCategory);
        CategoryEntity actualCategory=categoryService.categoryCreation(categoryRequest);

        assertAll(()-> assertEquals(NAME,actualCategory.getName()),
                ()-> assertEquals(COUNT,actualCategory.getBook_count()),
                ()-> assertEquals(C_Id,actualCategory.getId()));

        verify(categoryRepository)
                .save(any(CategoryEntity.class));    }


    @Test
    public void method_should_return_categoryUpdation(){
        CategoryRequest categoryRequest= CategoryRequest.builder().name(NAME).build();

        CategoryEntity savedCategory=CategoryEntity.builder().
                id(C_Id).name(NAME).book_count(COUNT).build();

        when(categoryRepository.findById(C_Id)).thenReturn(Optional.of(savedCategory));
        when(categoryRepository.save(any(CategoryEntity.class))).thenReturn(savedCategory);
        CategoryEntity actualCategory=categoryService.categoryUpdation(C_Id,categoryRequest);

        assertAll(()-> assertEquals(NAME,actualCategory.getName()),
                ()-> assertEquals(COUNT,actualCategory.getBook_count()),
                ()-> assertEquals(C_Id,actualCategory.getId()));

        verify(categoryRepository).findById(C_Id);
        verify(categoryRepository)
                .save(any(CategoryEntity.class));    }

    @Test
    public void method_should_return_categoryDeletion(){
        CategoryEntity savedCategory=CategoryEntity.builder().
                id(C_Id).name(NAME).book_count(COUNT).build();

        when(categoryRepository.findById(C_Id)).thenReturn(Optional.of(savedCategory));
        CategoryEntity actualCategory=categoryService.categoryDeletion(C_Id);

        verify(categoryRepository).findById(C_Id);
        verify(categoryRepository)
                .deleteById(C_Id);    }


}