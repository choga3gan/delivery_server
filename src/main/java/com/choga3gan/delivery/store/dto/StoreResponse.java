package com.choga3gan.delivery.store.dto;

import com.choga3gan.delivery.category.dto.CategoryResponse;
import com.choga3gan.delivery.store.domain.Store;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Schema(description = "매장 정보 반환 DTO")
public class StoreResponse {

    @Schema(description = "매장 Id", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID storeId;

    @Schema(description = "매장이 분류된 카테고리 리스트")
    private List<CategoryResponse> categories;

    @Schema(description = "매장 이름", example = "테스트 매장")
    private String storeName;

    @Schema(description = "매장 운영 여부", example = "false")
    private boolean closed;

    @Schema(description = "매장 평점 평균", example = "0.0")
    private double ratingAvg;

    @Schema(description = "매장 주소", example = "서울시 광화문")
    private String address;

    @Schema(description = "운영 시작 시간", example = "09:00")
    private LocalTime openTime;

    @Schema(description = "운영 종료 시간", example = "21:00")
    private LocalTime closeTime;

    @Schema(description = "매장 연락처", example = "01012345678")
    private String telNum;

    @Schema(description = "매장 리뷰 개수", example = "123")
    private int reviewCount;

    @Builder
    public StoreResponse(UUID storeId, List<CategoryResponse> categories, String storeName,
                         boolean closed, double ratingAvg, String address,
                         LocalTime openTime, LocalTime closeTime, String telNum, int reviewCount) {
        this.storeId = storeId;
        this.categories = categories;
        this.storeName = storeName;
        this.closed = closed;
        this.ratingAvg = ratingAvg;
        this.address = address;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.telNum = telNum;
        this.reviewCount = reviewCount;
    }

    public static StoreResponse from(Store store) {
        return StoreResponse.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .address(store.getAddress())
                .telNum(store.getTelNum())
                .openTime(store.getServiceTime().getStartTime())
                .closeTime(store.getServiceTime().getEndTime())
                .categories(store.getCategories() != null ?
                        store.getCategories().stream()
                                .map(CategoryResponse::from)
                                .collect(Collectors.toList())
                        : Collections.emptyList())
                .build();
    }
}
