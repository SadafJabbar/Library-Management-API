package com.lm_api.librarymangementapi.service;

import com.lm_api.librarymangementapi.annotation.TrackExecution;
import com.lm_api.librarymangementapi.dto.UserRequest;
import com.lm_api.librarymangementapi.dto.UserResponse;
import com.lm_api.librarymangementapi.entities.UserEntity;
import com.lm_api.librarymangementapi.exceptions.UserNotFoundException;
import com.lm_api.librarymangementapi.mapper.UserMapper;
import com.lm_api.librarymangementapi.repository.LoanRepository;
import com.lm_api.librarymangementapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public UserService(UserMapper userMapper,UserRepository userRepository,LoanRepository loanRepository){
        this.userMapper=userMapper;
        this.userRepository=userRepository;
        this.loanRepository=loanRepository;
    }


    @TrackExecution
    @Cacheable(value = "users",key = "#id")
    public UserResponse getUserById(Long id){
        UserEntity user=userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        return userMapper.transformToUserResponse(user);
    }

    @TrackExecution
    public Page<UserResponse> getAllUsers(Pageable pageable){
        return   userRepository.findAll(pageable).map(userMapper::transformToUserResponse);
    }



    @TrackExecution
    public List<UserResponse>   getUserByName(String name){
        List<UserResponse> userResponses=new ArrayList<>();
        for(UserEntity user:userRepository.findByNameContainingIgnoreCase(name)) {
            userResponses.add(userMapper.transformToUserResponse(user));
        }
        return userResponses;
    }


    public UserResponse userCreation(UserRequest userRequest){
        UserEntity user=userRepository.save(userMapper.transformToUserEntity(userRequest));
        return userMapper.transformToUserResponse(user);
    }

    @CacheEvict(value = "users",key = "#id")
    public UserResponse   userUpdation(Long id,UserRequest userRequest){
        UserEntity user=userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        user=userMapper.updateUser(user,userRequest);
        userRepository.save(user);
        return userMapper.transformToUserResponse(user);
    }

    @CacheEvict(value = "users", key = "#id")
    public UserResponse userDeletion(Long id){
        UserEntity user=userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
        if(loanRepository.countByUserId(id)>0){
            throw  new IllegalStateException("Clear The Book Dues for user id:" +id+ "for deletion of record");
        }
        userRepository.deleteById(id);
        return userMapper.transformToUserResponse(user);
    }
}
