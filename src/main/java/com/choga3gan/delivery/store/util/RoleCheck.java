/**
 * @package     com.choga3gan.delivery.store.util
 * @class       RoleCheck
 * @description 권한 확인을 위한 커스텀 인터페이스
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

public interface RoleCheck {
    boolean check(Store store);
}
