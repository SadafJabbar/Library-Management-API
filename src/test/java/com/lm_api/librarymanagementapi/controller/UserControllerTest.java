package com.lm_api.librarymanagementapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lm_api.librarymanagementapi.configuration.CacheConfig;
import com.lm_api.librarymanagementapi.dto.UserRequest;
import com.lm_api.librarymanagementapi.dto.UserResponse;
import com.lm_api.librarymanagementapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(CacheConfig.class)
class UserControllerTest {

    public static final String NAME="sadaf";
    public static final String EMAIL="sadaf@gmail";
    public static final Long U_ID=7L;
    public  static final String STATUS="Granted";

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper=new ObjectMapper();

    @MockitoBean
    private UserService userService;

    @Test
    public void method_should_return_UserID_response() throws Exception {

        UserResponse response = UserResponse.builder()
                .id(U_ID)
                .name(NAME)
                .email(EMAIL)
                .membershipStatus(STATUS)
                .build();

        when(userService.getUserById(U_ID)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/" + U_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message")
                        .value("user record fetched successfully"))
                .andExpect(jsonPath("$.data.id").value(U_ID))
                .andExpect(jsonPath("$.data.name").value(NAME))
                .andExpect(jsonPath("$.data.email").value(EMAIL))
                .andExpect(jsonPath("$.data.membershipStatus").value(STATUS));
    }
    @Test
    public void method_should_return_allUsers() throws Exception {

        UserResponse response = UserResponse.builder()
                .id(U_ID)
                .name(NAME)
                .email(EMAIL)
                .membershipStatus(STATUS)
                .build();
        Page<UserResponse> page=new PageImpl<>(List.of(response));
        when(userService.getAllUsers(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users" ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(U_ID))
                .andExpect(jsonPath("$.content[0].name").value(NAME))
                .andExpect(jsonPath("$.content[0].email").value(EMAIL))
                .andExpect(jsonPath("$.content[0].membershipStatus").value(STATUS));
    }


    @Test
    public void method_should_return_responseByName() throws Exception {

        UserResponse response = UserResponse.builder()
                .id(U_ID)
                .name(NAME)
                .email(EMAIL)
                .membershipStatus(STATUS)
                .build();

        when(userService.getUserByName(NAME)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/users/name").param("name", NAME))                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("records fetched successfully by name"))
                .andExpect(jsonPath("$.data[0].id").value(U_ID))
                .andExpect(jsonPath("$.data[0].name").value(NAME))
                .andExpect(jsonPath("$.data[0].email").value(EMAIL))
                .andExpect(jsonPath("$.data[0].membershipStatus").value(STATUS));
    }

    @Test
    public  void method_should_return_UserCreation() throws Exception{
        UserRequest userRequest= UserRequest.builder()
                .name(NAME).email(EMAIL).build();

        UserResponse response=UserResponse.builder().
                id(U_ID).name(NAME).email(EMAIL)
                .membershipStatus(STATUS).build();

        when(userService.userCreation(userRequest)).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message")
                        .value("user record created successfully"))
                .andExpect(jsonPath("$.data.id").value(U_ID))
                .andExpect(jsonPath("$.data.name").value(NAME))
                .andExpect(jsonPath("$.data.email").value(EMAIL))
                .andExpect(jsonPath("$.data.membershipStatus").value(STATUS));
}
    @Test
    public void method_should_return_updatedUser() throws Exception {

        UserRequest userRequest= UserRequest.builder()
                .name("salma").email("salma@gmail").build();

        UserResponse response = UserResponse.builder()
                .id(U_ID)
                .name("salma")
                .email("salma@gmail")
                .membershipStatus(STATUS)
                .build();

        when(userService.userUpdation(eq(U_ID),any(UserRequest.class))).thenReturn(response);

        mockMvc.perform(put("/api/v1/users/"+U_ID).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("user record updated successfully"))
                .andExpect(jsonPath("$.data.id").value(U_ID))
                .andExpect(jsonPath("$.data.name").value("salma"))
                .andExpect(jsonPath("$.data.email").value("salma@gmail"))
                .andExpect(jsonPath("$.data.membershipStatus").value(STATUS));
    }
    @Test
    public void method_should_return_deletedResponse() throws Exception {

        UserResponse response = UserResponse.builder()
                .id(U_ID)
                .name(NAME)
                .email(EMAIL)
                .membershipStatus(STATUS)
                .build();

        when(userService.userDeletion(U_ID)).thenReturn(response);

        mockMvc.perform(delete("/api/v1/users/"+U_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("user record deleted successfully"))
                .andExpect(jsonPath("$.data.id").value(U_ID))
                .andExpect(jsonPath("$.data.name").value(NAME))
                .andExpect(jsonPath("$.data.email").value(EMAIL))
                .andExpect(jsonPath("$.data.membershipStatus").value(STATUS));
    }

}