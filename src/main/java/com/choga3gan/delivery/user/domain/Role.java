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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

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

    @ToString.Exclude // 순환참조 문제 해결
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private List<User> users;


}
