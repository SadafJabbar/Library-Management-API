package com.lm_api.librarymanagementapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lm_api.librarymanagementapi.configuration.CacheConfig;
import com.lm_api.librarymanagementapi.dto.LoanRequest;
import com.lm_api.librarymanagementapi.dto.LoanResponse;
import com.lm_api.librarymanagementapi.service.LoanService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoanController.class)
@Import(CacheConfig.class)
class LoanControllerTest {
    public static final Long U_ID=8L;
    public static final Long B_ID=4L;
    public static final Long L_ID=4L;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper=new ObjectMapper();
    @MockitoBean
    private LoanService loanService;

    @Test
    public void method_should_return_LoanResponse() throws Exception{
        LoanRequest loanRequest=LoanRequest.builder()
                .userId(U_ID).bookId(B_ID).build();

        LoanResponse loanResponse=LoanResponse.builder().id(L_ID)
                .UserId(U_ID).bookId(B_ID).build();

    when(loanService.loanCreation(loanRequest)).thenReturn(loanResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/loans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loanRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Loan record created successfully"))
                .andExpect(jsonPath("$.data.id").value(L_ID))
                .andExpect(jsonPath("$.data.UserId").value(U_ID))
                .andExpect(jsonPath("$.data.bookId").value(B_ID));

    }
    @Test
    public void method_should_return_LoanIDResponse() throws Exception{
        LoanResponse loanResponse=LoanResponse.builder().id(L_ID)
                .UserId(U_ID).bookId(B_ID).build();

        when(loanService.getLoanById(L_ID)).thenReturn(loanResponse);

        mockMvc.perform(get("/api/v1/loans/"+L_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanResponse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Loan record fetched successfully"))
                .andExpect(jsonPath("$.data.id").value(L_ID))
                .andExpect(jsonPath("$.data.UserId").value(U_ID))
                .andExpect(jsonPath("$.data.bookId").value(B_ID));

    }
    @Test
    public void method_should_return_allLoans() throws Exception{
        LoanResponse loanResponse=LoanResponse.builder().id(L_ID)
                .UserId(U_ID).bookId(B_ID).build();

        Page<LoanResponse> page=new PageImpl<>(List.of(loanResponse));
        when(loanService.getAllLoans(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/loans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(L_ID))
                .andExpect(jsonPath("$.content[0].UserId").value(U_ID))
                .andExpect(jsonPath("$.content[0].bookId").value(B_ID));

    }

    @Test
    public void method_should_return_deletedLoan() throws Exception{
        LoanResponse loanResponse=LoanResponse.builder().id(L_ID)
                .UserId(U_ID).bookId(B_ID).build();

        when(loanService.loanDeletion(L_ID)).thenReturn(loanResponse);

        mockMvc.perform(delete("/api/v1/loans/"+L_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Loan record deleted successfully"))
                .andExpect(jsonPath("$.data.id").value(L_ID))
                .andExpect(jsonPath("$.data.UserId").value(U_ID))
                .andExpect(jsonPath("$.data.bookId").value(B_ID));

    }
}