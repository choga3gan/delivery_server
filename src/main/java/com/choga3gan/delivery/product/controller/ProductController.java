/**
 * @package     com.choga3gan.delivery.product.controller
 * @class       ProductController
 * @description Product CRUD 기능 구현을 위한 Controller 개발
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

package com.choga3gan.delivery.product.controller;

import com.choga3gan.delivery.product.domain.Product;
import com.choga3gan.delivery.product.dto.ProductRequest;
import com.choga3gan.delivery.product.dto.ProductResponse;
import com.choga3gan.delivery.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "상품 API", description = "상품 등록, 조회, 수정, 삭제 기능을 위한 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/stores/{storeId}/products")
public class ProductController {

    private final ProductService productService;

    @Operation(
        summary = "매장에 신규 상품 추가",
        description = """
            신규 상품 정보를 Request Body로 받아 신규 상품을 매장에 추가합니다.
            """
    )
    @Parameters({
        @Parameter(
            name = "storeId",
            description = "상품을 추가할 매장 Id",
            example = "123550e8400-e29b-41d4-a716-446655440000",
            required = true
        )
    })
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "신규 상품 생성 성공"),
        @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음")
    })
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@PathVariable UUID storeId, @RequestBody ProductRequest productRequest) {
        Product product = productService.createProduct(storeId, productRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ProductResponse.from(product));
    }

    @Operation(
            summary = "매장에 있는 모든 상품 조회",
            description = """
            매장에 포함되어 있는 모든 상품을 페이지 형태로 조회합니다.
            """
    )
    @Parameters({
            @Parameter(
                    name = "storeId",
                    description = "상품을 조회할 매장 Id",
                    example = "123550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 조회 성공"),
            @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음")
    })
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProductsFromStore(@PathVariable UUID storeId) {
        Page<Product> productPage = productService.getProducts(storeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productPage.map(ProductResponse::from));
    }
}
