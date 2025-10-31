/**
 * @package     com.choga3gan.delivery.store.domain
 * @class       Store
 * @description Store 도메인 Entity 생성
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

package com.choga3gan.delivery.store.domain;

import com.choga3gan.delivery.category.domain.Category;
import com.choga3gan.delivery.global.domain.Auditable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "p_store")
@Getter
@NoArgsConstructor
public class Store extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID storeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(nullable = false)
    private String storeName;

    @Column(name = "is_closed")
    private boolean isClosed = true;

    private double ratingAvg = 0.0;

    @Column(nullable = false)
    private String address;

    @Setter
    @Column(nullable = false)
    private String serviceTime;

    @Column(nullable = false)
    private String telNum;

    @Builder
    public Store(Category category, String storeName, String address, String serviceTime, String telNum) {
        this.category = category;
        this.storeName = storeName;
        this.address = address;
        this.serviceTime = serviceTime;
        this.telNum = telNum;
    }

    public void changeCategory(Category category) {
        this.category = category;
    }

    public void changeStoreName(String storeName) {
        this.storeName = storeName;
    }

    // 매장 open 상태로 변경
    public void open() {
        this.isClosed = false;
    }

    // 매장 close 상태로 변경
    public void close() {
        this.isClosed = true;
    }

    // 리뷰 작성 시 매장 평점 평균 업데이트
    public void updateRatingAvg(double ratingAvg) {
        this.ratingAvg = ratingAvg;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    public void changeServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void changeTelNum(String telNum) {
        this.telNum = telNum;
    }
}
