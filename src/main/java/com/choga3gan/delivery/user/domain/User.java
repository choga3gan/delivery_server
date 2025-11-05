/**
 * @package     com.choga3gan.delivery.user.domain
 * @class       User
 * @description 유저 엔티티
 *
 * @author      hakjun
 * @since       2025. 11. 3.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 3.        hakjun       최초 생성
 * </pre>
 */

package com.choga3gan.delivery.user.domain;

import com.choga3gan.delivery.global.domain.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@ToString
@Entity
@Table(name = "p_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
public class User extends Auditable {

    @EmbeddedId
    private UserId id;

    @Column(length = 100, nullable = false, unique = true)
    private String username; // 사용자 로그인 아이디

    @Column(nullable = false, unique = true)
    private String email; // 사용자 이메일

    @Column(length = 65, nullable = false)
    private String password;

    @Column(name="is_public")
    private Boolean publicInfo;

    private String refreshToken;

    @ToString.Exclude //  테스트용으로 잠깐 배제
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Role role;

    @Builder
    public User(UserId id, String username, String email, String password, Boolean publicInfo, Role role) {
        this.id = Objects.requireNonNullElse(id, UserId.of()); // 없으면 자동 생성
        this.username = username;
        this.email = email;
        this.password = password;
        this.publicInfo = publicInfo;
        this.role = role;
    }

    // 리프레시 토큰 업데이트
    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // 리프레시 토큰 딜리트
    public void deleteRefreshToken() {
        this.refreshToken = null;
    }

    public void changeRoles(List<Role> roles) {

    }

    public void changeRoles(Role role) {

    }

/*    private UUID roleId;

    private String accessToken;

    private String refreshToken;*/


}
