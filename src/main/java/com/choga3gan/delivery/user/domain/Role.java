/**
 * @package     com.choga3gan.delivery.user.domain
 * @class       Role
 * @description 역할 엔티티
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

import com.choga3gan.delivery.global.domain.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="p_role")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends Auditable {

    @EmbeddedId
    private RoleId id;

    @Column(length = 100, nullable = false,  unique = true)
    private String roleName;

    @Column(length = 100)
    private String roleDescription;

    @Builder
    public Role(RoleId id, String roleName, String roleDescription) {
        this.id = Objects.requireNonNullElse(id, RoleId.of()); // 없으면 자동 생성
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

    @ToString.Exclude // 순환참조 문제 해결
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private List<User> users;

    public void changeRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void changeRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }


}
