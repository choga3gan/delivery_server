/**
 * @package     com.choga3gan.delivery.store.util
 * @class       StaffConverter
 * @description 직원 정보 변환
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

package com.choga3gan.delivery.store.util;

import com.choga3gan.delivery.store.domain.Staff;
import com.choga3gan.delivery.user.domain.UserId;
import jakarta.persistence.AttributeConverter;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class StaffConverter implements AttributeConverter<Set<Staff>, String> {
    @Override
    public String convertToDatabaseColumn(Set<Staff> attribute) {
        return attribute == null ? null : attribute.stream().map(s -> s.getId().getId().toString()).collect(Collectors.joining(","));
    }

    @Override
    public Set<Staff> convertToEntityAttribute(String dbData) {
        return StringUtils.hasText(dbData) ? Arrays.stream(dbData.split(","))
                .map(s -> new Staff(UserId.of(UUID.fromString(s)))).collect(Collectors.toSet()) : null;
    }
}
