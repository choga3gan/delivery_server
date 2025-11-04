/**
 * @package     com.choga3gan.delivery.global.jwt
 * @class       JwtProperties
 * @description application.yml 의 설정을 매핑해주는 클래스
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
package com.choga3gan.delivery.global.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "user.jwt") //yml 설정 중 user.jwt로 시작하는 항목을 매핑
public class JwtProperties {
    private String secret;
    private int validTime;
}
