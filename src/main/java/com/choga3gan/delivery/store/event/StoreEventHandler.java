/**
 * @package     com.choga3gan.delivery.store.event
 * @class       StoreEventHandler
 * @description 리뷰 생성, 수정, 삭제 이벤트 발생 시 이를 받아 처리하는 이벤트 핸들러
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

package com.choga3gan.delivery.store.event;

import com.choga3gan.delivery.review.event.ReviewCreatedEvent;
import com.choga3gan.delivery.review.event.ReviewDeletedEvent;
import com.choga3gan.delivery.review.event.ReviewUpdatedEvent;
import com.choga3gan.delivery.store.domain.Store;
import com.choga3gan.delivery.store.exception.StoreNotFoundException;
import com.choga3gan.delivery.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoreEventHandler {
    private final StoreRepository storeRepository;

    /**
     * 리뷰 생성 시 매장의 평점 평균 업데이트
     *
     * @param  reviewCreatedEvent
     */
    @Async
    @EventListener(ReviewCreatedEvent.class)
    public void handlerReviewCreatedEvent(ReviewCreatedEvent reviewCreatedEvent) {
        Store store = storeRepository.findByStoreId(reviewCreatedEvent.getStoreId())
                .orElseThrow(StoreNotFoundException::new);
        store.updateRatingAvg(reviewCreatedEvent.getRating());
        storeRepository.saveAndFlush(store);
        log.info("Review has been created");
    }

    /**
     * 리뷰 수정 시 수정된 평점에 맞게 매장의 평점 평균 업데이트
     *
     * @param  reviewUpdatedEvent
     */
    @Async
    @EventListener(ReviewUpdatedEvent.class)
    public void handlerReviewUpdatedEvent(ReviewUpdatedEvent reviewUpdatedEvent) {
        Store store = storeRepository.findByStoreId(reviewUpdatedEvent.getStoreId())
                .orElseThrow(StoreNotFoundException::new);
        store.updateRatingAvg(reviewUpdatedEvent.getOldRating(), reviewUpdatedEvent.getNewRating());
        storeRepository.saveAndFlush(store);
        log.info("Review has been updated");
    }

    /**
     * 리뷰 삭제 시 매장의 평점 평균 업데이트
     *
     * @param  reviewDeletedEvent
     */
    @Async
    @EventListener(ReviewDeletedEvent.class)
    public void handlerReviewDeletedEvent(ReviewDeletedEvent reviewDeletedEvent) {
        Store store = storeRepository.findByStoreId(reviewDeletedEvent.getStoreId())
                .orElseThrow(StoreNotFoundException::new);
        store.deleteReviews(reviewDeletedEvent.getRating());
        storeRepository.saveAndFlush(store);
        log.info("Review has been deleted");
    }
}
