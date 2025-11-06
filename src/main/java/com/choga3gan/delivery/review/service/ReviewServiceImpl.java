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
import com.choga3gan.delivery.review.domain.Review;
import com.choga3gan.delivery.review.dto.ReviewRequest;
import com.choga3gan.delivery.review.exception.ReviewNotEditableException;
import com.choga3gan.delivery.review.exception.ReviewNotFoundException;
import com.choga3gan.delivery.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final SecurityUtilService securityUtil;

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
        // TODO : 리뷰 쓸 수 있는지 먼저 확인
        Review review = Review.builder()
                .username(reviewRequest.getUsername())
                .content(reviewRequest.getContent())
                .userId(reviewRequest.getUserId())
                .storeId(reviewRequest.getStoreId())
                .rating(reviewRequest.getRating())
                .build();

        // TODO : 매장 평점 평균 갱신 이벤트
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
     * 리뷰 수정
     * - 평점 수정 시 수정된 평점에 따라 매장 평점 평균 갱신
     *
     * @param  reviewId, reviewRequest
     * @return Review
     */
    @Override
    public Review updateReview(UUID reviewId, ReviewRequest reviewRequest) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        if (reviewRequest.getRating() != null) {
            review.changeRating(reviewRequest.getRating());
            // TODO : 매장 평점 평균 수정 이벤트
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
        } else {
            throw new ReviewNotEditableException();
        }
    }

    /**
     * 관리자 권한으로 리뷰 삭제
     *
     * @param  reviewId
     */
    @Override
    @PreAuthorize("hasAnyRole('MANAGER', 'MASTER')")
    public void removeReviewFromManager(UUID reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewNotFoundException::new);
        review.softDelete(securityUtil.getCurrentUsername());
    }
}
