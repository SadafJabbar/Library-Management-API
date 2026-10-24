package com.lm_api.librarymanagementapi.mapper;

import com.lm_api.librarymanagementapi.dto.BookRequest;
import com.lm_api.librarymanagementapi.dto.BookResponse;
import com.lm_api.librarymanagementapi.entities.BookEntity;
import com.lm_api.librarymanagementapi.entities.CategoryEntity;
import com.lm_api.librarymanagementapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    private final CategoryService categoryService;
    @Autowired
    public BookMapper(CategoryService categoryService){
        this.categoryService=categoryService;
    }

    public BookResponse transformToBookResponse(BookEntity bookEntity){
        CategoryEntity categoryEntity=null;
        if(bookEntity !=null && bookEntity.getCategory()!=null){
            categoryEntity=CategoryEntity.builder().
                    id(bookEntity.getCategory().getId())
                    .name(bookEntity.getCategory().getName())
                    .book_count(bookEntity.getCategory().getBook_count()).build();
        }
        return  BookResponse.builder()
                .id(bookEntity.getId())
                .title(bookEntity.getTitle())
                .author(bookEntity.getAuthor())
                .status(bookEntity.getStatus())
                .publishedYear(bookEntity.getPublishedYear())
                .stock(bookEntity.getStock())
                .category(categoryEntity).build();
    }

    public BookEntity transformToBookEntity(BookRequest bookRequest){
        CategoryEntity categoryEntity=null;
        if(bookRequest!=null & bookRequest.categoryId()!=null){
            categoryEntity= categoryService.getCategoryById(bookRequest.categoryId());
        }
        return BookEntity.builder()
                .title(bookRequest.title())
                .author(bookRequest.author())
                .status("Available")
                .stock(bookRequest.stock())
                .publishedYear(bookRequest.publishedYear())
                .category(categoryEntity).build();
    }


    public BookEntity updateEntity(BookEntity book,BookRequest bookRequest) {
        book.setTitle(bookRequest.title());
        book.setAuthor(bookRequest.author());
        book.setStatus(book.getStatus());
        book.setStock(bookRequest.stock());
        book.setPublishedYear(bookRequest.publishedYear());
        if (bookRequest != null && bookRequest.categoryId() != null) {
            book.setCategory(categoryService.getCategoryById(bookRequest.categoryId()));
        }
        return book;
    }

}
