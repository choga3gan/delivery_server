/**
 * @package     com.choga3gan.delivery.review.event
 * @class       ReviewDeletedEvent
 * @description 리뷰 삭제 이벤트
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
public class ReviewDeletedEvent {
    private UUID storeId;
    private double rating;

    public ReviewDeletedEvent(UUID storeId, double rating) {
        this.storeId = storeId;
        this.rating = rating;
    }
}
