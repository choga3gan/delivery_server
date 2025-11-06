/**
 * @package     com.choga3gan.delivery.store.dto
 * @class       StaffRequest
 * @description Staff 등록/수정 및 정보 반환을 위한 DTO
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

import com.choga3gan.delivery.store.domain.Staff;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "직원 등록/삭제 요청 및 조회 DTO")
public class StaffDto {
    @Schema(description = "직원의 user_id", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID id;

    @Builder
    public StaffDto(UUID id) {
        this.id = id;
    }

    public static StaffDto from(Staff staff) {
        return StaffDto.builder().id(staff.getId().getId()).build();
    }
}
