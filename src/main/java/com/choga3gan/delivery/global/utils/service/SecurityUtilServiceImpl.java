/**
 * @package     com.choga3gan.delivery.global.utils.service
 * @class       SecurityUtilServiceImpl
 * @description Security Util Service 인터페이스의 개발 단계에서 사용할 임시 구현체
 *
 * @author      jinnk0
 * @since       2025. 11. 3.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 3.        jinnk0       최초 생성
 * </pre>
 */

package com.choga3gan.delivery.global.utils.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class SecurityUtilServiceImpl implements SecurityUtilService {

    /**
     * 현재 접속 중인 사용자의 username 반환
     *
     * @return 현재 접속 중인 사용자의 username
     */
    @Override
    public String getCurrentUsername() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return "anonymous";
//        }
//
//        Object principal = authentication.getPrincipal();
//
//        if (principal instanceof UserDetails userDetails) {
//            return userDetails.getUsername();
//        } else if (principal instanceof String username) {
//            return username;
//        } else {
//            return "unknown";
//        }
        return "test_user";
    }
}
