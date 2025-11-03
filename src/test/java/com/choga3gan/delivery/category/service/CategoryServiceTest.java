package com.choga3gan.delivery.category.service;

import com.choga3gan.delivery.category.dto.CategoryDto;
import com.choga3gan.delivery.category.exception.DuplicateCategoryNameException;
import com.choga3gan.delivery.category.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.BDDMockito.given;

@SpringBootTest
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("이미 존재하는 카테고리 이름 생성 시 예외 발생")
    void createCategoryTest() {
        CategoryDto categoryDto = new CategoryDto("양식");
        given(categoryRepository.existsByCategoryName("양식")).willReturn(true);

        assertThrows(DuplicateCategoryNameException.class, () -> categoryService.createCategory(categoryDto));
    }
}
