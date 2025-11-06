/**
 * @package     com.choga3gan.delivery.user.controller;
 * @class       RoleController
 * @description 역할 컨트롤러
 *
 * @author      hakjun
 * @since       2025. 11. 4.
 * @version     1.0
 *
 * <pre>
 * == Modification Information ==
 * Date          Author        Description
 * ----------    -----------   ---------------------------
 * 2025. 11. 4.        hakjun       최초 생성
 * </pre>
 */
package com.choga3gan.delivery.user.controller;

import com.choga3gan.delivery.user.domain.Role;
import com.choga3gan.delivery.user.dto.RoleDto;
import com.choga3gan.delivery.user.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@Tag(name = "역할 API", description = "역할 등록, 조회, 수정, 삭제 기능을 위한 API")
@RestController
@RequestMapping("v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @Operation(
        summary = "모든 역할 조회",
        description = """
            모든 역할을 조회합니다.
            delete_at 소프트 딜리트 된 항목 제거 필요
            """
    )
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }


    @Operation(
        summary = "역할 단건 조회",
        description = """
            단건 역할을 조회합니다.
            delete_at 소프트 딜리트 된 항목 제거 필요
            """
    )
    @Parameters({
        @Parameter(
            name = "roleId",
            description = "조회할 역할 ID",
            example = "123550e8400-e29b-41d4-a716-446655440000",
            required = true
        )
    })
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "정상 조회"),
        @ApiResponse(responseCode = "404", description = "역할을 찾을 수 없음")
    })
    /**
     * 역할 단건 조회
     * @param @PathVariable (URL 경로에 있는 값을 파라미터로 받아옴) roleId
     * @return
     */
    @GetMapping("/{roleId}")
    public ResponseEntity<Role> getRole(@PathVariable UUID roleId) {
        Role role = roleService.getRole(roleId);
        return ResponseEntity.ok(role);
    }


    @Operation(
        summary = "역할 생성",
        description = """
            신규 역할을 생성합니다.
            """
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "역할 정상 생성"),
        @ApiResponse(responseCode = "404", description = "역할을 생성할 수 없음")
    })
    /**
     * 역할 생성
     * @param
     * @return
     */
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody RoleDto roleDto) {
        Role createRole = roleService.createRole(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createRole);
    }


    @Operation(
        summary = "역할 전체 수정",
        description = """
            주어진 userId에 해당하는 역할을 수정합니다.
            """
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "역할 정상 수정"),
        @ApiResponse(responseCode = "404", description = "역할을 수정할 수 없음")
    })
    /**
     * 역할 전체 수정
     * @param
     * @return
     */
    @PutMapping("/{roleId}")
    public ResponseEntity<Role> updateRole(@PathVariable UUID roleId, @RequestBody RoleDto roleDto) {
        Role updateRole = roleService.updateRole(roleId, roleDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateRole);
    }

    @Operation(
        summary = "역할 삭제",
        description = """
            주어진 roleId 에 대한 역할을 삭제합니다.
            """
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "역할 정상 삭제"),
        @ApiResponse(responseCode = "404", description = "역할을 삭제할 수 없음")
    })
    /**
     * 역할 삭제
     * @param
     * @return
     */
    @DeleteMapping("/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable UUID roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.ok().build();
    }

}
