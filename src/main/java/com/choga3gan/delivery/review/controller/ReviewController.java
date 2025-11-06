/**
 * @package     com.choga3gan.delivery.review.controller
 * @class       ReviewController
 * @description Review CRUD 기능을 위한 Controller
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

package com.choga3gan.delivery.review.controller;

import com.choga3gan.delivery.review.domain.Review;
import com.choga3gan.delivery.review.dto.ReviewRequest;
import com.choga3gan.delivery.review.dto.ReviewResponse;
import com.choga3gan.delivery.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "리뷰 API", description = "리뷰 등록, 조회, 수정, 삭제 기능을 위한 API")
@RestController
@RequiredArgsConstructor
@RequestMapping
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(
        summary = "리뷰 생성",
        description = """
            작성자가 리뷰를 작성하려고 하는 매장에 아직 리뷰를 작성하지 않은 주문 건이 존재하는 지 확인합니다. <br>
            존재하는 경우 리뷰를 작성하고 해당 주문 건에 대해 리뷰가 완료되었음을 알립니다.
            """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "리뷰 생성 완료"),
            @ApiResponse(responseCode = "403", description = "리뷰 작성 권한이 없음"),
            @ApiResponse(responseCode = "404", description = "사용자/매장을 찾을 수 없음")
    })
    @PostMapping("/v1/reviews")
    public ResponseEntity<ReviewResponse> createReview(@Valid @RequestBody ReviewRequest reviewRequest) {
        Review review = reviewService.createReview(reviewRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ReviewResponse.from(review));
    }

    @Operation(
        summary = "매장에 작성된 모든 리뷰 조회",
        description = """
            매장 기준으로 매장에 작성된 모든 리뷰를 조회합니다.
            """
    )
    @Parameters({
        @Parameter(
            name = "storeId",
            description = "리뷰를 조회할 매장 ID",
            example = "123550e8400-e29b-41d4-a716-446655440000",
            required = true
        )
    })
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "정상 조회"),
        @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음")
    })
    @GetMapping("/v1/stores/{storeId}/reviews")
    public ResponseEntity<Page<ReviewResponse>> getReviewsFromStore(@PathVariable UUID storeId) {
        Page<Review> reviews = reviewService.getReviewsFromStore(storeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviews.map(ReviewResponse::from));
    }

    @Operation(
            summary = "사용자가 작성한 모든 리뷰 조회",
            description = """
            사용자 기준으로 사용자가 작성한 모든 리뷰를 조회합니다.
            """
    )
    @Parameters({
            @Parameter(
                    name = "userId",
                    description = "리뷰를 조회할 사용자 ID",
                    example = "123550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 조회"),
            @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/v1/users/{userId}/reviews")
    public ResponseEntity<Page<ReviewResponse>> getReviewsFromUser(@PathVariable UUID userId) {
        Page<Review> reviews = reviewService.getReviewsFromUser(userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(reviews.map(ReviewResponse::from));
    }

    @Operation(
            summary = "특정 리뷰의 상세 정보 조회",
            description = """
            특정 리뷰 하나의 상세 정보를 조회합니다.
            """
    )
    @Parameters({
            @Parameter(
                    name = "reviewId",
                    description = "상세 정보를 조회할 리뷰 ID",
                    example = "123550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 조회"),
            @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음")
    })
    @GetMapping("/v1/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> getReview(@PathVariable UUID reviewId) {
        Review review = reviewService.getReview(reviewId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ReviewResponse.from(review));
    }

    @Operation(
            summary = "특정 리뷰 수정",
            description = """
            본인이 작성한 리뷰를 수정합니다.
            """
    )
    @Parameters({
            @Parameter(
                    name = "reviewId",
                    description = "수정할 리뷰 ID",
                    example = "123550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "403", description = "수정 권한이 없음")
    })
    @PatchMapping("/v1/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable UUID reviewId,
                                                       @Valid @RequestBody ReviewRequest reviewRequest) {
        Review review = reviewService.updateReview(reviewId, reviewRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ReviewResponse.from(review));
    }

    @Operation(
            summary = "특정 리뷰 삭제",
            description = """
            본인이 작성한 리뷰를 삭제합니다.
            """
    )
    @Parameters({
            @Parameter(
                    name = "userId",
                    description = "사용자 ID",
                    example = "123550e8400-e29b-41d4-a716-446655440000",
                    required = true
            ),
            @Parameter(
                    name = "reviewId",
                    description = "리뷰 ID",
                    example = "123550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "403", description = "삭제 권한이 없음"),
            @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음")
    })
    @DeleteMapping("/v1/user/{userId}/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> deleteReview(@PathVariable UUID userId, @PathVariable UUID reviewId) {
        reviewService.removeReview(userId, reviewId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "관리자 권한으로 특정 리뷰 삭제",
            description = """
            관리자가 특정 리뷰를 삭제합니다.
            """
    )
    @Parameters({
            @Parameter(
                    name = "reviewId",
                    description = "리뷰 ID",
                    example = "123550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "403", description = "삭제 권한이 없음"),
            @ApiResponse(responseCode = "404", description = "리뷰를 찾을 수 없음")
    })
    @DeleteMapping("/v1/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> deleteReviewFromManager(@PathVariable UUID reviewId) {
        reviewService.removeReviewFromManager(reviewId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT).build();
    }
}
