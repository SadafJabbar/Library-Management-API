package com.lm_api.librarymangementapi.service;

import com.lm_api.librarymangementapi.dto.BookRequest;
import com.lm_api.librarymangementapi.dto.BookResponse;
import com.lm_api.librarymangementapi.entities.BookEntity;
import com.lm_api.librarymangementapi.entities.CategoryEntity;
import com.lm_api.librarymangementapi.mapper.BookMapper;
import com.lm_api.librarymangementapi.repository.BookRepository;
import com.lm_api.librarymangementapi.repository.CategoryRepository;
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
class BookServiceTest {
    public static final String TITLE= "Resolution";
    public  static final String AUTHOR="William gray";
    public static final int STOCK= 10;
    public static final Long YEAR=1990L;
    public static final Long C_ID=3L;
    public static final Long book_ID=9L;
    public  static final String STATUS="Available";
    public  static final String C_NAME="Programming";


    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private BookService bookService;


    @Test
    public void Test_should_return_bookResponseById(){
        CategoryEntity categoryEntity= CategoryEntity.builder().id(C_ID)
                .name(C_NAME).book_count(10L).build();
        BookEntity bookEntity= BookEntity.builder().
                id(book_ID).title(TITLE).author(AUTHOR).
                status(STATUS).publishedYear(YEAR).
                stock(STOCK).category(categoryEntity).build();
        BookResponse response= BookResponse.builder().id(book_ID)
                .title(TITLE).author(AUTHOR).status(STATUS).
                publishedYear(YEAR).stock(STOCK).category(categoryEntity).build();
        when(bookRepository.findById(book_ID)).thenReturn(Optional.of(bookEntity));
        when(bookMapper.transformToBookResponse(any(BookEntity.class))).thenReturn(response);
        BookResponse expectedResponse=bookService.getBookById(book_ID);
        assertAll(()-> assertEquals(book_ID,expectedResponse.id()),
                ()-> assertEquals(TITLE,expectedResponse.title()),
                ()-> assertEquals(AUTHOR,expectedResponse.author()),
                ()-> assertEquals(STATUS,expectedResponse.status()),
                ()-> assertEquals(STOCK,expectedResponse.stock()),
                ()-> assertEquals(YEAR,expectedResponse.publishedYear())
        );

        verify(bookRepository).findById(book_ID);
        verify(bookMapper).transformToBookResponse(bookEntity);
    }


    @Test
    public void Test_should_return_allBookResponse(){
        Pageable page= PageRequest.of(0,10);
        CategoryEntity categoryEntity= CategoryEntity.builder().id(C_ID)
                .name(C_NAME).book_count(10L).build();

        BookEntity bookEntity= BookEntity.builder().
                id(book_ID).title(TITLE).author(AUTHOR).
                status(STATUS).publishedYear(YEAR).
                stock(STOCK).category(categoryEntity).build();

        BookResponse expectedResponse= BookResponse.builder().id(book_ID)
                .title(TITLE).author(AUTHOR).status(STATUS).
                publishedYear(YEAR).stock(STOCK).category(categoryEntity).build();
        Page<BookEntity> bookEntities=new PageImpl<>(List.of(bookEntity),page,1);
        when(bookRepository.findAll(page)).thenReturn(bookEntities);
        when(bookMapper.transformToBookResponse(bookEntity)).thenReturn(expectedResponse);
        Page<BookResponse> responses=bookService.getAllBooks(page);

        assertAll(()-> assertEquals(1,responses.getTotalElements()),
                ()-> assertEquals(book_ID,responses.getContent().get(0).id()),
                ()-> assertEquals(TITLE,responses.getContent().get(0).title()),
                ()-> assertEquals(AUTHOR,responses.getContent().get(0).author()));
        verify(bookRepository).findAll(page);
        verify(bookMapper).transformToBookResponse(bookEntity);

    }


    @Test//yet to complete
    public void Test_should_return_bookResponseByStatus(){
        CategoryEntity categoryEntity= CategoryEntity.builder().id(C_ID)
                .name(C_NAME).book_count(10L).build();

        BookEntity bookEntity= BookEntity.builder().
                id(book_ID).title(TITLE).author(AUTHOR).
                status(STATUS).publishedYear(YEAR).
                stock(STOCK).category(categoryEntity).build();

        BookResponse bookResponse= BookResponse.builder().id(book_ID)
                .title(TITLE).author(AUTHOR).status(STATUS).
                publishedYear(YEAR).stock(STOCK).category(categoryEntity).build();

        when(bookRepository.findAllByStatusContainingIgnoreCase(STATUS)).thenReturn(List.of(bookEntity));
        when(bookMapper.transformToBookResponse(bookEntity)).thenReturn(bookResponse);
        //act
       List< BookResponse>  actualResponse=bookService.getBookByStatus(STATUS);
        //verify
        assertNotNull(actualResponse);
        assertEquals(bookResponse,actualResponse.get(0));
    verify(bookRepository).findAllByStatusContainingIgnoreCase(STATUS);
    verify(bookMapper).transformToBookResponse(bookEntity);}


    @Test
    public void Test_should_return_bookResponseByTitle(){
        CategoryEntity categoryEntity= CategoryEntity.builder().id(C_ID)
                .name(C_NAME).book_count(10L).build();

        BookEntity bookEntity= BookEntity.builder().
                id(book_ID).title(TITLE).author(AUTHOR).
                status(STATUS).publishedYear(YEAR).
                stock(STOCK).category(categoryEntity).build();

        BookResponse bookResponse= BookResponse.builder().id(book_ID)
                .title(TITLE).author(AUTHOR).status(STATUS).
                publishedYear(YEAR).stock(STOCK).category(categoryEntity).build();

        when(bookRepository.findAllByTitleContainingIgnoreCase(TITLE)).thenReturn(List.of(bookEntity));
        when(bookMapper.transformToBookResponse(bookEntity)).thenReturn(bookResponse);
        //act
        List< BookResponse>  actualResponse=bookService.getBookByTitle(TITLE);
        //verify
        assertNotNull(actualResponse);
        assertEquals(bookResponse,actualResponse.get(0));
        verify(bookRepository).findAllByTitleContainingIgnoreCase(TITLE);
        verify(bookMapper).transformToBookResponse(bookEntity);}


    @Test
    public void Test_should_return_bookResponseByAuthor(){
        CategoryEntity categoryEntity= CategoryEntity.builder().id(C_ID)
                .name(C_NAME).book_count(10L).build();

        BookEntity bookEntity= BookEntity.builder().
                id(book_ID).title(TITLE).author(AUTHOR).
                status(STATUS).publishedYear(YEAR).
                stock(STOCK).category(categoryEntity).build();

        BookResponse bookResponse= BookResponse.builder().id(book_ID)
                .title(TITLE).author(AUTHOR).status(STATUS).
                publishedYear(YEAR).stock(STOCK).category(categoryEntity).build();

        when(bookRepository.findAllByAuthorContainingIgnoreCase(AUTHOR)).thenReturn(List.of(bookEntity));
        when(bookMapper.transformToBookResponse(bookEntity)).thenReturn(bookResponse);
        //act
        List< BookResponse>  actualResponse=bookService.getBookByAuthor(AUTHOR);
        //verify
        assertNotNull(actualResponse);
        assertEquals(bookResponse,actualResponse.get(0));
        verify(bookRepository).findAllByAuthorContainingIgnoreCase(AUTHOR);
        verify(bookMapper).transformToBookResponse(bookEntity);}


    @Test
    public void Test_should_return_bookResponseByCategoryId(){
        CategoryEntity categoryEntity= CategoryEntity.builder().id(C_ID)
                .name(C_NAME).book_count(10L).build();

        BookEntity bookEntity= BookEntity.builder().
                id(book_ID).title(TITLE).author(AUTHOR).
                status(STATUS).publishedYear(YEAR).
                stock(STOCK).category(categoryEntity).build();

        BookResponse bookResponse= BookResponse.builder().id(book_ID)
                .title(TITLE).author(AUTHOR).status(STATUS).
                publishedYear(YEAR).stock(STOCK).category(categoryEntity).build();

        when(bookRepository.findAllByCategoryId(C_ID)).thenReturn(List.of(bookEntity));
        when(bookMapper.transformToBookResponse(bookEntity)).thenReturn(bookResponse);
        //act
        List< BookResponse>  actualResponse=bookService.getByCategoryId(C_ID);
        //verify
        assertNotNull(actualResponse);
        assertEquals(bookResponse,actualResponse.get(0));
        verify(bookRepository).findAllByCategoryId(C_ID);
        verify(bookMapper).transformToBookResponse(bookEntity);}



    @Test
    public void Test_should_return_bookCreationResponse(){
        BookRequest bookRequest=BookRequest.builder().
                title(TITLE).author(AUTHOR).stock(STOCK).publishedYear(YEAR).categoryId(C_ID).build();

        CategoryEntity categoryEntity= CategoryEntity.builder().id(C_ID)
                .name(C_NAME).book_count(10L).build();

        BookEntity bookEntity= BookEntity.builder().
        id(book_ID).title(TITLE).author(AUTHOR).
                status(STATUS).publishedYear(YEAR).
                stock(STOCK).category(categoryEntity).build();

        BookResponse expectedResponse= BookResponse.builder().id(book_ID)
                .title(TITLE).author(AUTHOR).status(STATUS).
                publishedYear(YEAR).stock(STOCK).category(categoryEntity).build();

        when(categoryRepository.findById(C_ID)).thenReturn(Optional.of(categoryEntity));
        when(bookMapper.transformToBookEntity(bookRequest)).thenReturn(bookEntity);
        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);
        when(bookRepository.countByCategoryId(C_ID)).thenReturn(1L);
        when(bookMapper.transformToBookResponse(bookEntity)).thenReturn(expectedResponse);

        //act
        BookResponse actualResponse=bookService.bookCreation(bookRequest);
        //verify
        assertAll(
                ()-> assertEquals(TITLE,actualResponse.title()),
                ()-> assertEquals(AUTHOR,actualResponse.author()),
                ()-> assertEquals(STOCK,actualResponse.stock()),
                ()-> assertEquals(YEAR,actualResponse.publishedYear()));
    }


    @Test
    public void Test_should_return_bookUpdationResponse() {
        Long OLD_C_ID = 3L;
        Long NEW_C_ID = 5L;
        BookRequest bookRequest = BookRequest.builder()
                .title(TITLE)
                .author(AUTHOR)
                .stock(STOCK)
                .publishedYear(YEAR)
                .categoryId(NEW_C_ID)
                .build();
        CategoryEntity oldCategory = CategoryEntity.builder()
                .id(OLD_C_ID)
                .name("Old Category")
                .book_count(10L)
                .build();
        CategoryEntity newCategory = CategoryEntity.builder()
                .id(NEW_C_ID)
                .name(C_NAME)
                .book_count(5L)
                .build();
        BookEntity bookEntity = BookEntity.builder()
                .id(book_ID)
                .title(TITLE)
                .author(AUTHOR)
                .status(STATUS)
                .publishedYear(YEAR)
                .stock(STOCK)
                .category(oldCategory)
                .build();
        BookResponse bookResponse = BookResponse.builder()
                .id(book_ID)
                .title(TITLE)
                .author(AUTHOR)
                .status(STATUS)
                .publishedYear(YEAR)
                .stock(STOCK)
                .category(newCategory)
                .build();
        when(bookRepository.findById(book_ID)).thenReturn(Optional.of(bookEntity));
        when(categoryRepository.findById(NEW_C_ID)).thenReturn(Optional.of(newCategory));
        when(bookMapper.updateEntity(bookEntity, bookRequest)).thenReturn(bookEntity);
        when(bookRepository.save(bookEntity)).thenReturn(bookEntity);
        when(bookRepository.countByCategoryId(NEW_C_ID)).thenReturn(9L);
        when(bookRepository.countByCategoryId(OLD_C_ID)).thenReturn(9L);
        when(bookMapper.transformToBookResponse(bookEntity)).thenReturn(bookResponse);
        BookResponse actualResponse =bookService.bookUpdation(book_ID, bookRequest);
        assertAll(
                () -> assertEquals(TITLE, actualResponse.title()),
                () -> assertEquals(AUTHOR, actualResponse.author()),
                () -> assertEquals(STOCK, actualResponse.stock()),
                () -> assertEquals(YEAR, actualResponse.publishedYear())
        );

        verify(bookRepository).findById(book_ID);
        verify(categoryRepository).findById(NEW_C_ID);
        verify(bookMapper).updateEntity(bookEntity, bookRequest);
        verify(bookRepository).save(bookEntity);
        verify(bookRepository).countByCategoryId(NEW_C_ID);
        verify(bookRepository).countByCategoryId(OLD_C_ID);
        verify(categoryRepository).save(oldCategory);
        verify(categoryRepository).save(newCategory);
        verify(bookMapper).transformToBookResponse(bookEntity);
    }


    @Test
    public void Test_should_return_bookDeletionResponse(){
       CategoryEntity categoryEntity= CategoryEntity.builder().id(C_ID)
                .name(C_NAME).book_count(10L).build();

        BookEntity bookEntity= BookEntity.builder().
                id(book_ID).title(TITLE).author(AUTHOR).
                status(STATUS).publishedYear(YEAR).
                stock(STOCK).category(categoryEntity).build();

        BookResponse bookResponse= BookResponse.builder().id(book_ID)
                .title(TITLE).author(AUTHOR).status(STATUS).
                publishedYear(YEAR).stock(STOCK).category(categoryEntity).build();
        when(bookRepository.findById(book_ID)).thenReturn(Optional.of(bookEntity));
        when(bookMapper.transformToBookResponse(any(BookEntity.class))).thenReturn(bookResponse);
        when(bookRepository.countByCategoryId(C_ID)).thenReturn(2L);
        //act
        BookResponse actualResponse=bookService.bookDeletion(book_ID);
        verify(bookRepository).findById(book_ID);
        verify(bookMapper).transformToBookResponse(bookEntity);
        verify(bookRepository).deleteById(book_ID);
        verify(bookRepository).countByCategoryId(C_ID);
        verify(categoryRepository).save(categoryEntity);
    }


}
