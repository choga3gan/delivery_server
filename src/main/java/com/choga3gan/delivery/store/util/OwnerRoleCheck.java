/**
 * @package     com.choga3gan.delivery.store.util
 * @class       OwnerRoleCheck
 * @description 매장 수정 권한 보유 확인 (OWNER 권한, 소유 매장 일치 여부 확인)
 *
 * @author      jinnk0
 * @since       2025. 11. 4.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 4.        jinnk0       최초 생성
 * </pre>
 */

package com.choga3gan.delivery.store.util;

import com.choga3gan.delivery.store.domain.Store;
import com.choga3gan.delivery.store.exception.StoreNotEditableException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OwnerRoleCheck implements RoleCheck {

    private static final Set<String> EDITABLE_ROLES = Set.of("OWNER", "MANAGER", "MASTER");

    /**
     * 현재 로그인한 사용자가 해당 매장을 수정할 수 있는지 확인
     * - store.user.id 와 JWT subject(UUID)가 일치해야 함
     * - 권한이 OWNER, MANAGER, MASTER 이여야 함
     *
     * @param store 매장 엔티티
     * @return 수정 가능 여부 (true 가능 / false 불가능)
     */
    @Override
    public boolean check(Store store) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof Jwt jwt)) {
            throw new StoreNotEditableException("인증 정보가 없습니다.");
        }

        UUID userIdFromJwt = UUID.fromString(jwt.getSubject());
        String role = jwt.getClaimAsString("role");

        // 매장 주인 ID
        UUID storeOwnerId = store.getUser().getId().getId();

        // 매장 소유주 확인 및 권한 확인
        if (!userIdFromJwt.equals(storeOwnerId) || !EDITABLE_ROLES.contains(role.toUpperCase())) {
            throw new StoreNotEditableException();
        }

        return true;
    }
}
