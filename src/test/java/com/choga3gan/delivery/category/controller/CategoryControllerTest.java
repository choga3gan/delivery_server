package com.choga3gan.delivery.category.controller;

import com.choga3gan.delivery.category.domain.Category;
import com.choga3gan.delivery.category.dto.CategoryDto;
import com.choga3gan.delivery.category.repository.CategoryRepository;
import com.choga3gan.delivery.category.service.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();

        Category category1 = new Category("양식");
        Category category2 = new Category("한식");
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        category2.softDelete("test_user");
        categoryRepository.save(category2);
    }

    @Test
    @DisplayName("새로운 카테고리 생성 완료")
    void createCategory() throws Exception {
        CategoryDto categoryRequest = new CategoryDto("분식");
        mockMvc.perform(post("/v1/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryName").value("분식"));
    }

    @Test
    @DisplayName("이미 존재하는 카테고리 생성 시 400 에러 발생")
    void createCategory400() throws Exception {
        CategoryDto categoryRequest = new CategoryDto("양식");
        mockMvc.perform(post("/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("삭제된 카테고리를 생성 시 새롭게 생성")
    void createDeletedCategory() throws Exception {
        CategoryDto categoryRequest = new CategoryDto("한식");
        mockMvc.perform(post("/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryName").value("한식"));
    }

    @Test
    @DisplayName("삭제되지 않은 모든 카테고리 조회")
    void getAllCategories() throws Exception {
        mockMvc.perform(get("/v1/categories"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].categoryName").value("양식"));
    }

    @Test
    @DisplayName("특정 카테고리 조회")
    void getCategory() throws Exception {
        Category category = categoryRepository.save(new Category("중식"));

        mockMvc.perform(get("/v1/categories/{categoryId}", category.getCategoryId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryName").value("중식"));
    }

    @Test
    @DisplayName("특정 카테고리 수정")
    void updateCategory() throws Exception {
        Category category = categoryRepository.save(new Category("디저트"));
        CategoryDto updateRequest = new CategoryDto("디저트/빵");

        mockMvc.perform(patch("/v1/categories/{categoryId}", category.getCategoryId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryName").value("디저트/빵"));
    }

    @Test
    @DisplayName("특정 카테고리 삭제")
    void deleteCategory() throws Exception {
        Category category = categoryRepository.save(new Category("분식"));

        mockMvc.perform(delete("/v1/categories/{categoryId}", category.getCategoryId()))
                .andDo(print())
                .andExpect(status().isNoContent());

    }
}
