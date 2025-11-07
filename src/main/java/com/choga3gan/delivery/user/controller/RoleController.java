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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
            MASTER 권한의 사용자만 조회 가능합니다.
            """
    )
    @Parameters({
        @Parameter(
            name = "roleId",
            description = "사용자",
            example = "b56bd4ff-fcbd-4f3e-8f02-ed88f6647747",
            required = true
        )
    })
    @GetMapping("/{roleId}")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<Role> getRole(@PathVariable UUID roleId) {
        Role role = roleService.getRole(roleId);
        return ResponseEntity.ok(role);
    }


    @Operation(
        summary = "역할 생성",
        description = """
            신규 역할을 생성합니다.
            MASTER 권한의 사용자만 역할 생성이 가능합니다.
            """
    )
    @PreAuthorize("hasRole('MASTER')")
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody RoleDto roleDto) {
        Role createRole = roleService.createRole(roleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createRole);
    }


    @Operation(
        summary = "역할 수정",
        description = """
            주어진 userId에 해당하는 역할을 수정합니다.
            MASTER 권한의 사용자만 역할 수정이 가능합니다.
            """
    )
    @PutMapping("/{roleId}")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<Role> updateRole(@PathVariable UUID roleId, @RequestBody RoleDto roleDto) {
        Role updateRole = roleService.updateRole(roleId, roleDto);
        return ResponseEntity.status(HttpStatus.OK).body(updateRole);
    }

    @Operation(
        summary = "역할 삭제",
        description = """
            주어진 roleId 에 대한 역할을 삭제합니다.
            MASTER 권한의 사용자만 역할 삭제가 가능합니다.
            """
    )
    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasRole('MASTER')")
    public ResponseEntity<Void> deleteRole(@PathVariable UUID roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.ok().build();
    }

}
