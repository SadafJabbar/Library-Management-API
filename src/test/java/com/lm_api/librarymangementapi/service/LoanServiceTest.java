package com.lm_api.librarymangementapi.service;

import com.lm_api.librarymangementapi.dto.LoanRequest;
import com.lm_api.librarymangementapi.dto.LoanResponse;
import com.lm_api.librarymangementapi.entities.BookEntity;
import com.lm_api.librarymangementapi.entities.CategoryEntity;
import com.lm_api.librarymangementapi.entities.LoanEntity;
import com.lm_api.librarymangementapi.entities.UserEntity;
import com.lm_api.librarymangementapi.mapper.LoanMapper;
import com.lm_api.librarymangementapi.repository.BookRepository;
import com.lm_api.librarymangementapi.repository.LoanRepository;
import com.lm_api.librarymangementapi.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    public static final int STOCK=20;
    public static final Long U_ID=8L;
    public static final Long B_ID=4L;
    public static final Long L_ID=4L;
    public static final Long C_ID=5L;
    public static final Long COUNT=7L;
    public static final Long YEAR=1990L;
    public static final String NAME="jhon";
    public static final String EMAIL="jhon@gmail";
    public static final String STATUS="Granted";
    public static final String C_NAME=" Programming";
    public static final String AUTHOR="William grey";
    public static final String TITLE="Java coding";
    public static final String STATUS_BOOK=" Available";



    @Mock
    private  LoanRepository loanRepository;
    @Mock
    private  LoanMapper loanMapper;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  BookRepository bookRepository;
    @InjectMocks
    private LoanService loanService;


    @Test
    public void method_should_return_loanResponseById(){
        UserEntity user= UserEntity.builder().id(U_ID)
                .name(NAME).email(EMAIL).membershipStatus(STATUS).build();

        CategoryEntity category= CategoryEntity.builder()
                .id(C_ID).name(C_NAME).book_count(COUNT).build();

        BookEntity book= BookEntity.builder()
                .id(B_ID).title(TITLE).stock(STOCK)
                .author(AUTHOR).status(STATUS_BOOK).
                publishedYear(YEAR).category(category).build();

        LoanEntity loanEntity=LoanEntity.builder().id(L_ID)
                .user(user).book(book).build();

        LoanResponse response=LoanResponse.builder().id(L_ID)
                .UserId(U_ID).bookId(B_ID).build();

        when(loanRepository.findById(L_ID)).thenReturn(Optional.of(loanEntity));
        when(loanMapper.transformToLoanResponse(any(LoanEntity.class))).thenReturn(response);
        LoanResponse loanResponse=loanService.getLoanById(L_ID);
        assertAll(
                ()-> assertEquals(L_ID,loanResponse.id()),
                ()-> assertEquals(U_ID,loanResponse.UserId()),
                ()-> assertEquals(B_ID,loanResponse.bookId())
        );
        verify(loanRepository).findById(L_ID);
        verify(loanMapper).transformToLoanResponse(loanEntity);
    }



    @Test
    public void method_should_return_allLoanResponse(){
        LoanResponse loanResponse=LoanResponse.builder().id(L_ID)
                .UserId(U_ID).bookId(B_ID).build();
        UserEntity user= UserEntity.builder().id(U_ID)
                .name(NAME).email(EMAIL).membershipStatus(STATUS).build();

        CategoryEntity category= CategoryEntity.builder()
                .id(C_ID).name(C_NAME).book_count(COUNT).build();

        BookEntity book= BookEntity.builder()
                .id(B_ID).title(TITLE).stock(STOCK)
                .author(AUTHOR).status(STATUS_BOOK).
                publishedYear(YEAR).category(category).build();
        LoanEntity loanEntity=LoanEntity.builder().id(L_ID)
                .user(user).book(book).build();
        Pageable pageable= PageRequest.of(0,10);
        Page<LoanEntity> loanEntityPage=new PageImpl<>(List.of(loanEntity),pageable,1);
        when(loanRepository.findAll(pageable)).thenReturn(loanEntityPage);
        when(loanMapper.transformToLoanResponse(loanEntity)).thenReturn(loanResponse);
        Page<LoanResponse> loanResponsePage=loanService.getAllLoans(pageable);
        assertNotNull(loanResponsePage);
        assertEquals(1,loanResponsePage.getTotalElements());
        assertEquals(loanResponse,loanResponsePage.getContent().get(0));
        verify(loanRepository).findAll(pageable);
        verify(loanMapper).transformToLoanResponse(loanEntity);
    }


        @Test
    public void method_should_return_loanResponse(){
        LoanRequest loanRequest=LoanRequest.builder()
                .userId(U_ID).bookId(B_ID).build();

        UserEntity user= UserEntity.builder().id(U_ID)
                .name(NAME).email(EMAIL).membershipStatus(STATUS).build();

        CategoryEntity category= CategoryEntity.builder()
                .id(C_ID).name(C_NAME).book_count(COUNT).build();

        BookEntity book= BookEntity.builder()
                .id(B_ID).title(TITLE).stock(STOCK)
                .author(AUTHOR).status(STATUS_BOOK).
                publishedYear(YEAR).category(category).build();

        LoanEntity loanEntity=LoanEntity.builder().id(L_ID)
                .user(user).book(book).build();

        LoanResponse loanResponse=LoanResponse.builder().id(L_ID)
                .UserId(U_ID).bookId(B_ID).build();


        when(userRepository.findById(U_ID)).thenReturn(Optional.of(user));
        when(bookRepository.findById(B_ID)).thenReturn(Optional.of(book));
        when(loanRepository.countByUserId(U_ID)).thenReturn(1);
        when(loanRepository.existsByUserIdAndBookId(U_ID,B_ID)).thenReturn(false);
        when(loanMapper.transformToLoanEntity(user,book)).thenReturn(loanEntity);
        when(loanRepository.save(loanEntity)).thenReturn(loanEntity);
        when(bookRepository.save(book)).thenReturn(book);
        when(loanMapper.transformToLoanResponse(loanEntity)).thenReturn(loanResponse);

        LoanResponse loanResponse1=loanService.loanCreation(loanRequest);
        assertAll(()-> assertEquals(L_ID,loanResponse1.id()),
                ()-> assertEquals(U_ID,loanResponse1.UserId()),
                ()-> assertEquals(B_ID,loanResponse1.bookId()),
                ()-> assertEquals(19, book.getStock()));

        verify(userRepository).findById(U_ID);
        verify(bookRepository).findById(B_ID);
        verify(loanRepository).save(loanEntity);
        verify(bookRepository).save(book);
    }

    @Test
    public void method_should_return_deletedLoanResponse(){
        UserEntity user= UserEntity.builder().id(U_ID)
                .name(NAME).email(EMAIL).membershipStatus(STATUS).build();

        CategoryEntity category= CategoryEntity.builder()
                .id(C_ID).name(C_NAME).book_count(COUNT).build();

        BookEntity book= BookEntity.builder()
                .id(B_ID).title(TITLE).stock(STOCK)
                .author(AUTHOR).status(STATUS_BOOK).
                publishedYear(YEAR).category(category).build();

        LoanEntity loanEntity=LoanEntity.builder().id(L_ID)
                .user(user).book(book).build();

        LoanResponse loanResponse=LoanResponse.builder().id(L_ID)
                .UserId(U_ID).bookId(B_ID).build();

        when(loanRepository.findById(L_ID)).thenReturn(Optional.of(loanEntity));
        when(bookRepository.findById(B_ID)).thenReturn(Optional.of(book));
        when(loanMapper.transformToLoanResponse(any(LoanEntity.class))).thenReturn(loanResponse);
        LoanResponse loanResponse1=loanService.loanDeletion(L_ID);
        verify(loanRepository).findById(L_ID);
        verify(loanMapper).transformToLoanResponse(loanEntity);
        verify(loanRepository).deleteById(L_ID);
    }


}