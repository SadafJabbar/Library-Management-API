package com.lm_api.librarymanagementapi.service;

import com.lm_api.librarymanagementapi.dto.UserRequest;
import com.lm_api.librarymanagementapi.dto.UserResponse;
import com.lm_api.librarymanagementapi.entities.UserEntity;
import com.lm_api.librarymanagementapi.mapper.UserMapper;
import com.lm_api.librarymanagementapi.repository.LoanRepository;
import com.lm_api.librarymanagementapi.repository.UserRepository;
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
class UserServiceTest {

    public static final String NAME="sadaf";
    public static final String EMAIL="sadaf@gmail";
    public static final Long U_ID=7L;
    public  static final String STATUS="Granted";
    @Mock
    private  UserMapper userMapper;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private LoanRepository loanRepository;
    @InjectMocks
    private UserService  userService;

    @Test
    public void method_should_return_getUserById(){
               UserEntity user=UserEntity.builder().
                id(U_ID).name(NAME).email(EMAIL)
                .membershipStatus(STATUS).build();

        UserResponse response=UserResponse.builder().
                id(U_ID).name(NAME).email(EMAIL)
                .membershipStatus(STATUS).build();
        when(userRepository.findById(U_ID)).thenReturn(Optional.of(user));
        when(userMapper.transformToUserResponse(any(UserEntity.class))).thenReturn(response);

        UserResponse userResponse=userService.getUserById(U_ID);
        assertAll(()-> assertEquals(U_ID,userResponse.id()),
                ()-> assertEquals(NAME,userResponse.name()),
                ()-> assertEquals(EMAIL,userResponse.email()),
                ()-> assertEquals(STATUS,userResponse.membershipStatus()));
        verify(userRepository).findById(U_ID);
        verify(userMapper).transformToUserResponse(user);

    }


    @Test
    public void method_should_return_getAllUser(){
        UserEntity user=UserEntity.builder().
                id(U_ID).name(NAME).email(EMAIL)
                .membershipStatus(STATUS).build();

        UserResponse response=UserResponse.builder().
                id(U_ID).name(NAME).email(EMAIL)
                .membershipStatus(STATUS).build();

        Pageable pageable= PageRequest.of(0,10);
        Page<UserEntity> userEntityPage=new PageImpl<>(List.of(user),pageable,1);
        when(userRepository.findAll(pageable)).thenReturn(userEntityPage);
        when(userMapper.transformToUserResponse(any(UserEntity.class))).thenReturn(response);

        Page<UserResponse> userResponses=userService.getAllUsers(pageable);
        assertNotNull(userResponses);
        assertEquals(1,userResponses.getTotalElements());
        assertEquals(response,userResponses.getContent().get(0));
        verify(userRepository).findAll(pageable);
        verify(userMapper).transformToUserResponse(user);

    }

    @Test
    public void method_should_return_getUserByName(){
        UserEntity user=UserEntity.builder().
                id(U_ID).name(NAME).email(EMAIL)
                .membershipStatus(STATUS).build();

        UserResponse response=UserResponse.builder().
                id(U_ID).name(NAME).email(EMAIL)
                .membershipStatus(STATUS).build();
        when(userRepository.findByNameContainingIgnoreCase(NAME)).thenReturn(List.of(user));
        when(userMapper.transformToUserResponse(any(UserEntity.class))).thenReturn(response);

        List<UserResponse> userResponse=userService.getUserByName(NAME);
        assertAll(()-> assertEquals(U_ID,userResponse.get(0).id()),
                ()-> assertEquals(NAME,userResponse.get(0).name()),
                ()-> assertEquals(EMAIL,userResponse.get(0).email()),
                ()-> assertEquals(STATUS,userResponse.get(0).membershipStatus()));
        verify(userRepository).findByNameContainingIgnoreCase(NAME);
        verify(userMapper).transformToUserResponse(user);

    }

    @Test
    public void method_should_return_userResponse(){
        UserRequest userRequest=UserRequest.builder()
                .name(NAME).email(EMAIL).build();

        UserEntity user=UserEntity.builder().
                id(U_ID).name(NAME).email(EMAIL)
                .membershipStatus(STATUS).build();

        UserResponse response=UserResponse.builder().
                id(U_ID).name(NAME).email(EMAIL)
                .membershipStatus(STATUS).build();

        when(userMapper.transformToUserEntity(userRequest)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.transformToUserResponse(user)).thenReturn(response);

        UserResponse userResponse=userService.userCreation(userRequest);
        assertAll(()-> assertEquals(U_ID,userResponse.id()),
                ()-> assertEquals(NAME,userResponse.name()),
                ()-> assertEquals(EMAIL,userResponse.email()),
                ()-> assertEquals(STATUS,userResponse.membershipStatus()));
        verify(userMapper).transformToUserEntity(userRequest);
        verify(userRepository).save(user);
        verify(userMapper).transformToUserResponse(user);

    }


    @Test
    public void method_should_return_userUpdationResponse(){
        UserRequest userRequest=UserRequest.builder()
                .name(NAME).email(EMAIL).build();

        UserEntity user=UserEntity.builder().
                id(U_ID).name(NAME).email(EMAIL)
                .membershipStatus(STATUS).build();

        UserResponse response=UserResponse.builder().
                id(U_ID).name(NAME).email(EMAIL)
                .membershipStatus(STATUS).build();

        when(userRepository.findById(U_ID)).thenReturn(Optional.of(user));
        when(userMapper.updateUser(user,userRequest)).thenReturn(user);
        when(userMapper.transformToUserResponse(user)).thenReturn(response);

        UserResponse userResponse=userService.userUpdation(U_ID,userRequest);
        assertAll(()-> assertEquals(U_ID,userResponse.id()),
                ()-> assertEquals(NAME,userResponse.name()),
                ()-> assertEquals(EMAIL,userResponse.email()),
                ()-> assertEquals(STATUS,userResponse.membershipStatus()));
        verify(userRepository).findById(U_ID);
        verify(userMapper).updateUser(user,userRequest);
        verify(userRepository).save(user);
        verify(userMapper).transformToUserResponse(user);

    }
    @Test
    public void method_should_return_userDeletionResponse(){
        UserEntity user=UserEntity.builder().
                id(U_ID).name(NAME).email(EMAIL)
                .membershipStatus(STATUS).build();

        UserResponse response=UserResponse.builder().
                id(U_ID).name(NAME).email(EMAIL)
                .membershipStatus(STATUS).build();

        when(userRepository.findById(U_ID)).thenReturn(Optional.of(user));
        when(userMapper.transformToUserResponse(any(UserEntity.class))).thenReturn(response);
        when(loanRepository.countByUserId(U_ID)).thenReturn(0);
        UserResponse userResponse=userService.userDeletion(U_ID);
        verify(userRepository).findById(U_ID);
        verify(userRepository).deleteById(U_ID);
        verify(userMapper).transformToUserResponse(user);
    }


}