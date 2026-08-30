package com.lm_api.librarymangementapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lm_api.librarymangementapi.configuration.CacheConfig;
import com.lm_api.librarymangementapi.dto.CategoryRequest;
import com.lm_api.librarymangementapi.entities.CategoryEntity;
import com.lm_api.librarymangementapi.service.CategoryService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@Import(CacheConfig.class)
class CategoryControllerTest {
    public static final String NAME="Programming";
    public static final Long C_Id=4L;
    public static final Long COUNT=9L;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper=new ObjectMapper();

    @MockitoBean
    private CategoryService categoryService;

    @Test
    public void method_should_return_categoryIdResponse() throws Exception{
        CategoryEntity categoryResponse=CategoryEntity.builder().
                id(C_Id).name(NAME).book_count(COUNT).build();

        when(categoryService.getCategoryById(C_Id)).thenReturn(categoryResponse);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories/"+C_Id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryResponse)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Category Record fetched successfully"))
                .andExpect(jsonPath("$.data.id").value(C_Id))
                .andExpect(jsonPath("$.data.name").value(NAME))
                .andExpect(jsonPath("$.data.book_count").value(COUNT));




    }

    @Test
    public void should_return_all_categories() throws Exception {

        CategoryEntity categoryResponse = CategoryEntity.builder()
                .id(C_Id)
                .name(NAME)
                .book_count(COUNT)
                .build();

        Page<CategoryEntity> page =
                new PageImpl<>(List.of(categoryResponse));

        when(categoryService.getAllCategories(any(Pageable.class)))
                .thenReturn(page);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(C_Id))
                .andExpect(jsonPath("$.content[0].name").value(NAME))
                .andExpect(jsonPath("$.content[0].book_count").value(COUNT));
    }

    @Test
    public void method_should_return_categoryCreation() throws Exception{
        CategoryRequest categoryRequest= CategoryRequest.builder().name(NAME).build();

        CategoryEntity categoryResponse=CategoryEntity.builder().
                id(C_Id).name(NAME).book_count(COUNT).build();

        when(categoryService.categoryCreation(categoryRequest)).thenReturn(categoryResponse);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Category Record created successfully"))
                .andExpect(jsonPath("$.data.id").value(C_Id))
                .andExpect(jsonPath("$.data.name").value(NAME))
                .andExpect(jsonPath("$.data.book_count").value(COUNT));




    }

    @Test
    public void method_should_return_categoryUpdation() throws Exception{
        CategoryRequest categoryRequest= CategoryRequest.builder().name("science").build();

        CategoryEntity categoryResponse=CategoryEntity.builder().
                id(C_Id).name("science").book_count(10L).build();

        when(categoryService.categoryUpdation(eq(C_Id),any(CategoryRequest.class))).thenReturn(categoryResponse);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/categories/"+C_Id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Category Record updated successfully"))
                .andExpect(jsonPath("$.data.id").value(C_Id))
                .andExpect(jsonPath("$.data.name").value("science"))
                .andExpect(jsonPath("$.data.book_count").value(10L));




    }

    @Test
    public void method_should_return_categoryDeletion() throws Exception{
        CategoryRequest categoryRequest= CategoryRequest.builder().name(NAME).build();

        CategoryEntity categoryResponse=CategoryEntity.builder().
                id(C_Id).name(NAME).book_count(COUNT).build();

        when(categoryService.categoryDeletion(C_Id)).thenReturn(categoryResponse);
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/categories/"+C_Id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Category Record deleted successfully"))
                .andExpect(jsonPath("$.data.id").value(C_Id))
                .andExpect(jsonPath("$.data.name").value(NAME))
                .andExpect(jsonPath("$.data.book_count").value(COUNT));




    }

}