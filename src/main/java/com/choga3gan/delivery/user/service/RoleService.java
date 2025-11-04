package com.choga3gan.delivery.user.service;

import com.choga3gan.delivery.user.domain.Role;
import com.choga3gan.delivery.user.dto.RoleDto;
import com.choga3gan.delivery.user.exception.DuplicatedRoleException;
import com.choga3gan.delivery.user.exception.RoleNotFoundException;
import com.choga3gan.delivery.user.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    /**
     * 역할 전체 조회
     * @param
     * @return Role
     */
     public List<Role> getAllRoles() {
         return roleRepository.findAll();
     }

     /**
      * 역할 단건 조회
      * @param
      * @return
      */
     public Role getRole(UUID roleId) {
         return roleRepository.findById(roleId).orElseThrow(RoleNotFoundException::new);
     }

     /**
      * 역할 생성
      * @param roleDto
      * @return Role
      */
     public Role createRole(RoleDto roleDto) {
         if (roleRepository.existsByRoleNameAndDeletedAtIsNull(roleDto.getRoleName())) {
             throw new DuplicatedRoleException();
         }
         Role role = new Role(null, roleDto.getRoleName(), roleDto.getRoleDescription());
         return roleRepository.save(role);
     }

     /**
      * 역할 수정
      * @param
      * @return
      */
     public Role updateRole(UUID roleId, RoleDto roleDto) {
         Role role = roleRepository.findById(roleId).orElseThrow(RoleNotFoundException::new);

         role.changeRoleName(roleDto.getRoleName());
         role.changeRoleDescription(roleDto.getRoleDescription());
         return roleRepository.save(role);
     }

    /**
     * 역할 삭제
     * @param
     * @return
     */
     public void deleteRole(UUID roleId) {
         Role role = roleRepository.findById(roleId).orElseThrow(RoleNotFoundException::new);
         role.softDelete("delete_tester01");
     }
}
