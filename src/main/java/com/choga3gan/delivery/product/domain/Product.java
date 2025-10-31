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

import java.util.UUID;

@Entity
@Table(name = "p_product")
@Getter
@NoArgsConstructor
public class Product extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private double price;

    private boolean isPublic = true;

    private String productImg;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Builder
    public Product(String productName, double price, String productImg, String description, Category category, Store store) {
        this.productName = productName;
        this.price = price;
        this.productImg = productImg;
        this.description = description;
        this.category = category;
        this.store = store;
    }

    public void changeProductName(String productName) {
        this.productName = productName;
    }

    public void changePrice(double price) {
        this.price = price;
    }

    // 상품을 공개 상태로 변경
    public void publish() {
        this.isPublic = true;
    }

    // 상품을 비공개 상태로 변경
    public void unpublish() {
        this.isPublic = false;
    }

    public void changeProductImg(String productImg) {
        this.productImg = productImg;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeCategory(Category category) {
        this.category = category;
    }
}
