package com.choga3gan.delivery.store.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "운영 시간 등록을 위한 DTO, StoreRequest DTO 내부에 포함")
public class ServiceTimeDto {
    @JsonFormat(pattern = "HH:mm")
    @Schema(description = "운영 시작 시간", example = "09:00")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    @Schema(description = "운영 마감 시간", example = "21:00")
    private LocalTime endTime;
}
