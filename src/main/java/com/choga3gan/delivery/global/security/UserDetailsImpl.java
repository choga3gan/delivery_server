/**
 * @package     com.choga3gan.delivery.global.security
 * @class       UserDetailsImpl
 * @description spring security의 인증 처리시,
 *              User 엔티티를 UserDetails 형식으로 다루기 위한 구현 클래스
 *              - UserDetails는 spring security의 인증 및 권한 정보를 담기 위한 인터페이스
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
package com.choga3gan.delivery.global.security;

import com.choga3gan.delivery.user.domain.User;
import com.choga3gan.delivery.user.exception.UserNotFoundException;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@ToString
public class UserDetailsImpl implements UserDetails {

    private final User user;

    /**
     * UserDetailsImpl 생성자
     * 로그인 시점에서 DB에서 조회한 User 엔티티를 받음
     * 유저가 없으면 UserNotFoundException 발생
     * @param user
     * @return
     */
    public UserDetailsImpl(User user){
        if(user == null){
            throw new UserNotFoundException();
        }

        this.user = user;
    }

    /**
     * 현 사용자가 가진 권한을 GrantedAuthority 형식으로 반환
     * 현재 ROLE_USER 권한 자동 매핑,
     * 추후 role.name() 등 메소드로 가져오기
     * @param  
     * @return 
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER")
            //, new SimpleGrantedAuthority("ROLE_ADMIN") // 이런 방식으로 필요시 추가
        );
    }

    /**
     * Spring security가 로그인 할 때 비밀번호, 사용자명을 비교할때 사용
     * @param
     * @return
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    //계정이 잠겨있지 않은가 확인
    @Override
    public boolean isAccountNonExpired() {
        //return UserDetails.super.isAccountNonExpired();
        return true;
    }

    //계정이 만료되지 않았는가 확인
    @Override
    public boolean isAccountNonLocked() {
        //return UserDetails.super.isAccountNonLocked();
        return true;
    }

    //비밀번호가 만료되지 않았는가 확인
    @Override
    public boolean isCredentialsNonExpired() {
        //return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    //사용 가능한 계정인가 확인
    @Override
    public boolean isEnabled() {
        //return UserDetails.super.isEnabled();
        return true;
    }
}
