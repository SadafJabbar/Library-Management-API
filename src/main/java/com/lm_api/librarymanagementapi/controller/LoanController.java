package com.lm_api.librarymanagementapi.controller;


import com.lm_api.librarymanagementapi.dto.ApiResponse;
import com.lm_api.librarymanagementapi.dto.LoanRequest;
import com.lm_api.librarymanagementapi.dto.LoanResponse;
import com.lm_api.librarymanagementapi.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(final LoanService loanService) {
        this.loanService = loanService;
    }

    @Operation(summary = "Get a loan by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<LoanResponse>> getById(@PathVariable Long id) {
        LoanResponse loanResponse = loanService.getLoanById(id);
        ApiResponse<LoanResponse> apiResponse = ApiResponse.<LoanResponse>builder()
                .success(true).message("Loan record fetched successfully").data(loanResponse).build();
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Get all loans")
    @GetMapping
    public Page<LoanResponse> getAll(Pageable pageable) {
        return loanService.getAllLoans(pageable);
    }

    @Operation(summary = "Create a new loan")
    @PostMapping
    public ResponseEntity<ApiResponse<LoanResponse>> createLoan(@Valid @RequestBody LoanRequest loanRequest) {
        LoanResponse loanResponse = loanService.loanCreation(loanRequest);
        ApiResponse<LoanResponse> apiResponse = ApiResponse.<LoanResponse>builder()
                .success(true).message("Loan record created successfully").data(loanResponse).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

    }

    @Operation(summary = "Delete a loan")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<LoanResponse>> delLoan(@PathVariable Long id) {
        LoanResponse loanResponse = loanService.loanDeletion(id);
        ApiResponse<LoanResponse> apiResponse = ApiResponse.<LoanResponse>builder()
                .success(true).message("Loan record deleted successfully").data(loanResponse).build();
        return ResponseEntity.ok(apiResponse);
    }
}
