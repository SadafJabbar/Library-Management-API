package com.lm_api.librarymanagementapi.exceptions;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler =
            new GlobalExceptionHandler(new ExceptionResponse());

    @Test
    public void should_return_book_404(){
        BookNotFoundException bookNotFoundException=new BookNotFoundException(10L);
        WebRequest webRequest=mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/api/v1/books/10");
        ResponseEntity<Map<String,Object>> response=globalExceptionHandler.bookNotFound(bookNotFoundException,webRequest);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        Map<String,Object> map=response.getBody();
        assertNotNull(map);
        assertEquals(bookNotFoundException.getMessage(),map.get("message"));
        assertEquals("/api/v1/books/10", response.getBody().get("path"));
        assertNotNull(map.get("timestamp"));

    }

    @Test
    public void should_return_user_404(){
        UserNotFoundException userNotFoundException=new UserNotFoundException(10L);
        WebRequest webRequest=mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/api/v1/users/10");
        ResponseEntity<Map<String,Object>> response=globalExceptionHandler.userNotFound(userNotFoundException,webRequest);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        Map<String,Object> map=response.getBody();
        assertNotNull(map);
        assertEquals(userNotFoundException.getMessage(),map.get("message"));
        assertEquals("/api/v1/users/10",map.get("path"));
        assertNotNull(map.get("timestamp"));
    }

    @Test
    public void should_return_loan_404(){
        LoanNotFoundException loanNotFoundException=new LoanNotFoundException(10L);
        WebRequest webRequest=mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/api/v1/loans/10");
        ResponseEntity<Map<String,Object>> response=globalExceptionHandler.loanNotFound(loanNotFoundException,webRequest);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        Map<String,Object> map=response.getBody();
        assertNotNull(map);
        assertEquals(loanNotFoundException.getMessage(),map.get("message"));
        assertEquals("/api/v1/loans/10",map.get("path"));
        assertNotNull(map.get("timestamp"));
    }

    @Test
    public void should_return_category_404(){
        CategoryNotFoundException categoryNotFoundException=new CategoryNotFoundException(10L);
        WebRequest webRequest=mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/api/v1/categories/10");
        ResponseEntity<Map<String,Object>> response=globalExceptionHandler.categoryNotFound(categoryNotFoundException,webRequest);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
        Map<String,Object> map=response.getBody();
        assertNotNull(map);
        assertEquals(categoryNotFoundException.getMessage(),map.get("message"));
        assertEquals("/api/v1/categories/10",map.get("path"));
        assertNotNull(map.get("timestamp"));
    }

    @Test
    public void should_return_method_400(){
        WebRequest webRequest=mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/api/v1/users");
        BindingResult bindingResult=mock(BindingResult.class);
        FieldError nameError=new FieldError("user request",
                "name",
                "name cannot be null");
        FieldError emailError=new FieldError("user request",
                "email",
                "please enter a valid email address");
        when(bindingResult.getFieldErrors())
                .thenReturn(List.of(nameError, emailError));
        MethodArgumentNotValidException ex=mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);
        ResponseEntity<Map<String, Object>> response = globalExceptionHandler
                .methodArgumentNotFoundException(ex,webRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body); assertEquals(400, body.get("status"));
        assertEquals("validation failed", body.get("message"));
        assertEquals("/api/v1/users", body.get("path"));
        assertNotNull(body.get("timestamp"));

        List<String> errors=(List<String>)body.get("errors");
        assertNotNull(errors);
        assertEquals(2,errors.size());
        assertTrue(errors.contains("name : name cannot be null"));
        assertTrue(errors.contains("email : please enter a valid email address"));
    }



    @Test
    public void should_return_global_500(){
        Exception exception=new Exception();
        WebRequest webRequest=mock(WebRequest.class);
        when(webRequest.getDescription(false)).thenReturn("uri=/api/v1/loans/10");
        ResponseEntity<Map<String,Object>> response=globalExceptionHandler.globalExceptions(exception,webRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
        Map<String,Object> map=response.getBody();
        assertNotNull(map);
        assertEquals(exception.getMessage(),map.get("message"));
        assertEquals("/api/v1/loans/10",map.get("path"));
        assertNotNull(map.get("timestamp"));
    }
}
