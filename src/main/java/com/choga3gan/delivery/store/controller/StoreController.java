/**
 * @package     com.choga3gan.delivery.store.controller
 * @class       StoreController
 * @description store CRUD 기능을 제공하는 컨트롤러 개발
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

package com.choga3gan.delivery.store.controller;

import com.choga3gan.delivery.store.domain.Staff;
import com.choga3gan.delivery.store.domain.Store;
import com.choga3gan.delivery.store.dto.StaffDto;
import com.choga3gan.delivery.store.dto.StoreRequest;
import com.choga3gan.delivery.store.dto.StoreResponse;
import com.choga3gan.delivery.store.service.StoreService;
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

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Tag(name = "매장 API", description = "매장 등록, 조회, 수정, 삭제 기능을 위한 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/stores")
public class StoreController {

    private final StoreService storeService;

    @Operation(
        summary = "신규 매장 생성",
        description = """
            신규 매장 정보를 Request Body로 받아 신규 매장을 생성합니다. <br>
            등록하고자 하는 회원을 매장의 주인으로 등록하고 OWNER 권한을 부여합니다.
            """
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "신규 생성 성공"),
        @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @PostMapping
    public ResponseEntity<StoreResponse> createStore(@RequestBody StoreRequest storeRequest) {
        Store store = storeService.createStore(storeRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(StoreResponse.from(store));
    }

    @Operation(
        summary = "모든 매장 목록 조회",
        description = """
            모든 매장 목록을 페이지 형태로 반환합니다.
            """
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "정상 조회"),
    })
    @GetMapping
    public ResponseEntity<Page<StoreResponse>> getAllStores() {
        Page<Store> stores = storeService.getStores();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(stores.map(StoreResponse::from));
    }

    @Operation(
        summary = "특정 매장의 상세 정보 조회",
        description = """
            주어진 storeId에 해당하는 매장의 상세 정보를 반환합니다.
            """
    )
    @Parameters({
        @Parameter(
            name = "storeId",
            description = "조회할 매장 ID",
            example = "123550e8400-e29b-41d4-a716-446655440000",
            required = true
        )
    })
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "정상 조회"),
        @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음")
    })
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponse> getStore(@PathVariable UUID storeId) {
        Store store = storeService.getStore(storeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StoreResponse.from(store));
    }

    @Operation(
        summary = "매장에 등록되어 있는 직원 조회",
        description = """
            주어진 storeId에 해당하는 매장의 직원 목록을 조회합니다.
            """
    )
    @Parameters({
        @Parameter(
            name = "storeId",
            description = "조회할 매장 ID",
            example = "123550e8400-e29b-41d4-a716-446655440000",
            required = true
        )
    })
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "정상 조회"),
        @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음")
    })
    @GetMapping("/{storeId}/staffs")
    public ResponseEntity<List<StaffDto>>  getStaffs(@PathVariable UUID storeId) {
        Set<Staff> staffs = storeService.getStaff(storeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(staffs.stream().map(StaffDto::from).toList());
    }

    @Operation(
            summary = "매장에 신규 직원 등록",
            description = """
            주어진 storeId의 매장에 신규 직원을 추가 및 등록합니다.
            """
    )
    @Parameters({
            @Parameter(
                    name = "storeId",
                    description = "매장 ID",
                    example = "123550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 조회"),
            @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음")
    })
    @PostMapping("/{storeId}/staffs")
    public ResponseEntity<List<StaffDto>> createStaff(@PathVariable UUID storeId, @RequestBody List<StaffDto> staffs) {
        Set<Staff> createdStaffs = storeService.updateStaff(storeId, staffs);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdStaffs.stream().map(StaffDto::from).toList());
    }

    @Operation(
            summary = "매장 정보 수정",
            description = """
            주어진 storeId에 해당하는 매장의 상세 정보를 수정합니다. <br>
            DTO에 채워진 필드를 확인하여 채워진 필드만 수정 내용을 반영합니다.
            """
    )
    @Parameters({
            @Parameter(
                    name = "storeId",
                    description = "수정할 매장 ID",
                    example = "123550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 조회"),
            @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음")
    })
    @PatchMapping("/{storeId}")
    public ResponseEntity<StoreResponse> updateStore(@PathVariable UUID storeId, @RequestBody StoreRequest storeRequest) {
        Store store = storeService.updateStore(storeId, storeRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(StoreResponse.from(store));
    }

    @Operation(
            summary = "매장 운영 시작/운영 종료 처리",
            description = """
            주어진 storeId에 해당하는 매장을 영업 시작/영업 종료로 처리합니다.
            """
    )
    @Parameters({
            @Parameter(
                    name = "storeId",
                    description = "수정할 매장 ID",
                    example = "123550e8400-e29b-41d4-a716-446655440000",
                    required = true
            ),
            @Parameter(
                    name = "close",
                    description = "운영 시작(false)/운영 종료 여부(true)",
                    example = "true",
                    required = true
            )
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음")
    })
    @PatchMapping("/{storeId}/close")
    public ResponseEntity<StoreResponse> closeStore(@PathVariable UUID storeId, @RequestParam boolean close) {
        storeService.closeStore(storeId, close);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
        summary = "매장 삭제",
        description = """
            주어진 storeId에 해당하는 매장을 soft delete로 삭제 처리합니다.
            """
    )
    @Parameters({
        @Parameter(
            name = "storeId",
            description = "삭제할 매장 ID",
            example = "123550e8400-e29b-41d4-a716-446655440000",
            required = true
        )
    })
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "삭제 성공"),
        @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음")
    })
    @DeleteMapping("/{storeId}")
    public ResponseEntity<StoreResponse> removeStore(@PathVariable UUID storeId) {
        storeService.removeStore(storeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "직원 삭제",
            description = """
            주어진 storeId에 해당하는 매장에 등록되어 있는 직원을 삭제합니다.
            """
    )
    @Parameters({
            @Parameter(
                    name = "storeId",
                    description = "직원을 삭제할 매장 ID",
                    example = "123550e8400-e29b-41d4-a716-446655440000",
                    required = true
            )
    })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "매장을 찾을 수 없음")
    })
    @DeleteMapping("/{storeId}/staffs")
    public ResponseEntity<StoreResponse> removeStaff(@PathVariable UUID storeId, @RequestBody List<StaffDto> staffs) {
        storeService.removeStaff(storeId, staffs);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
