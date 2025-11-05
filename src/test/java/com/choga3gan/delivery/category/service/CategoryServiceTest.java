package com.choga3gan.delivery.category.service;

import com.choga3gan.delivery.category.domain.Category;
import com.choga3gan.delivery.category.dto.CategoryDto;
import com.choga3gan.delivery.category.exception.DuplicateCategoryNameException;
import com.choga3gan.delivery.category.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository.deleteAll();

        Category category1 = new Category("양식");
        Category category2 = new Category("한식");
        categoryRepository.save(category1);
        categoryRepository.save(category2);
        category2.softDelete("test_user");
    }

    @Test
    @DisplayName("이미 존재하는 카테고리 이름 생성 시 예외 발생")
    void createCategoryTest() {
        CategoryDto categoryDto = new CategoryDto("양식");

        assertThrows(DuplicateCategoryNameException.class, () -> categoryService.createCategory(categoryDto));
    }

    @Test
    @DisplayName("모든 카테고리 조회 시 삭제된 카테고리는 조회되지 않는다")
    void findAllExcludingDeletedTest() {
        List<Category> results = categoryRepository.findAll();
        assertEquals(1, results.size());
        assertEquals("양식", results.getFirst().getCategoryName());
    }
}
