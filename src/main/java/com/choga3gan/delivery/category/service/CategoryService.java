/**
 * @package     com.choga3gan.delivery.category.service
 * @class       CategoryService
 * @description 카테고리 CRUD 서비스 개발을 위한 인터페이스 구현
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

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    // CREATE
    Category createCategory(CategoryDto categoryDto);

    // READ
    Category getCategory(UUID categoryId);
    List<Category> getAllCategories();

    // Update
    Category updateCategory(CategoryDto categoryDto);

    // Delete
    void deleteCategory(UUID categoryId);
}
