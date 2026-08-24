package com.lm_api.librarymangementapi.service;

import com.lm_api.librarymangementapi.annotation.TrackExecution;
import com.lm_api.librarymangementapi.dto.LoanRequest;
import com.lm_api.librarymangementapi.dto.LoanResponse;
import com.lm_api.librarymangementapi.entities.BookEntity;
import com.lm_api.librarymangementapi.entities.LoanEntity;
import com.lm_api.librarymangementapi.entities.UserEntity;
import com.lm_api.librarymangementapi.exceptions.BookNotFoundException;
import com.lm_api.librarymangementapi.exceptions.LoanNotFoundException;
import com.lm_api.librarymangementapi.exceptions.UserNotFoundException;
import com.lm_api.librarymangementapi.mapper.LoanMapper;
import com.lm_api.librarymangementapi.repository.BookRepository;
import com.lm_api.librarymangementapi.repository.LoanRepository;
import com.lm_api.librarymangementapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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


    @TrackExecution
    @Cacheable(value = "loans",key = "#id")
    public LoanResponse getLoanById(Long id){
        LoanEntity loan=loanRepository.findById(id).orElseThrow(()-> new LoanNotFoundException(id));
        return loanMapper.transformToLoanResponse(loan);
    }


    @TrackExecution
     public Page<LoanResponse> getAllLoans(Pageable pageable){
        return loanRepository.findAll(pageable).map(loanMapper::transformToLoanResponse);
        }


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
