package com.lm_api.librarymanagementapi.mapper;

import com.lm_api.librarymanagementapi.dto.UserRequest;
import com.lm_api.librarymanagementapi.dto.UserResponse;
import com.lm_api.librarymanagementapi.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse transformToUserResponse(UserEntity userEntity){
        return new UserResponse(
                userEntity.getId(),
                userEntity.getName(),
                userEntity.getEmail(),
                userEntity.getRole(),
                userEntity.getMembershipStatus()
        );

    }

    public UserEntity transformToUserEntity(UserRequest userRequest){
        return UserEntity.builder()
                .name(userRequest.name())
                .email(userRequest.email())
                .role(userRequest.role())
                .password(userRequest.password())
                .membershipStatus("Granted").build();
    }


   public UserEntity updateUser(UserEntity user, UserRequest userRequest){
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setRole(userRequest.role());
        return user;
    }
}
