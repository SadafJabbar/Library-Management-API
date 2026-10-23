package com.lm_api.librarymanagementapi.mapper;

import com.lm_api.librarymanagementapi.dto.LoanResponse;
import com.lm_api.librarymanagementapi.entities.BookEntity;
import com.lm_api.librarymanagementapi.entities.CategoryEntity;
import com.lm_api.librarymanagementapi.entities.LoanEntity;
import com.lm_api.librarymanagementapi.entities.UserEntity;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoanMapperTest {
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

    @Autowired
    private final LoanMapper loanMapper=new LoanMapper();

    @Test
    public void should_return_loanResponse(){
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
        LoanResponse loanResponse=loanMapper.transformToLoanResponse(loanEntity);
        assertEquals(L_ID,loanResponse.id());
        assertEquals(U_ID,loanResponse.UserId());
        assertEquals(B_ID,loanResponse.bookId());
    }

    @Test
    public void should_return_loanEntity(){
        UserEntity user= UserEntity.builder().id(U_ID)
                .name(NAME).email(EMAIL).membershipStatus(STATUS).build();
        CategoryEntity category= CategoryEntity.builder()
                .id(C_ID).name(C_NAME).book_count(COUNT).build();
        BookEntity book= BookEntity.builder()
                .id(B_ID).title(TITLE).stock(STOCK)
                .author(AUTHOR).status(STATUS_BOOK).
                publishedYear(YEAR).category(category).build();
        LoanEntity loanEntity=loanMapper.transformToLoanEntity(user,book);
        assertEquals(user,loanEntity.getUser());
        assertEquals(book,loanEntity.getBook());
    }
}