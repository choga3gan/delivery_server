/**
 * @package     com.choga3gan.delivery.store.service
 * @class       StoreService
 * @description Store Service 구현을 위한 인터페이스
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

import com.choga3gan.delivery.store.domain.Staff;
import com.choga3gan.delivery.store.domain.Store;
import com.choga3gan.delivery.store.dto.StaffDto;
import com.choga3gan.delivery.store.dto.StoreRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface StoreService {

    // CREATE
    Store createStore(StoreRequest storeRequest);

    // READ
    Store getStore(UUID storeId);
    Page<Store> getStores();
    Set<Staff> getStaff(UUID storeId);

    // UPDATE
    Store updateStore(UUID storeId, StoreRequest storeRequest);
    void closeStore(UUID storeId, boolean close);
    Set<Staff> updateStaff(UUID storeId, List<StaffDto> staffDTO);
    void removeStaff(UUID storeId, List<StaffDto> staffRequests);

    // DELETE
    void removeStore(UUID storeId);
}
