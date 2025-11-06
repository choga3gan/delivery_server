/**
 * @package     com.choga3gan.delivery.review.dto
 * @class       ReviewRequest
 * @description review 작성/수정 요청을 위한 DTO
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

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Review 작성/수정 요청을 위한 DTO")
public class ReviewRequest {
    @Schema(description = "리뷰 본문 내용", example = "맛있게 잘 먹었습니다.")
    private String content;

    @Schema(description = "평점 (1~5)", example = "4.0")
    private Double rating;

    @Schema(description = "리뷰를 작성하는 사용자의 id", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID userId;

    @Schema(description = "리뷰를 작성하려고 하는 매장의 id", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID storeId;

    @Schema(description = "리뷰를 작성하려고 하는 사용자의 username", example = "user123")
    private String username;
}
