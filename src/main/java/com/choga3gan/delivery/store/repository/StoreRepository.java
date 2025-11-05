/**
 * @package     com.choga3gan.delivery.store.repository
 * @class       StoreRepository
 * @description Store Jpa 데이터 접근을 위한 Repository
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

package com.choga3gan.delivery.store.repository;

import com.choga3gan.delivery.store.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {

    Optional<Store> findByStoreId(UUID storeId);
    Page<Store> findAll(Pageable pageable);
}
