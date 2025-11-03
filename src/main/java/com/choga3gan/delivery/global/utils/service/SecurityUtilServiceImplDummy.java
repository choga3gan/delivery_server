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

public class SecurityUtilServiceImplDummy implements SecurityUtilService {

    /**
     * Spring Security 개발이 완료되기 전까지 현재 접속 중인 사용자의 username을 가짜로 반환
     *
     * @return 현재 접속 중인 사용자의 username (Dummy)
     */
    @Override
    public String getCurrentUsername() {
        return "test_system";
    }
}
