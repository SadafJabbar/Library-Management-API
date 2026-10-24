package com.lm_api.librarymanagementapi.service;


import com.lm_api.librarymanagementapi.entities.UserEntity;
import com.lm_api.librarymanagementapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public CustomUserDetailService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        UserEntity userEntity=userRepository.findByName(username)
                .orElseThrow(()-> new UsernameNotFoundException(username));
        return User.builder()
                .username(username)
                .password(userEntity.getPassword())
                .roles(userEntity.getRole().name()).build();
    }
}
