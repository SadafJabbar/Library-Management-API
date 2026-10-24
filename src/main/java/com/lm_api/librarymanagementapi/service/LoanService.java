package com.lm_api.librarymanagementapi.service;

import com.lm_api.librarymanagementapi.annotation.TrackExecution;
import com.lm_api.librarymanagementapi.dto.LoanRequest;
import com.lm_api.librarymanagementapi.dto.LoanResponse;
import com.lm_api.librarymanagementapi.dto.UserRole;
import com.lm_api.librarymanagementapi.entities.BookEntity;
import com.lm_api.librarymanagementapi.entities.LoanEntity;
import com.lm_api.librarymanagementapi.entities.UserEntity;
import com.lm_api.librarymanagementapi.exceptions.BookNotFoundException;
import com.lm_api.librarymanagementapi.exceptions.LoanNotFoundException;
import com.lm_api.librarymanagementapi.exceptions.UserNotFoundException;
import com.lm_api.librarymanagementapi.exceptions.UsernameNotFoundException;
import com.lm_api.librarymanagementapi.mapper.LoanMapper;
import com.lm_api.librarymanagementapi.repository.BookRepository;
import com.lm_api.librarymanagementapi.repository.LoanRepository;
import com.lm_api.librarymanagementapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    @Autowired
    public LoanService(LoanRepository loanRepository,LoanMapper loanMapper,
                       UserRepository userRepository,BookRepository bookRepository){
        this.loanRepository=loanRepository;
        this.loanMapper=loanMapper;
        this.userRepository=userRepository;
        this.bookRepository=bookRepository;
    }


    @TrackExecution(name = "Get Loan by Id")
    public LoanResponse getLoanById(Long id){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user=userRepository.findByName(username)
                .orElseThrow(()-> new UsernameNotFoundException(username));


        LoanEntity loan=loanRepository.findById(id).orElseThrow(()-> new LoanNotFoundException(id));
        if(user.getRole().equals(UserRole.MEMBER)){
            if(!loan.getUser().getId().equals(user.getId())){
                throw new RuntimeException("This Loan Doesn't belong to you");
        }}
        return loanMapper.transformToLoanResponse(loan);
    }


    @TrackExecution(name = "Getting All Loans")
     public Page<LoanResponse> getAllLoans(Pageable pageable){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user=userRepository.findByName(username)
                .orElseThrow(()-> new UsernameNotFoundException(username));
        if(user.getRole().equals(UserRole.MEMBER)){
            return loanRepository.findAllByUser_Id(user.getId(),pageable).map(loanMapper::transformToLoanResponse);
        }

        return loanRepository.findAll(pageable).map(loanMapper::transformToLoanResponse);
        }


    @TrackExecution(name = "Loan Creation")
    public LoanResponse loanCreation(LoanRequest loanRequest){
        Long userId=loanRequest.userId();
        Long bookId=loanRequest.bookId();
         UserEntity user=userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
         BookEntity book=bookRepository.findById(bookId).orElseThrow(()-> new BookNotFoundException(bookId));
         if(loanRepository.countByUserId(userId)>=3 ){
             throw new IllegalStateException("this user has already  reached max limit of borrowing.");
         }
         if(book.getStock()==0){
             throw new IllegalStateException("this book is currently out of stock");
         }
         if(loanRepository.existsByUserIdAndBookId(userId,bookId)){
             throw new IllegalStateException("this user has issued this book already before." +
                     " cant issue same book twice.");
         }
        LoanEntity loan= loanMapper.transformToLoanEntity(user,book);
         loanRepository.save(loan);
         book.setStock(book.getStock()-1);
         if(book.getStock()==0){
             book.setStatus("ISSUED");
         }
         bookRepository.save(book);
        return loanMapper.transformToLoanResponse(loan);
     }


    @TrackExecution(name = "Loan deletion by Id")
    @CacheEvict(value = "loans", key = "#id")
    public LoanResponse loanDeletion(Long id){
        LoanEntity loan=loanRepository.findById(id).orElseThrow(()-> new LoanNotFoundException(id));
        Long bookId=loan.getBook().getId();
        BookEntity book=bookRepository.findById(bookId).orElseThrow(()-> new BookNotFoundException(bookId));
        book.setStock(book.getStock()+1);
        if(book.getStock()>=0){
            book.setStatus("Available");}
        LoanResponse loanResponse=loanMapper.transformToLoanResponse(loan);
        bookRepository.save(book);
        loanRepository.deleteById(id);
        return loanResponse;
    }
}
