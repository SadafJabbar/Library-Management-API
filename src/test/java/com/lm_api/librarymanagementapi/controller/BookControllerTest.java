package com.lm_api.librarymanagementapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lm_api.librarymanagementapi.configuration.CacheConfig;
import com.lm_api.librarymanagementapi.dto.BookRequest;
import com.lm_api.librarymanagementapi.dto.BookResponse;
import com.lm_api.librarymanagementapi.entities.CategoryEntity;
import com.lm_api.librarymanagementapi.service.BookService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@Import(CacheConfig.class)
class BookControllerTest {

    public static final String TITLE = "Resolution";
    public static final String AUTHOR = "William gray";
    public static final int STOCK = 10;
    public static final Long YEAR = 1990L;
    public static final Long C_ID = 3L;
    public static final Long COUNT = 10L;
    public static final Long B_ID = 9L;
    public static final String STATUS = "Available";
    public static final String C_NAME = "Programming";


    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private BookService bookService;


    @Test
    void method_should_return_BookIdResponse() throws Exception {
        CategoryEntity category = CategoryEntity.builder()
                .id(C_ID)
                .name(C_NAME)
                .book_count(COUNT)
                .build();
        BookResponse bookResponse = BookResponse.builder()
                .id(B_ID)
                .title(TITLE)
                .author(AUTHOR)
                .status(STATUS)
                .publishedYear(YEAR)
                .stock(STOCK)
                .category(category)
                .build();
        when(bookService.getBookById(B_ID)).thenReturn(bookResponse);
        mockMvc.perform(get("/api/v1/books/"+B_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookResponse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("record fetched successfully"))
                .andExpect(jsonPath("$.data.id").value(B_ID))
                .andExpect(jsonPath("$.data.title").value(TITLE))
                .andExpect(jsonPath("$.data.author").value(AUTHOR))
                .andExpect(jsonPath("$.data.stock").value(STOCK))
                .andExpect(jsonPath("$.data.status").value(STATUS));}


    @Test
    public void should_return_all_books()throws Exception{

        CategoryEntity category = CategoryEntity.builder()
                .id(C_ID)
                .name(C_NAME)
                .book_count(COUNT)
                .build();
        BookResponse bookResponse = BookResponse.builder()
                .id(B_ID)
                .title(TITLE)
                .author(AUTHOR)
                .status(STATUS)
                .publishedYear(YEAR)
                .stock(STOCK)
                .category(category)
                .build();
        Page<BookResponse> page=new PageImpl<>(List.of(bookResponse));
        when(bookService.getAllBooks(any(Pageable.class))).thenReturn(page);
        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(B_ID))
                .andExpect(jsonPath("$.content[0].title").value(TITLE))
                .andExpect(jsonPath("$.content[0].author").value(AUTHOR))
                .andExpect(jsonPath("$.content[0].stock").value(STOCK))
                .andExpect(jsonPath("$.content[0].status").value(STATUS));


    }

    @Test
    void method_should_return_BookStatusResponse() throws Exception {
        CategoryEntity category = CategoryEntity.builder()
                .id(C_ID)
                .name(C_NAME)
                .book_count(COUNT)
                .build();

        BookResponse bookResponse = BookResponse.builder()
                .id(B_ID)
                .title(TITLE)
                .author(AUTHOR)
                .status(STATUS)
                .publishedYear(YEAR)
                .stock(STOCK)
                .category(category)
                .build();
        when(bookService.getBookByStatus(STATUS)).thenReturn(List.of(bookResponse));
        mockMvc.perform(get("/api/v1/books/status").param("status",STATUS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("record fetched successfully by status"))
                .andExpect(jsonPath("$.data[0].id").value(B_ID))
                .andExpect(jsonPath("$.data[0].title").value(TITLE))
                .andExpect(jsonPath("$.data[0].author").value(AUTHOR))
                .andExpect(jsonPath("$.data[0].stock").value(STOCK))
                .andExpect(jsonPath("$.data[0].status").value(STATUS));}


    @Test
    public void should_return_bookByTitle() throws Exception{
        CategoryEntity category = CategoryEntity.builder()
                .id(C_ID)
                .name(C_NAME)
                .book_count(COUNT)
                .build();

        BookResponse bookResponse = BookResponse.builder()
                .id(B_ID)
                .title(TITLE)
                .author(AUTHOR)
                .status(STATUS)
                .publishedYear(YEAR)
                .stock(STOCK)
                .category(category)
                .build();
        when(bookService.getBookByTitle(TITLE)).thenReturn(List.of(bookResponse));
        mockMvc.perform(get("/api/v1/books/title").param("title",TITLE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("record fetched successfully by title"))
                .andExpect(jsonPath("$.data[0].id").value(B_ID))
                .andExpect(jsonPath("$.data[0].title").value(TITLE))
                .andExpect(jsonPath("$.data[0].author").value(AUTHOR))
                .andExpect(jsonPath("$.data[0].stock").value(STOCK))
                .andExpect(jsonPath("$.data[0].status").value(STATUS));

    }

    @Test
    public void should_return_bookByAuthor() throws Exception{
        CategoryEntity category = CategoryEntity.builder()
                .id(C_ID)
                .name(C_NAME)
                .book_count(COUNT)
                .build();

        BookResponse bookResponse = BookResponse.builder()
                .id(B_ID)
                .title(TITLE)
                .author(AUTHOR)
                .status(STATUS)
                .publishedYear(YEAR)
                .stock(STOCK)
                .category(category)
                .build();
        when(bookService.getBookByAuthor(AUTHOR)).thenReturn(List.of(bookResponse));
        mockMvc.perform(get("/api/v1/books/author").param("author",AUTHOR))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("record fetched successfully by author"))
                .andExpect(jsonPath("$.data[0].id").value(B_ID))
                .andExpect(jsonPath("$.data[0].title").value(TITLE))
                .andExpect(jsonPath("$.data[0].author").value(AUTHOR))
                .andExpect(jsonPath("$.data[0].stock").value(STOCK))
                .andExpect(jsonPath("$.data[0].status").value(STATUS));

    }


    @Test
    public void should_return_by_categoryId() throws Exception{
        CategoryEntity category = CategoryEntity.builder()
                .id(C_ID)
                .name(C_NAME)
                .book_count(COUNT)
                .build();

        BookResponse bookResponse = BookResponse.builder()
                .id(B_ID)
                .title(TITLE)
                .author(AUTHOR)
                .status(STATUS)
                .publishedYear(YEAR)
                .stock(STOCK)
                .category(category)
                .build();
        when(bookService.getByCategoryId(C_ID)).thenReturn(List.of(bookResponse));
        mockMvc.perform(get("/api/v1/books/category/"+C_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("record fetched successfully by category id"))
                .andExpect(jsonPath("$.data[0].id").value(B_ID))
                .andExpect(jsonPath("$.data[0].title").value(TITLE))
                .andExpect(jsonPath("$.data[0].author").value(AUTHOR))
                .andExpect(jsonPath("$.data[0].stock").value(STOCK))
                .andExpect(jsonPath("$.data[0].status").value(STATUS));

    }

    @Test
    void method_should_return_BookCreationResponse() throws Exception {
        CategoryEntity category = CategoryEntity.builder()
                .id(C_ID)
                .name(C_NAME)
                .book_count(COUNT)
                .build();
        BookRequest bookRequest = BookRequest.builder()
                .title(TITLE)
                .author(AUTHOR)
                .stock(STOCK)
                .publishedYear(YEAR)
                .categoryId(C_ID)
                .build();
        BookResponse bookResponse = BookResponse.builder()
                .id(B_ID)
                .title(TITLE)
                .author(AUTHOR)
                .status(STATUS)
                .publishedYear(YEAR)
                .stock(STOCK)
                .category(category)
                .build();
        when(bookService.bookCreation(any(BookRequest.class)))
                .thenReturn(bookResponse);
        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("book record created successfully"))
                .andExpect(jsonPath("$.data.id").value(B_ID))
                .andExpect(jsonPath("$.data.title").value(TITLE))
                .andExpect(jsonPath("$.data.author").value(AUTHOR))
                .andExpect(jsonPath("$.data.stock").value(STOCK))
                .andExpect(jsonPath("$.data.status").value(STATUS));}


    @Test
    public void should_return_book_updation() throws Exception {

        CategoryEntity category = CategoryEntity.builder()
                .id(C_ID)
                .name("programs")
                .book_count(9L)
                .build();

        BookRequest bookRequest = BookRequest.builder()
                .title("java coding")
                .author("buyers")
                .stock(6)
                .publishedYear(1992L)
                .categoryId(C_ID)
                .build();

        BookResponse bookResponse = BookResponse.builder()
                .id(B_ID)
                .title("java coding")
                .author("buyers")
                .status(STATUS)
                .publishedYear(1992L)
                .stock(6)
                .category(category)
                .build();

        when(bookService.bookUpdation(eq(B_ID), any(BookRequest.class)))
                .thenReturn(bookResponse);

        mockMvc.perform(put("/api/v1/books/" + B_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Book record updated successfully"))
                .andExpect(jsonPath("$.data.id").value(B_ID))
                .andExpect(jsonPath("$.data.title").value("java coding"))
                .andExpect(jsonPath("$.data.author").value("buyers"))
                .andExpect(jsonPath("$.data.stock").value(6))
                .andExpect(jsonPath("$.data.status").value(STATUS));
    }

    @Test
    public void should_return_delBook() throws Exception{
        CategoryEntity category = CategoryEntity.builder()
                .id(C_ID)
                .name(C_NAME)
                .book_count(COUNT)
                .build();

        BookResponse bookResponse = BookResponse.builder()
                .id(B_ID)
                .title(TITLE)
                .author(AUTHOR)
                .status(STATUS)
                .publishedYear(YEAR)
                .stock(STOCK)
                .category(category)
                .build();
        when(bookService.bookDeletion(B_ID)).thenReturn(bookResponse);
        mockMvc.perform(delete("/api/v1/books/" + B_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Book record deleted successfully"))
                .andExpect(jsonPath("$.data.id").value(B_ID))
                .andExpect(jsonPath("$.data.title").value(TITLE))
                .andExpect(jsonPath("$.data.author").value(AUTHOR))
                .andExpect(jsonPath("$.data.stock").value(STOCK))
                .andExpect(jsonPath("$.data.status").value(STATUS));

    }


}