package com.lm_api.librarymangementapi.service;

import com.lm_api.librarymangementapi.annotation.TrackExecution;
import com.lm_api.librarymangementapi.dto.BookRequest;
import com.lm_api.librarymangementapi.dto.BookResponse;
import com.lm_api.librarymangementapi.entities.BookEntity;
import com.lm_api.librarymangementapi.entities.CategoryEntity;
import com.lm_api.librarymangementapi.exceptions.BookNotFoundException;
import com.lm_api.librarymangementapi.exceptions.CategoryNotFoundException;
import com.lm_api.librarymangementapi.mapper.BookMapper;
import com.lm_api.librarymangementapi.repository.BookRepository;
import com.lm_api.librarymangementapi.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CategoryRepository categoryRepository;
    @Autowired
    public BookService( BookRepository bookRepository,BookMapper bookMapper,CategoryRepository categoryRepository){
        this.bookRepository=bookRepository;
        this.bookMapper=bookMapper;
        this.categoryRepository=categoryRepository;
    }

    @TrackExecution(name = "Get book by Id")
    @Cacheable(value = "books", key = "#id")
    public BookResponse getBookById(Long id){
        BookEntity bookEntity=bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException(id));
        return bookMapper.transformToBookResponse(bookEntity);
    }

    @TrackExecution(name = "Getting All books")
    public Page<BookResponse> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable)
                .map(bookMapper::transformToBookResponse);
    }


    @TrackExecution(name = "Getting books by Status")
    public List<BookResponse> getBookByStatus(String status){
        List<BookResponse> responses=new ArrayList<>();
        for(BookEntity book:bookRepository.findAllByStatusContainingIgnoreCase(status)){
            responses.add(bookMapper.transformToBookResponse(book));
        }
        return responses;
    }


    @TrackExecution(name = "Getting books by Title")
    public List<BookResponse> getBookByTitle(String title){
        List<BookResponse> responses=new ArrayList<>();
        for(BookEntity book:bookRepository.findAllByTitleContainingIgnoreCase(title)){
            responses.add(bookMapper.transformToBookResponse(book));}
        return responses;
    }


    @TrackExecution(name = "Getting books by Author")
    public List<BookResponse> getBookByAuthor(String author){
        List<BookResponse> responses=new ArrayList<>();
        for(BookEntity book:bookRepository.findAllByAuthorContainingIgnoreCase(author)){
            responses.add(bookMapper.transformToBookResponse(book));}
        return responses;
    }


    @TrackExecution(name = "Getting book by CategoryId")
    public List<BookResponse> getByCategoryId(Long id){
        List<BookResponse> responses=new ArrayList<>();
        for(BookEntity book:bookRepository.findAllByCategoryId(id)){
            responses.add(bookMapper.transformToBookResponse(book));}
        return responses;

    }


    @TrackExecution(name="Book Creation")
    public BookResponse bookCreation( BookRequest bookRequest){
        CategoryEntity category = categoryRepository.findById(bookRequest.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(bookRequest.categoryId()));
        BookEntity bookEntity=bookMapper.transformToBookEntity(bookRequest);
        bookEntity.setCategory(category);
        bookEntity=bookRepository.save(bookEntity);
        Long count = bookRepository.countByCategoryId(category.getId());
        category.setBook_count(count);
        categoryRepository.save(category);
        return bookMapper.transformToBookResponse(bookEntity);
    }


    @TrackExecution(name = "Book Updation")
    @CacheEvict(value = "books",key = "#id")
    public BookResponse bookUpdation(Long id, BookRequest bookRequest){
        BookEntity existingBook=bookRepository.findById(id).orElseThrow(()->new BookNotFoundException(id));
        CategoryEntity oldCategory=existingBook.getCategory();
        CategoryEntity newCategory=categoryRepository.findById(bookRequest.categoryId()).orElseThrow(()-> new CategoryNotFoundException(bookRequest.categoryId()));
        BookEntity book= bookMapper.updateEntity(existingBook,bookRequest);
        book.setCategory(newCategory);
        book=bookRepository.save(book);
        Long count=bookRepository.countByCategoryId(newCategory.getId());
        Long countId=bookRepository.countByCategoryId(oldCategory.getId());
        oldCategory.setBook_count(countId);
        newCategory.setBook_count(count);
        categoryRepository.save(oldCategory);
        categoryRepository.save(newCategory);
        return bookMapper.transformToBookResponse(book);
    }

    @TrackExecution(name = "book Deletion by Id")
    @CacheEvict(value = "books", key = "#id")
    public BookResponse bookDeletion(Long id) {
        BookEntity book=bookRepository.findById(id).orElseThrow(()-> new BookNotFoundException(id));
        BookResponse bookResponse=bookMapper.transformToBookResponse(book);
        CategoryEntity category=book.getCategory();
        bookRepository.deleteById(id);
        Long count=bookRepository.countByCategoryId(category.getId());
        category.setBook_count(count);
        categoryRepository.save(category);
        return bookResponse;
    }

}
