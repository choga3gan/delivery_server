/**
 * @package     com.choga3gan.delivery.product.dto
 * @class       DescriptionDTO
 * @description 상품 소개 문구 요청 DTO
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
@Schema(description = "상품 소개 문구 요청 DTO")
public class DescriptionRequest {

    @Schema(description = "상품 설명을 포함하여 상품 소개 문구를 요청",
            example = "신당동에서 판매하고 있는 아주 맵지만 맛있는 떡볶이의 상품 소개 작성 부탁해")
    private String request;

    @Builder
    public DescriptionRequest(String request) {
        this.request = request;
    }
}
