/**
 * @package     com.choga3gan.delivery.product.domain
 * @class       Product
 * @description Product 도메인 Entity 관리
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

package com.choga3gan.delivery.product.domain;

import com.choga3gan.delivery.category.domain.Category;
import com.choga3gan.delivery.global.domain.Auditable;
import com.choga3gan.delivery.store.domain.Store;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "p_product")
@Getter
@NoArgsConstructor
@SQLRestriction("deleted_at is NULL")
public class Product extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private UUID productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private double price;

    private boolean isPublic = true;

    private String productImg;

    private String description;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @OrderColumn(name = "category_order")
    private List<Category> categories = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public Product(String productName, double price, String productImg, String description, List<Category> categories, Store store) {
        this.productName = productName;
        this.price = price;
        this.productImg = productImg;
        this.description = description;
        this.categories = categories;
        this.store = store;
    }

    /**
     * 상품 이름 변경
     *
     * @param productName
     */
    public void changeProductName(String productName) {
        this.productName = productName;
    }

    /**
     * 상품 가격 변경
     *
     * @param price
     */
    public void changePrice(double price) {
        this.price = price;
    }

    /**
     * 상품을 공개 상태로 변경
     *
     */
    public void publish() {
        this.isPublic = true;
    }

    /**
     * 상품을 비공개 상태로 변경
     *
     */
    public void unpublish() {
        this.isPublic = false;
    }

    /**
     * 상품 이미지 변경 및 등록
     *
     * @param productImg
     */
    public void changeProductImg(String productImg) {
        this.productImg = productImg;
    }

    /**
     * 상품 소개 수정 및 등록
     *
     * @param description
     */
    public void changeDescription(String description) {
        this.description = description;
    }

    /**
     * 카테고리 전체 내용 변경
     *
     * @param categories
     */
    public void changeCategory(List<Category> categories) {
        this.categories = categories.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 카테고리 추가
     *
     * @param category
     */
    public void addCategory(Category category) {
        if (categories.stream().noneMatch(c -> c.getCategoryId().equals(category.getCategoryId()))) {
            categories.add(category);
        }
    }

    public void addCategory(List<Category> categories) {
        if (categories == null) return;
        for (Category category : categories) {
            this.addCategory(category);
        }
    }

    /**
     * 카테고리 삭제
     *
     * @param category
     */
    public void removeCategory(Category category) {
        this.categories.remove(category);
    }

    public void removeCategory(List<Category> categories) {
        this.categories.removeAll(categories);
    }
}
