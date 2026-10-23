package com.lm_api.librarymanagementapi.controller;

import com.lm_api.librarymanagementapi.dto.ApiResponse;
import com.lm_api.librarymanagementapi.dto.BookRequest;
import com.lm_api.librarymanagementapi.dto.BookResponse;
import com.lm_api.librarymanagementapi.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;
    @Autowired
    public BookController(final BookService bookService){
        this.bookService=bookService;
    }

    @Operation(summary = "Get a book by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse> >getById(@PathVariable Long id){
        BookResponse book=bookService.getBookById(id);
        ApiResponse<BookResponse> response= ApiResponse.<BookResponse>builder().
        success(true).message("record fetched successfully").data(book).build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all books")    @GetMapping
    public Page<BookResponse> getAll(Pageable pageable){
        return bookService.getAllBooks(pageable);
    }

    @Operation(summary = "Get books by status")    @GetMapping("/status")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getByStatus(@RequestParam String status){
        List<BookResponse> bookResponses=bookService.getBookByStatus(status);
        ApiResponse<List<BookResponse>> response= ApiResponse.<List<BookResponse>>builder().
                success(true).message("record fetched successfully by status").data(bookResponses).build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get books by title")    @GetMapping("/title")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getByTitle(@RequestParam String title){
        List<BookResponse> bookResponses=bookService.getBookByTitle(title);
        ApiResponse<List<BookResponse>> response= ApiResponse.<List<BookResponse>>builder().
                success(true).message("record fetched successfully by title").data(bookResponses).build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get books by author")    @GetMapping("/author")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getByAuthor(@RequestParam String author){
        List<BookResponse> bookResponses= bookService.getBookByAuthor(author);
        ApiResponse<List<BookResponse>> response= ApiResponse.<List<BookResponse>>builder().
                success(true).message("record fetched successfully by author").data(bookResponses).build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get books by category")    @GetMapping("/category/{id}")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getByCategory(@PathVariable Long id){
        List<BookResponse> bookResponses= bookService.getByCategoryId(id);
        ApiResponse<List<BookResponse>> response= ApiResponse.<List<BookResponse>>builder().
                success(true).message("record fetched successfully by category id").data(bookResponses).build();
        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Create a new book")    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> createBook(@Valid @RequestBody BookRequest bookRequest){
        BookResponse bookResponse= bookService.bookCreation(bookRequest);
        ApiResponse<BookResponse> response= ApiResponse.<BookResponse>builder().
        success(true).message("book record created successfully").data(bookResponse).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(summary = "Update a book")    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> updateBook(@PathVariable Long id,@Valid @RequestBody BookRequest bookRequest){
        BookResponse bookResponse= bookService.bookUpdation(id,bookRequest);
        ApiResponse<BookResponse> response= ApiResponse.<BookResponse>builder().
                success(true).message("Book record updated successfully").data(bookResponse).build();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a book")    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> delBook(@PathVariable Long id){
        BookResponse bookResponse= bookService.bookDeletion(id);
        ApiResponse<BookResponse> response= ApiResponse.<BookResponse>builder().
                success(true).message("Book record deleted successfully").data(bookResponse).build();
        return ResponseEntity.ok(response);
    }

}
