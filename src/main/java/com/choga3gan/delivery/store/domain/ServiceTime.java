/**
 * @package     com.choga3gan.delivery.store.domain
 * @class       ServiceTime
 * @description 매장 운영 시간을 보다 상세하게 정의하여 운영 시작 시간과 마감 시간을 설정
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

package com.choga3gan.delivery.store.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class ServiceTime {
    @Column
    private LocalTime startTime;
    @Column
    private LocalTime endTime;

    @Builder
    ServiceTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
