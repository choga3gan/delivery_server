/**
 * @package     com.choga3gan.delivery.global.utils.service
 * @class       SpringUtilService
 * @description Spring Security에서 모든 도메인에서 사용할 util 관련 서비스 로직을 구현하기 위한 규격을 정의하는 인터페이스
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

public interface SecurityUtilService {
    // 현재 접속하고 있는 사용자의 username 가져오기
    String getCurrentUsername();
}
