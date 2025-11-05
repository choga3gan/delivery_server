/**
 * @package     com.choga3gan.delivery.product.service
 * @class       ProductServiceImpl
 * @description Product Service 로직 구현
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

import com.choga3gan.delivery.category.domain.Category;
import com.choga3gan.delivery.category.repository.CategoryRepository;
import com.choga3gan.delivery.global.utils.service.SecurityUtilService;
import com.choga3gan.delivery.product.domain.Product;
import com.choga3gan.delivery.product.dto.DescriptionRequest;
import com.choga3gan.delivery.product.dto.ProductRequest;
import com.choga3gan.delivery.product.repository.ProductRepository;
import com.choga3gan.delivery.store.domain.Store;
import com.choga3gan.delivery.store.exception.StoreNotFoundException;
import com.choga3gan.delivery.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final SecurityUtilService securityUtilService;

    /**
     * 매장에 신규 상품 추가
     *
     * @param  productRequest
     * @return Product
     */
    @Override
    public Product createProduct(UUID storeId, ProductRequest productRequest) {
        Store store = storeRepository.findByStoreId(storeId).orElseThrow(StoreNotFoundException::new);

        List<Category> categories = categoryRepository.findAllByCategoryIdIn(productRequest.getCategoryIds());

        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .categories(categories)
                .price(productRequest.getPrice())
                .description(productRequest.getDescription())
                .store(store)
                .productImg(productRequest.getProductImg())
                .build();

        return productRepository.save(product);
    }

    /**
     * 매장의 모든 상품 목록 조회
     * - Page 객체와 Pageable을 활용하여 페이지 형태로 반환
     *
     * @param  storeId
     * @return Product 객체를 페이지 형태로 반환
     */
    @Override
    public Page<Product> getProducts(UUID storeId) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        return productRepository.findAllByStore_StoreId(storeId, pageable);
    }

    /**
     * 단일 상품의 상세 정보 조회
     *
     * @param  storeId, productId
     * @return Product
     */
    @Override
    public Product getProduct(UUID storeId, UUID productId) {
        return productRepository.findByStore_StoreIdAndProductId(storeId, productId).orElseThrow(StoreNotFoundException::new);
    }

    /**
     * 매장 구분 없이 db의 모든 상품 조회
     * - Page 객체와 Pageable을 활용하여 페이지 형태로 반환
     *
     * @return Product 객체를 페이지 형태로 반환
     */
    @Override
    public Page<Product> getProductsForManager() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        return productRepository.findAll(pageable);
    }

    /**
     * 상품 정보 수정
     * - requestDto에 값이 있을 경우에만 수정
     *
     * @param  storeId, productId
     * @return Product
     */
    @Override
    public Product updateProduct(UUID storeId, UUID productId, ProductRequest productRequest) {
        Product product = productRepository.findByStore_StoreIdAndProductId(storeId, productId).orElseThrow(StoreNotFoundException::new);
        if (productRequest.getProductImg() != null) {
            product.changeProductImg(productRequest.getProductImg());
        }
        if (productRequest.getDescription() != null) {
            product.changeDescription(productRequest.getDescription());
        }
        if (productRequest.getCategoryIds() != null && !productRequest.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllByCategoryIdIn(productRequest.getCategoryIds());
            product.changeCategory(categories);
        }
        if (productRequest.getProductName() != null) {
            product.changeProductName(productRequest.getProductName());
        }
        if (productRequest.getPrice() != null) {
            product.changePrice(productRequest.getPrice());
        }
        if (productRequest.getOpen() != null) {
            if (productRequest.getOpen()) {
                product.publish();
            } else {
                product.unpublish();
            }
        }
        return productRepository.save(product);
    }

    /**
     * 상품 소개 AI를 통해 생성
     *
     * @param  productId, request
     * @return 상품 소개 문자열
     */
    @Override
    public String addDescriptionFromAI(UUID productId, DescriptionRequest descriptionRequest) {
        return "description"; // TODO: AI 연동 및 요청 질문과 요청 문구 저장 (테이블 생성 필요)
    }

    /**
     * 상품 삭제
     * - soft delete로 삭제 처리
     *
     * @param storeId, productId
     */
    @Override
    public void removeProduct(UUID storeId, UUID productId) {
        Product product = productRepository.findByStore_StoreIdAndProductId(storeId, productId)
                .orElseThrow(StoreNotFoundException::new);
        product.softDelete(securityUtilService.getCurrentUsername());
    }
}
