/**
 * @package     com.choga3gan.delivery.review.service
 * @class       ReviewService
 * @description Review Service 개발을 위한 인터페이스
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

package com.choga3gan.delivery.review.service;

import com.choga3gan.delivery.review.domain.Review;
import com.choga3gan.delivery.review.dto.ReviewRequest;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ReviewService {

    // CREATE
    Review createReview(ReviewRequest reviewRequest);

    // READ
    Page<Review> getReviewsFromStore(UUID storeId);
    Page<Review> getReviewsFromUser(UUID userId);

    // UPDATE
    Review updateReview(UUID reviewId, ReviewRequest reviewRequest);

    // DELETE
    void removeReview(UUID userId, UUID reviewId);
    void removeReviewFromManager(UUID reviewId);
}
