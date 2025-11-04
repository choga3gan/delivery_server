/**
 * @package     com.choga3gan.delivery.category.repository
 * @class       CategoryRepository
 * @description JPA를 활용하여 Category 테이블 조작을 하기 위한 Repository
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

package com.choga3gan.delivery.category.repository;

import com.choga3gan.delivery.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByCategoryId(UUID categoryId);
    List<Category> findAll();
    boolean existsByCategoryName(String categoryName);
}
