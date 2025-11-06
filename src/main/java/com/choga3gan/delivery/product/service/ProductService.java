/**
 * @package     com.choga3gan.delivery.product.service
 * @class       ProductService
 * @description Product Service 구현을 위한 인터페이스 정의
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

package com.choga3gan.delivery.product.service;

import com.choga3gan.delivery.product.domain.Product;
import com.choga3gan.delivery.product.dto.DescriptionRequest;
import com.choga3gan.delivery.product.dto.ProductRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProductService {
    // CREATE
    Product createProduct(UUID storeId, ProductRequest productRequest);

    // READ
    Page<Product> getProducts(UUID storeId);
    Product getProduct(UUID storeId, UUID productId);
    Page<Product> getProductsForManager();

    // UPDATE
    Product updateProduct(UUID storeId, UUID productId, ProductRequest productRequest);
    String addDescriptionFromAI(UUID productId, DescriptionRequest descriptionRequest);

    // DELETE
    void removeProduct(UUID storeId, UUID productId);
}
