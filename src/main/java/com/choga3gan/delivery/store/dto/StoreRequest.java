/**
 * @package     com.choga3gan.delivery.store.dto
 * @class       StoreRequest
 * @description Store 생성/수정을 위한 요청 DTO
 *
 * @author      jinnk0
 * @since       2025. 11. 4.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 4.        jinnk0       최초 생성
 * </pre>
 */

package com.choga3gan.delivery.store.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Store 생성/수정을 위한 요청 DTO")
public class StoreRequest {
    @Schema(description = "매장 이름", example = "테스트 매장")
    private String storeName;

    @Schema(description = "매장 주소", example = "서울시 광화문")
    private String address;

    @Schema(description = "매장 연락처", example = "01012345678")
    private String telNum;

    @Schema(description = "매장의 카테고리 분류 선택 리스트", example = "[550e8400-e29b-41d4-a716-446655440000]")
    private List<UUID> categoryIds; // 카테고리 ID 리스트

    @Schema(description = "운영 시간 DTO")
    private ServiceTimeDto serviceTime;

    @Builder
    public StoreRequest(String storeName, String address, String telNum,
                        List<UUID> categoryIds, ServiceTimeDto serviceTime) {
        this.storeName = storeName;
        this.address = address;
        this.telNum = telNum;
        this.categoryIds = categoryIds;
        this.serviceTime = serviceTime;
    }
}
