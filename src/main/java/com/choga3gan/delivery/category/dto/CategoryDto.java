/**
 * @package     com.choga3gan.delivery.category.dto
 * @class       CategoryDto
 * @description category 생성 및 수정 요청 시 사용할 DTO
 *
 * @author      jinnk0
 * @since       2025. 10. 31.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 10. 31.        jinnk0       최초 생성
 * </pre>
 */

package com.choga3gan.delivery.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "카테고리 생성 및 수정 요청 DTO")
public class CategoryDto {

    @Schema(description = "등록 또는 수정할 카테고리 이름 설정", example = "한식")
    private String categoryName;
}
