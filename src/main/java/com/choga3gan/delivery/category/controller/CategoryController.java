/**
 * @package     com.choga3gan.delivery.category.controller
 * @class       CategoryController
 * @description Category 도메인의 CRUD 기능을 제공하기 위한 Controller
 *
 * @author      jinnk0
 * @since       2025. 11. 3.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 3.        jinnk0       최초 생성
 * </pre>
 */

package com.choga3gan.delivery.category.controller;

import com.choga3gan.delivery.category.domain.Category;
import com.choga3gan.delivery.category.dto.CategoryDto;
import com.choga3gan.delivery.category.dto.CategoryResponse;
import com.choga3gan.delivery.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "카테고리 API", description = "카테고리 등록, 조회, 수정, 삭제 기능을 위한 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(
        summary = "새로운 카테고리 등록",
        description = """
            CategoryDTO의 형식에 맞도록 새로운 카테고리 정보를 입력하여 Request Body로 전송합니다. <br>
            CategoryDTO의 내용에 맞게 신규 카테고리를 생성한 뒤 생성된 정보를 반환합니다. <br>
            이미 생성되어 있는 카테고리를 등록하려고 할 경우 DuplicateCategoryNameException이 발생합니다.
            """
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "생성 완료"),
        @ApiResponse(responseCode = "400", description = "잘못된 입력값")
    })
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.createCategory(categoryDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CategoryResponse(category.getCategoryId(), category.getCategoryName()));
    }

    @Operation(
        summary = "모든 카테고리 조회",
        description = """
            삭제 처리되지 않은 모든 카테고리를 조회합니다. <br>
            조회한 카테고리들을 리스트로 반환합니다.
            """
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "정상 조회"),
    })
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        List<CategoryResponse> responseList = categories.stream()
                .map(category -> new CategoryResponse(category.getCategoryId(), category.getCategoryName()))
                .toList();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseList);
    }

    @Operation(
        summary = "특정 카테고리 조회",
        description = """
            주어진 categoryId에 해당하는 카테고리의 상세 정보를 반환합니다.
            """
    )
    @Parameters({
        @Parameter(
            name = "categoryId",
            description = "조회할 카테고리 ID",
            example = "123550e8400-e29b-41d4-a716-446655440000",
            required = true
        )
    })
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "정상 조회"),
        @ApiResponse(responseCode = "404", description = "해당 카테고리를 찾을 수 없음")
    })
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable("categoryId") UUID categoryId) {
        Category category = categoryService.getCategory(categoryId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CategoryResponse(category.getCategoryId(), category.getCategoryName()));
    }

    @Operation(
            summary = "특정 카테고리 수정",
            description = """
            주어진 categoryId에 대하여 body에 담긴 필드에 대한 값을 수정합니다.
            """
    )
    @Parameters({
            @Parameter(
                    name = "categoryId",
                    description = "수정할 카테고리 ID",
                    example = "123550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 조회"),
            @ApiResponse(responseCode = "404", description = "해당 카테고리를 찾을 수 없음")
    })
    @PatchMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("categoryId") UUID categoryId,
                                                           @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.updateCategory(categoryId, categoryDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new CategoryResponse(category.getCategoryId(), category.getCategoryName()));
    }

    @Operation(
            summary = "특정 카테고리 삭제",
            description = """
            주어진 categoryId에 대하여 해당 카테고리를 삭제 처리합니다.
            """
    )
    @Parameters({
            @Parameter(
                    name = "categoryId",
                    description = "삭제할 카테고리 ID",
                    example = "123550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 처리 완료"),
            @ApiResponse(responseCode = "404", description = "해당 카테고리를 찾을 수 없음")
    })
    @DeleteMapping("/{categoryId}")
    public ResponseEntity removeCategory(@PathVariable("categoryId") UUID categoryId) {
        categoryService.removeCategory(categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
