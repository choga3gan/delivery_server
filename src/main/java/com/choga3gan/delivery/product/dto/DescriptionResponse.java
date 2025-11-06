/**
 * @package     com.choga3gan.delivery.product.dto
 * @class       DescriptionDTO
 * @description 상품 소개 문구 답변 DTO
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

package com.choga3gan.delivery.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "상품 소개 문구 답변 DTO")
public class DescriptionResponse {

    @Schema(description = "50자 이내의 상품 소개 문구",
            example = "신당동 떡볶이: 혀는 얼얼, 손은 멈출 수 없는 마성의 매운맛!")
    private String response;

    @Builder
    public DescriptionResponse(String response) {
        this.response = response;
    }
}
