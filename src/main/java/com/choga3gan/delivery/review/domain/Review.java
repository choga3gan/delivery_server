/**
 * @package     com.choga3gan.delivery.review.domain
 * @class       Review
 * @description Review 도메인 Entity 관리
 *
 * @author      jinnk0
 * @since       2025. 10. 31.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 10. 31.        jinnk0       최초 생성
 * </pre>
 */

package com.choga3gan.delivery.review.domain;

import com.choga3gan.delivery.global.domain.Auditable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "p_review")
@Getter
@NoArgsConstructor
public class Review extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID reviewId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private UUID storeId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String content;

    private double rating = 5.0;

    @Builder
    public Review(UUID userId, UUID storeId, String username, String content, double rating) {
        this.userId = userId;
        this.storeId = storeId;
        this.username = username;
        this.content = content;
        this.rating = rating;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeRating(double rating) {
        this.rating = rating;
    }
}
