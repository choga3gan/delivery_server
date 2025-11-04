/**
 * @package     com.choga3gan.delivery.category.dto
 * @class       CategoryResponse
 * @description 카테고리 값 반환을 위한 DTO
 *
 * @author      jinnk0
 * @since       2025. 11. 3.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 3.        jinnk0       최초 생성
 * </pre>
 */

package com.choga3gan.delivery.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Schema(description = "카테고리 값 반환 DTO")
public class CategoryResponse {
    @Schema(description = "카테고리 id", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID categoryID;

    @Schema(description = "카테고리 이름", example = "한식")
    private String categoryName;

    @Builder
    public CategoryResponse(UUID categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }
}
