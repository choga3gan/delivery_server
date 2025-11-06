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
