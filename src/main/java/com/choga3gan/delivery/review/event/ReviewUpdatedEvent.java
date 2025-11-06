/**
 * @package     com.choga3gan.delivery.review.event
 * @class       ReviewUpdatedEvent
 * @description 리뷰 수정 이벤트
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

package com.choga3gan.delivery.review.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ReviewUpdatedEvent {
    private UUID storeId;
    private double oldRating;
    private double newRating;

    public ReviewUpdatedEvent(UUID storeId, double oldRating, double newRating) {
        this.storeId = storeId;
        this.oldRating = oldRating;
        this.newRating = newRating;
    }
}
