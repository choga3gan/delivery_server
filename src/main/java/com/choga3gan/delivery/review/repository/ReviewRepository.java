/**
 * @package     com.choga3gan.delivery.review.repository
 * @class       ReviewRepository
 * @description Review 데이터 접근을 위한 repository
 *
 * @author      jinnk0
 * @since       2025. 11. 6.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 6.        jinnk0       최초 생성
 * </pre>
 */

package com.choga3gan.delivery.review.repository;

import com.choga3gan.delivery.review.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    Page<Review> findByStoreId(UUID storeId, Pageable pageable);
    Page<Review> findByUserId(UUID userId, Pageable pageable);
    Optional<Review> findByReviewId(UUID reviewId);
}
