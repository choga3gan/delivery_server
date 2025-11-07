/**
 * @package     com.choga3gan.delivery.review.service
 * @class       ReviewServiceImpl
 * @description Review Service 구현체
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

import com.choga3gan.delivery.global.utils.service.SecurityUtilService;
import com.choga3gan.delivery.order.domain.Order;
import com.choga3gan.delivery.order.domain.OrderStatus;
import com.choga3gan.delivery.order.repository.OrderRepository;
import com.choga3gan.delivery.review.domain.Review;
import com.choga3gan.delivery.review.dto.ReviewRequest;
import com.choga3gan.delivery.review.event.ReviewCreatedEvent;
import com.choga3gan.delivery.review.event.ReviewDeletedEvent;
import com.choga3gan.delivery.review.event.ReviewUpdatedEvent;
import com.choga3gan.delivery.review.exception.ReviewNotEditableException;
import com.choga3gan.delivery.review.exception.ReviewNotFoundException;
import com.choga3gan.delivery.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final SecurityUtilService securityUtil;
    private final ApplicationEventPublisher publisher;
    private final OrderRepository orderRepository;

    /**
     * 새로운 리뷰 추가
     * - 리뷰 추가 시 리뷰 추가 가능한지 여부 먼저 확인
     * - 리뷰를 추가하고자 하는 매장에 리뷰가 완료되지 않은 주문건이 존재해야 함
     *
     * @param  reviewRequest
     * @return Review
     */
    @Override
    public Review createReview(ReviewRequest reviewRequest) {
        List<Order> orders = orderRepository.findByUserIdAndOrderItems_StoreIdAndOrderStatusAndReviewed(
                reviewRequest.getUserId(), reviewRequest.getStoreId(), OrderStatus.DELIVERY_COMPLETED, false
        );
        if (orders.isEmpty()) {
            throw new ReviewNotEditableException();
        }

        Review review = Review.builder()
                .username(reviewRequest.getUsername())
                .content(reviewRequest.getContent())
                .userId(reviewRequest.getUserId())
                .storeId(reviewRequest.getStoreId())
                .rating(reviewRequest.getRating())
                .build();

        orders.getFirst().changeReviewed(true);
        publisher.publishEvent(new ReviewCreatedEvent(review.getStoreId(), review.getRating()));
        return reviewRepository.save(review);
    }

    /**
     * 매장의 모든 리뷰 목록 조회
     *
     * @param  storeId
     * @return Review 객체 목록 페이지 형태로 반환
     */
    @Override
    public Page<Review> getReviewsFromStore(UUID storeId) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        return reviewRepository.findByStoreId(storeId, pageable);
    }

    /**
     * 사용자의 모든 리뷰 목록 조회
     *
     * @param  userId
     * @return Review 객체 목록 페이지 형태로 반환
     */
    @Override
    public Page<Review> getReviewsFromUser(UUID userId) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());
        return reviewRepository.findByUserId(userId, pageable);
    }

    /**
     * 특정 리뷰의 상세 정보 조회
     *
     * @param  reviewId
     * @return Review
     */
    public Review getReview(UUID reviewId) {
        return reviewRepository.findByReviewId(reviewId).orElseThrow(ReviewNotFoundException::new);
    }

    /**
     * 리뷰 수정
     * - 평점 수정 시 수정된 평점에 따라 매장 평점 평균 갱신
     *
     * @param  reviewId, reviewRequest
     * @return Review
     */
    @Override
    public Review updateReview(UUID reviewId, ReviewRequest reviewRequest) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);

        // 본인이 작성한 리뷰만 수정 가능
        if (!Objects.equals(review.getUsername(), securityUtil.getCurrentUsername())) {
            throw new ReviewNotEditableException();
        }

        // null이 아닌 필드만 수정
        if (reviewRequest.getRating() != null) {
            review.changeRating(reviewRequest.getRating());
            publisher.publishEvent(
                    new ReviewUpdatedEvent(review.getStoreId(), review.getRating(), reviewRequest.getRating()));
        }
        if (reviewRequest.getContent() != null) {
            review.changeContent(reviewRequest.getContent());
        }
        return reviewRepository.save(review);
    }

    /**
     * 리뷰 삭제
     * - 본인이 작성한 리뷰만 삭제 가능
     *
     * @param  userId, reviewId
     */
    @Override
    public void removeReview(UUID userId, UUID reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        if  (review.getUserId().equals(userId)) {
            review.softDelete(securityUtil.getCurrentUsername());
            publisher.publishEvent(new ReviewDeletedEvent(review.getStoreId(), review.getRating()));
        } else {
            throw new ReviewNotEditableException();
        }
    }

    /**
     * 관리자 권한으로 리뷰 삭제
     *
     * @param  reviewId
     * ROLE_ROLE_MANAGER
     */
    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    public void removeReviewFromManager(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        review.softDelete(securityUtil.getCurrentUsername());
        publisher.publishEvent(new ReviewDeletedEvent(review.getStoreId(), review.getRating()));
    }
}
