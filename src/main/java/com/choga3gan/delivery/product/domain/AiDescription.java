/**
 * @package     com.choga3gan.delivery.product.domain
 * @class       AiDescription
 * @description Ai 추천 Description 요청 질문과 답변을 저장하기 위한 엔티티
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
package com.choga3gan.delivery.product.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "p_ai_description")
@Getter
@NoArgsConstructor
public class AiDescription {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ai_description_id")
    private UUID aiDescriptionId;
    private UUID productId;
    private String request;
    private String response;

    @Builder
    public AiDescription(UUID productId, String request, String response) {
        this.productId = productId;
        this.request = request;
        this.response = response;
    }
}
