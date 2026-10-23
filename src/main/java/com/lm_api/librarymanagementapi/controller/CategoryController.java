package com.lm_api.librarymanagementapi.controller;


import com.lm_api.librarymanagementapi.dto.ApiResponse;
import com.lm_api.librarymanagementapi.dto.CategoryRequest;
import com.lm_api.librarymanagementapi.entities.CategoryEntity;
import com.lm_api.librarymanagementapi.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    public final CategoryService categoryService;
    @Autowired
    public CategoryController(final CategoryService categoryService){
        this.categoryService=categoryService;
    }

    @Operation(summary = "Get a category by ID")    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryEntity>> getById(@PathVariable Long id){
        CategoryEntity category=categoryService.getCategoryById(id);
        ApiResponse<CategoryEntity> apiResponse=ApiResponse.<CategoryEntity>builder()
                .success(true).message("Category Record fetched successfully").
                data(category).build();
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Get all categories")    @GetMapping
    public Page<CategoryEntity> getAll(Pageable pageable){
        return categoryService.getAllCategories(pageable);
    }

    @Operation(summary = "Create a new category")    @PostMapping
    public ResponseEntity<ApiResponse<CategoryEntity>> createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        CategoryEntity category= categoryService.categoryCreation(categoryRequest);
        ApiResponse<CategoryEntity> apiResponse=ApiResponse.<CategoryEntity>builder()
                .success(true).message("Category Record created successfully").
                data(category).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Operation(summary = "Update a category")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryEntity>> updateCategory(@PathVariable Long id,
                                   @Valid @RequestBody CategoryRequest categoryRequest){
        CategoryEntity category= categoryService.categoryUpdation(id,categoryRequest);
        ApiResponse<CategoryEntity> apiResponse=ApiResponse.<CategoryEntity>builder()
                .success(true).message("Category Record updated successfully").
                data(category).build();
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Delete a category")    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryEntity>> delCategory(@PathVariable Long id){
        CategoryEntity categoryEntity=categoryService.categoryDeletion(id);
        ApiResponse<CategoryEntity> apiResponse= ApiResponse.<CategoryEntity>builder()
                .success(true).message("Category Record deleted successfully").
                data(categoryEntity).build();
        return ResponseEntity.ok(apiResponse);
    }
}
