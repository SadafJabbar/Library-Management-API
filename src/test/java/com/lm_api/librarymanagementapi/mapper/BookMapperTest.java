package com.lm_api.librarymanagementapi.mapper;

import com.lm_api.librarymanagementapi.dto.BookRequest;
import com.lm_api.librarymanagementapi.dto.BookResponse;
import com.lm_api.librarymanagementapi.entities.BookEntity;
import com.lm_api.librarymanagementapi.entities.CategoryEntity;
import com.lm_api.librarymanagementapi.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookMapperTest {
    public static final String TITLE= "Resolution";
    public  static final String AUTHOR="William gray";
    public static final int STOCK= 10;
    public static final Long YEAR=1990L;
    public static final Long C_ID=3L;
    public static final Long book_ID=9L;
    public  static final String STATUS="Available";
    public  static final String C_NAME="Programming";

    @Mock
    private  CategoryService categoryService;

    @InjectMocks
    private BookMapper bookMapper;

    @Test
    public void should_return_bookResponse(){

        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(C_ID)
                .name(C_NAME)
                .book_count(10L)
                .build();

        BookEntity bookEntity = BookEntity.builder()
                .id(book_ID)
                .title(TITLE)
                .author(AUTHOR)
                .status(STATUS)
                .publishedYear(YEAR)
                .stock(STOCK)
                .category(categoryEntity)
                .build();

        BookResponse bookResponse = bookMapper.transformToBookResponse(bookEntity);

        assertEquals(book_ID, bookResponse.id());
        assertEquals(TITLE, bookResponse.title());
        assertEquals(AUTHOR, bookResponse.author());
        assertEquals(STATUS, bookResponse.status());
        assertEquals(STOCK, bookResponse.stock());
        assertEquals(YEAR, bookResponse.publishedYear());

        assertEquals(C_ID, bookResponse.category().getId());
        assertEquals(C_NAME, bookResponse.category().getName());
        assertEquals(10L, bookResponse.category().getBook_count());
    }

    @Test
    public void should_return_bookEntity(){
        BookRequest bookRequest = BookRequest.builder()
                .title(TITLE)
                .author(AUTHOR)
                .stock(STOCK)
                .publishedYear(YEAR)
                .categoryId(C_ID)
                .build();
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(C_ID)
                .name(C_NAME)
                .book_count(10L)
                .build();
        when(categoryService.getCategoryById(C_ID)).thenReturn(categoryEntity);
        BookEntity book=bookMapper.transformToBookEntity(bookRequest);
        assertEquals(TITLE,book.getTitle());
        assertEquals(AUTHOR,book.getAuthor());
        assertEquals(STATUS,book.getStatus());
        assertEquals(STOCK,book.getStock());
        assertEquals(YEAR,book.getPublishedYear());
        assertEquals(categoryEntity,book.getCategory());

    }

    @Test
    public void should_return_bookUpdate(){

        BookRequest bookRequest = BookRequest.builder()
                .title(TITLE)
                .author(AUTHOR)
                .stock(STOCK)
                .publishedYear(YEAR)
                .categoryId(C_ID)
                .build();

        CategoryEntity categoryEntity = CategoryEntity.builder()
                .id(C_ID)
                .name(C_NAME)
                .book_count(10L)
                .build();

        BookEntity bookEntity = BookEntity.builder()
                .id(book_ID)
                .title(TITLE)
                .author(AUTHOR)
                .status(STATUS)
                .publishedYear(YEAR)
                .stock(STOCK)
                .category(categoryEntity)
                .build();

        when(categoryService.getCategoryById(C_ID))
                .thenReturn(categoryEntity);

        BookEntity book = bookMapper.updateEntity(bookEntity, bookRequest);

        assertEquals(TITLE, book.getTitle());
        assertEquals(AUTHOR, book.getAuthor());
        assertEquals(STATUS, book.getStatus());
        assertEquals(STOCK, book.getStock());
        assertEquals(YEAR, book.getPublishedYear());
    }



}