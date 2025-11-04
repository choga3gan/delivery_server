/**
 * @package     com.choga3gan.delivery.user.test
 * @class       MockUser
 * @description jwt 토큰 인증 없이 테스트를 하기 위한 어노테이션
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
package com.choga3gan.delivery.user.test;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) // 어디에 해당 어노테이션을 붙일 수 있는지 저장하는 메타 어노테이션
@Retention(RetentionPolicy.RUNTIME) // 해당 어노테이션이 언제까지 유지되는가를 나타내는 어노테이션
@WithSecurityContext(factory = WithMockSecurityContextFactory.class) //spring security 지원용 어노테이션 -> 지정된 factory 클래스를 이용해 security 컨텍스트 생성
public @interface MockUser {
    String username() default "testuser01";
    String email() default "testuser@test.org";
    String[] roles() default {"USER"};
}
