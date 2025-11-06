/**
 * @package     com.choga3gan.delivery.store.service
 * @class       StoreServiceImpl
 * @description Store Service 구현체
 *
 * @author      jinnk0
 * @since       2025. 11. 4.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 4.        jinnk0       최초 생성
 * </pre>
 */

package com.choga3gan.delivery.store.service;

import com.choga3gan.delivery.category.domain.Category;
import com.choga3gan.delivery.category.repository.CategoryRepository;
import com.choga3gan.delivery.global.utils.service.SecurityUtilService;
import com.choga3gan.delivery.store.domain.ServiceTime;
import com.choga3gan.delivery.store.domain.Staff;
import com.choga3gan.delivery.store.domain.Store;
import com.choga3gan.delivery.store.dto.ServiceTimeDto;
import com.choga3gan.delivery.store.dto.StaffDto;
import com.choga3gan.delivery.store.dto.StoreRequest;
import com.choga3gan.delivery.store.exception.StoreNotFoundException;
import com.choga3gan.delivery.store.repository.StoreRepository;
import com.choga3gan.delivery.user.domain.Role;
import com.choga3gan.delivery.user.domain.User;
import com.choga3gan.delivery.user.domain.UserId;
import com.choga3gan.delivery.user.exception.UserNotFoundException;
import com.choga3gan.delivery.user.repository.UserRepository;
import com.choga3gan.delivery.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final SecurityUtilService securityUtil;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    /**
     * 신규 매장 생성
     *
     * @param request 신규 매장 생성 정보
     * @return Store 객체
     */
    @Override
    public Store createStore(StoreRequest request) {
        User user = userRepository.findByUsername(securityUtil.getCurrentUsername())
                .orElseThrow(UserNotFoundException::new);

        List<Category> categories = categoryRepository.findAllByCategoryIdIn(request.getCategoryIds());

        ServiceTime serviceTime = ServiceTime.builder()
                .startTime(request.getServiceTime().getStartTime())
                .endTime(request.getServiceTime().getEndTime())
                .build();

        Store store = Store.builder()
                .categories(categories)
                .storeName(request.getStoreName())
                .address(request.getAddress())
                .serviceTime(serviceTime)
                .telNum(request.getTelNum())
                .user(user)
                .build();

        userService.changeUserRole(user.getUsername(), "ROLE_OWNER");

        return storeRepository.save(store);
    }

    /**
     * 단일 매장 상세 정보 조회
     *
     * @param storeId
     * @return Store 객체
     */
    @Override
    public Store getStore(UUID storeId) {
        return storeRepository.findByStoreId(storeId).orElseThrow(StoreNotFoundException::new);
    }

    /**
     * 모든 매장 목록 조회
     * - Page 객체와 Pageable 설정을 활용하여 페이지 형태로 반환
     *
     * @return Page<Store>
     */
    @Override
    public Page<Store> getStores() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        return storeRepository.findAll(pageable);
    }

    /**
     * 매장의 직원 목록 조회
     *
     * @param  storeId
     * @return 직원 목록 반환
     */
    @Override
    @CheckStoreAccess
    public Set<Staff> getStaff(UUID storeId) {
        Store store = storeRepository.findByStoreId(storeId).orElseThrow(StoreNotFoundException::new);
        return store.getStaffs();
    }

    /**
     * 매장 정보 수정
     * - 수정하고자 하는 매장의 storeId를 받아 request에 작성된 내용으로 수정
     * - null인 필드는 수정되지 않음
     *
     * @param  storeId, request
     * @return Store 객체
     */
    @Override
    @CheckStoreAccess
    public Store updateStore(UUID storeId, StoreRequest request) {
        Store store = storeRepository.findByStoreId(storeId).orElseThrow(StoreNotFoundException::new);
        if (request.getStoreName() != null) {
            store.changeStoreName(request.getStoreName());
        }
        if (request.getAddress() != null) {
            store.changeAddress(request.getAddress());
        }
        if (request.getTelNum() != null) {
            store.changeTelNum(request.getTelNum());
        }
        if (request.getCategoryIds() != null && !request.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllByCategoryIdIn(request.getCategoryIds());
            store.changeCategory(categories);
        }
        if (request.getServiceTime() != null) {
            ServiceTimeDto st = request.getServiceTime();
            store.changeServiceTime(st.getStartTime(), st.getEndTime());
        }
        return storeRepository.save(store);
    }

    /**
     * 매장 운영 여부 설정
     * - 매장 운영 시작 시 false, 종료 시 true
     *
     * @param storeId, close
     */
    @Override
    @CheckStoreAccess
    public void closeStore(UUID storeId, boolean close) {
        Store store = storeRepository.findByStoreId(storeId).orElseThrow(StoreNotFoundException::new);
        if (close) {
            store.close();
        } else  {
            store.open();
        }
    }

    /**
     * 매장에 직원 등록
     *
     * @param  storeId, staffs
     * @return 등록된 직원 목록 반환
     */
    @Override
    @CheckStoreAccess
    public Set<Staff> updateStaff(UUID storeId, List<StaffDto> staffs) {
        Store store = storeRepository.findByStoreId(storeId).orElseThrow(StoreNotFoundException::new);
        List<Staff> staffEntities = staffs.stream()
                .map(dto -> new Staff(UserId.of(dto.getId())))
                .toList();
        store.addStaff(staffEntities);
        staffs.forEach(staff -> {
            User user = userRepository.findById(staff.getId()).orElseThrow(UserNotFoundException::new);
            userService.changeUserRole(user.getUsername(), "ROLE_STAFF");
        });
        return store.getStaffs();
    }

    /**
     * 직원 삭제
     * - storeId에 있는 직원 중 staffs에 있는 직원 삭제
     *
     * @param  storeId, staffs
     */
    @Override
    @CheckStoreAccess
    public void removeStaff(UUID storeId, List<StaffDto> staffs) {
        Store store = storeRepository.findByStoreId(storeId).orElseThrow(StoreNotFoundException::new);
        List<Staff> staffEntities = staffs.stream()
                .map(dto -> new Staff(UserId.of(dto.getId())))
                .toList();
        store.removeStaff(staffEntities);
        staffs.forEach(staff -> {
            User user = userRepository.findById(staff.getId()).orElseThrow(UserNotFoundException::new);
            userService.changeUserRole(user.getUsername(), "ROLE_CUSTOMER");
        });
    }

    /**
     * 매장 삭제
     * - soft delete로 삭제 처리
     *
     * @param  storeId
     */
    @Override
    @CheckStoreAccess
    public void removeStore(UUID storeId) {
        User user = userRepository.findByUsername(securityUtil.getCurrentUsername())
                .orElseThrow(UserNotFoundException::new);
        Store store = storeRepository.findByStoreId(storeId).orElseThrow(StoreNotFoundException::new);
        store.softDelete(securityUtil.getCurrentUsername());
        if (storeRepository.findByUser(user).isEmpty()) {
            userService.changeUserRole(user.getUsername(), "ROLE_CUSTOMER");
        }
    }
}
