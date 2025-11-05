/**
 * @package     com.choga3gan.delivery.store.domain
 * @class       Staff
 * @description 매장에 소속된 직원 정의
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

import com.choga3gan.delivery.user.domain.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;

@ToString
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    @Column(name = "staff_id")
    private UserId id;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Staff staff = (Staff) o;
        return Objects.equals(id.getId(), staff.id.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id.getId());
    }
}
