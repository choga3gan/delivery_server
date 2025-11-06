/**
 * @package     com.choga3gan.delivery.review.dto
 * @class       ReviewResponse
 * @description Review 정보 반환 DTO
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
package com.choga3gan.delivery.review.dto;

import com.choga3gan.delivery.review.domain.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "리뷰 정보 반환을 위한 DTO")
public class ReviewResponse {

    @Schema(description = "리뷰 id", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID reviewId;

    @Schema(description = "리뷰 본문 내용", example = "맛있게 잘 먹었습니다.")
    private String content;

    @Schema(description = "평점 (1~5)", example = "4.0")
    private Double rating;

    @Schema(description = "리뷰를 작성하는 사용자의 id", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID userId;

    @Schema(description = "리뷰를 작성하는 매장의 id", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID storeId;

    @Schema(description = "리뷰를 작성하는 사용자의 username", example = "user123")
    private String username;

    @Builder
    public ReviewResponse(UUID reviewId, String content, Double rating, UUID userId, UUID storeId, String username) {
        this.reviewId = reviewId;
        this.content = content;
        this.rating = rating;
        this.userId = userId;
        this.storeId = storeId;
        this.username = username;
    }

    public static ReviewResponse from(Review review) {
        return ReviewResponse.builder()
                .content(review.getContent())
                .reviewId(review.getReviewId())
                .rating(review.getRating())
                .userId(review.getUserId())
                .storeId(review.getStoreId())
                .username(review.getUsername())
                .build();
    }
}
