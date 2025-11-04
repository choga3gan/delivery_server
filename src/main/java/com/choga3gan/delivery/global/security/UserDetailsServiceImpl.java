/**
 * @package     com.choga3gan.delivery.global.security
 * @class       UserDetailsServiceImpl
 * @description spring security 인증 처리시,
 *              로그인할 때 사용자 정보를 DB에서 조회하고 UserDetails 객체로 변환해주는 서비스
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
import com.choga3gan.delivery.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    // 사용자 조회를 위한 repository
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 유저 이름으로 조회
        User user = repository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return new UserDetailsImpl(user);
    }
}
