/**
 * @package     com.choga3gan.delivery.product.dto
 * @class       ProductResponse
 * @description 상품 정보 반환을 위한 DTO
 *
 * @author      jinnk0
 * @since       2025. 11. 5.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 5.        jinnk0       최초 생성
 * </pre>
 */
package com.choga3gan.delivery.product.dto;

import com.choga3gan.delivery.category.dto.CategoryResponse;
import com.choga3gan.delivery.product.domain.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "상품 정보 반환을 위한 DTO")
public class ProductResponse {

    @Schema(description = "상품 식별을 위한 id", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID productId;

    @Schema(description = "상품이 분류된 카테고리 리스트")
    private List<CategoryResponse> categories;

    @Schema(description = "상품 이름", example = "떡볶이")
    private String productName;

    @Schema(description = "상품 가격", example = "3000.0")
    private Double price;

    @Schema(description = "상품 공개 설정", example = "true")
    private Boolean open;

    @Schema(description = "상품 이미지 url", example = "https://picsum.photos/200")
    private String productImg;

    @Schema(description = "상품 소개 설명", example = "둘이 먹다 하나가 죽어도 모르는 떡볶이")
    private String description;

    @Builder
    public ProductResponse(UUID productId, List<CategoryResponse> categories, String productName,
                           Double price, Boolean open, String productImg, String description) {
        this.productId = productId;
        this.categories = categories;
        this.productName = productName;
        this.price = price;
        this.open = open;
        this.productImg = productImg;
        this.description = description;
    }

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .categories(product.getCategories() != null ?
                        product.getCategories().stream()
                                .map(CategoryResponse::from)
                                .collect(Collectors.toList())
                        : Collections.emptyList())
                .productName(product.getProductName())
                .price((double)product.getPrice())
                .open(product.isOpen())
                .productImg(product.getProductImg())
                .description(product.getDescription())
                .build();
    }
}
