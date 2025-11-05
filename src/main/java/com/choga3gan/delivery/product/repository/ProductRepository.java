/**
 * @package     com.choga3gan.delivery.product.repository
 * @class       ProductRepository
 * @description 상품 데이터 접근을 위한 repository
 *
 * @author      jinnk0
 * @since       2025. 11. 5.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 5.        jinnk0       최초 생성
 * </pre>
 */

package com.choga3gan.delivery.product.repository;

import com.choga3gan.delivery.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findAllByStore_StoreId(UUID storeId, Pageable pageable);
    Optional<Product> findByStore_StoreIdAndProductId(UUID storeId, UUID productId);
    Page<Product> findAll(Pageable pageable);
}
