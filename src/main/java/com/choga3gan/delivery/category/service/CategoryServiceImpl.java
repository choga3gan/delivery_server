/**
 * @package     com.choga3gan.delivery.category.service
 * @class       CategoryServiceImpl
 * @description CategoryService 구현체
 *
 * @author      jinnk0
 * @since       2025. 10. 31.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 10. 31.        jinnk0       최초 생성
 * </pre>
 */

package com.choga3gan.delivery.category.service;

import com.choga3gan.delivery.category.domain.Category;
import com.choga3gan.delivery.category.dto.CategoryDto;
import com.choga3gan.delivery.category.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    /**
     * 신규 카테고리 생성 메서드
     *
     * @param categoryDto 카테고리 이름
     * @return 생성된 Category 객체
     */
    @Override
    public Category createCategory(CategoryDto categoryDto) {
        Category category = new Category(categoryDto.getCategoryName());
        return categoryRepository.save(category);
    }

    /**
     * category_id로 특정 카테고리 조회
     *
     * @param categoryId
     * @return 조회한 Category 객체
     */
    @Override
    public Category getCategory(UUID categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    /**
     * 모든 카테고리 조회
     *
     * @return 테이블에 존재하는 모든 Category 리스트
     */
    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll(); // TODO : 삭제된 카테고리 조회 안되도록
    }

    /**
     * 카테고리 이름 수정
     *
     * @param categoryId, categoryDto 카테고리 id, 수정된 카테고리 이름
     * @return 수정된 Category 객체
     */
    @Override
    public Category updateCategory(UUID categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        category.changeCategoryName(categoryDto.getCategoryName());
        return category;
    }

    /**
     * 카테고리 삭제
     *
     * @param categoryId
     */
    @Override
    public void deleteCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        // category.softDelete();
    }
}
