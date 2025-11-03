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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카테고리 API", description = "카테고리 등록, 조회, 수정, 삭제 기능을 위한 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/categories")
public class CategoryController {

    private CategoryService categoryService;

    @Operation(
        summary = "새로운 카테고리 등록",
        description = """
            CategoryDTO의 형식에 맞도록 새로운 카테고리 정보를 입력하여 Request Body로 전송합니다.  
            CategoryDTO의 내용에 맞게 신규 카테고리를 생성한 뒤 생성된 정보를 반환합니다.
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
}
