/**
 * @package     com.choga3gan.delivery.product.repository
 * @class       AiDescriptionRepository
 * @description AI로 생성한 상품 소개 문구 요청/답변 저장을 위한 repository
 *
 * @author      jinnk0
 * @since       2025. 11. 6.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 6.        jinnk0       최초 생성
 * </pre>
 */

package com.choga3gan.delivery.product.repository;

import com.choga3gan.delivery.product.domain.AiDescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AiDescriptionRepository extends JpaRepository<AiDescription, UUID> {
}
