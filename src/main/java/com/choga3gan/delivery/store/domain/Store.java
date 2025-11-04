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
import com.choga3gan.delivery.store.exception.StoreNotEditableException;
import com.choga3gan.delivery.store.util.OwnerRoleCheck;
import com.choga3gan.delivery.store.util.RoleCheck;
import com.choga3gan.delivery.store.util.StaffConverter;
import com.choga3gan.delivery.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "p_store")
@Getter
@NoArgsConstructor
public class Store extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID storeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "p_category",
            joinColumns = @JoinColumn(name = "store_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @OrderColumn(name = "category_order")
    private List<Category> categories = new ArrayList<>();

    @Column(nullable = false)
    private String storeName;

    @Column(name = "is_closed")
    private boolean closed = true;

    private double ratingAvg = 0.0;

    @Column(nullable = false)
    private String address;

    @Convert(converter = StaffConverter.class)
    private Set<Staff> staffs;

    @Embedded
    private ServiceTime serviceTime;

    @Column(nullable = false)
    private String telNum;

    private int reviewCount;

    @Builder
    public Store(List<Category> categories, String storeName, String address, ServiceTime serviceTime, String telNum) {
        this.categories = categories;
        this.storeName = storeName;
        this.address = address;
        this.serviceTime = serviceTime;
        this.telNum = telNum;
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

    /**
     * 매장 이름 변경
     *
     * @param storeName
     */
    public void changeStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * 매장 오픈
     *
     */
    public void open(OwnerRoleCheck roleCheck) {
        roleCheck.check(this);
        this.closed = false;
    }

    /**
     * 매장 마감
     *
     */
    public void close(OwnerRoleCheck roleCheck) {
        roleCheck.check(this);
        this.closed = true;
    }

    /**
     * 리뷰 작성 시 매장 평점 평균 업데이트
     *
     * @param rating
     */
    public void updateRatingAvg(double rating) {
        this.ratingAvg = (ratingAvg * reviewCount + rating) / (reviewCount + 1);
        this.reviewCount += 1;
    }

    public void changeAddress(String address) {
        this.address = address;
    }

    /**
     * 직원 추가
     * OWNER 권한을 보유한 사람만 수행 가능
     *
     * @param staffs
     */
    public void addStaff(Collection<Staff> staffs, RoleCheck roleCheck) {
        if (!roleCheck.check(this)) {
            throw new StoreNotEditableException();
        }
        this.staffs = Objects.requireNonNullElseGet(this.staffs, HashSet::new);
        staffs.addAll(staffs);
        // TODO : 추가된 사람이 STAFF 권한을 갖게 됨
    }


    public void addStaff(Staff staff, RoleCheck roleCheck) {
        addStaff(Set.of(staff), roleCheck);
    }

    /**
     * 직원 제거
     * OWNER 권한을 보유한 사람만 수행 가능
     *
     * @param staffs
     */
    public void removeStaff(Collection<Staff> staffs, RoleCheck roleCheck) {
        if (roleCheck.check(this)) {
            throw new StoreNotEditableException();
        }
        staffs.removeAll(staffs);
    }

    public void removeStaff(Staff staff, RoleCheck roleCheck) {
        removeStaff(Set.of(staff), roleCheck);
    }

    /**
     * 매장 운영 시간 설정
     *
     * @param startTime 운영 시작 시간, endTime 운영 마감 시간
     */
    public void changeServiceTime(LocalTime startTime, LocalTime endTime) {
        this.serviceTime = new ServiceTime(startTime, endTime);
    }

    public void changeTelNum(String telNum) {
        this.telNum = telNum;
    }
}
