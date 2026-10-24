package com.lm_api.librarymanagementapi.integeration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lm_api.librarymanagementapi.dto.BookRequest;
import com.lm_api.librarymanagementapi.entities.CategoryEntity;
import com.lm_api.librarymanagementapi.repository.BookRepository;
import com.lm_api.librarymanagementapi.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BookIntegerationTest {
    public static final String TITLE = "Resolution";
    public static final String AUTHOR = "William gray";
    public static final int STOCK = 10;
    public static final Long YEAR = 1990L;
    public static final Long C_ID = 1L;
    public static final Long B_ID = 9L;
    public static final String STATUS = "Available";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private ObjectMapper objectMapper=new ObjectMapper();

    @Test
    void method_should_return_BookResponse() throws Exception {
        CategoryEntity category = CategoryEntity.builder()
                .name("Programming")
                .book_count(0L)
                .build();

        CategoryEntity savedCategory = categoryRepository.save(category);
        BookRequest bookRequest = BookRequest.builder()
                .title(TITLE)
                .author(AUTHOR)
                .stock(STOCK)
                .publishedYear(YEAR)
                .categoryId(savedCategory.getId())
                .build();

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("book record created successfully"))
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.title").value(TITLE))
                .andExpect(jsonPath("$.data.author").value(AUTHOR))
                .andExpect(jsonPath("$.data.stock").value(STOCK))
                .andExpect(jsonPath("$.data.status").value(STATUS));
    }

}
