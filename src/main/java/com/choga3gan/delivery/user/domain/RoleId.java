/**
 * @package     com.choga3gan.delivery.user.domain
 * @class       RoleId
 * @description 역할 아이디 객체
 *
 * @author      hakjun
 * @since       2025. 11. 4.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 4.        hakjun       최초 생성
 * </pre>
 */
package com.choga3gan.delivery.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@EqualsAndHashCode // 기본키 생성
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleId {
    @Column(length = 45, name="role_id")
    private UUID id;

    protected RoleId(UUID id) {
        this.id = id;
    }
    public static RoleId of(UUID id) {
        return new RoleId(id);
    }
    public static RoleId of() {
        return RoleId.of(UUID.randomUUID());
    }
}
