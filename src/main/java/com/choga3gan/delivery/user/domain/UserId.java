/**
 * @package     com.choga3gan.delivery.user.domain
 * @class       UserId
 * @description 유저 아이디 객체
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

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class UserId {
    @Column(length = 45, name="user_id")
    private UUID id;

    protected UserId(UUID id){
        this.id = id;
    }

    public static UserId of(){
        return UserId.of(UUID.randomUUID());
    }

    public static UserId of(UUID id){
        return new UserId(id);
    }
}

