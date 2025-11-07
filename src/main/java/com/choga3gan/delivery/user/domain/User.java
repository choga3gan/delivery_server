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
import org.hibernate.annotations.SQLRestriction;

import java.util.Objects;

@ToString
@Entity
@Table(name = "p_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
@SQLRestriction("deleted_at IS NULL")
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
    private Boolean publicInfo = true;

    private String refreshToken;

    @ToString.Exclude //  무한루프 배제
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
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

    // 역할 변경 메서드
    public void changeRoles(Role role) {
        this.role = role;
    }

    //사용자 역할 확인 메서드
    public boolean hasRole(String role) {
        return this.role != null && this.role.getRoleName().equals(role);
    }

    //사용자 수정 메서드
    public void updateUser(String email, String password, Boolean publicInfo) {
        if(email != null && !email.isBlank()){
            this.email = email;
        }
        if(password != null && !password.isBlank()){
            this.password = password;
        }
        if(publicInfo != null){
            this.publicInfo = publicInfo;
        }
    }

}
