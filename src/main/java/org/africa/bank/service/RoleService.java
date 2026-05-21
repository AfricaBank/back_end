package org.africa.bank.service;



import org.africa.bank.dto.RoleRequestDTO;
import org.africa.bank.dto.RoleResponseDTO;

import java.util.List;

public interface RoleService {

    RoleResponseDTO createRole(RoleRequestDTO dto);

    List<RoleResponseDTO> getAllRoles();

    RoleResponseDTO getRoleById(Long id);

    RoleResponseDTO updateRole(Long id, RoleRequestDTO dto);

    void deleteRole(Long id);
}