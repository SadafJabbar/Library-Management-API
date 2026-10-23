package com.lm_api.librarymanagementapi.mapper;

import com.lm_api.librarymanagementapi.dto.UserRequest;
import com.lm_api.librarymanagementapi.dto.UserResponse;
import com.lm_api.librarymanagementapi.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {
    public static final String NAME="sadaf";
    public static final String EMAIL="sadaf@gmail";
    public static final Long U_ID=7L;
    public  static final String STATUS="Granted";
    @Autowired
    private final UserMapper userMapper=new UserMapper();

    @Test
    public void should_return_userResponse(){
        UserEntity userEntity=UserEntity.builder().
                id(U_ID).name(NAME).email(EMAIL)
                .membershipStatus(STATUS).build();
        UserResponse response1=userMapper.transformToUserResponse(userEntity);
        assertEquals(U_ID,response1.id());
        assertEquals(NAME,response1.name());
        assertEquals(EMAIL,response1.email());
        assertEquals(STATUS,response1.membershipStatus());
    }


    @Test
    public void should_return_userEntity(){
        UserRequest userRequest=UserRequest.builder()
                .name(NAME).email(EMAIL).build();
        UserEntity user=userMapper.transformToUserEntity(userRequest);
        assertEquals(NAME,user.getName());
        assertEquals(EMAIL,user.getEmail());
        assertEquals(STATUS,user.getMembershipStatus());

    }


    @Test
    public void shoul_return_userUpdation(){
        UserEntity userEntity=UserEntity.builder().
                id(U_ID).name(NAME).email(EMAIL)
                .membershipStatus(STATUS).build();
        UserRequest userRequest=UserRequest.builder()
                .name("john").email("john@gmail").build();
        UserEntity user=userMapper.updateUser(userEntity,userRequest);
        assertEquals("john",user.getName());
        assertEquals("john@gmail",user.getEmail());
    }



}