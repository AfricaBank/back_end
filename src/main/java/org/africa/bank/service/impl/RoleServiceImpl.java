package org.africa.bank.service.impl;

import lombok.RequiredArgsConstructor;
import org.africa.bank.dto.RoleRequestDTO;
import org.africa.bank.dto.RoleResponseDTO;
import org.africa.bank.entity.Permission;
import org.africa.bank.entity.Role;
import org.africa.bank.repository.PermissionRepository;
import org.africa.bank.repository.RoleRepository;
import org.africa.bank.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public RoleResponseDTO createRole(RoleRequestDTO dto) {

        if (roleRepository.existsByName(dto.getName())) {
            throw new RuntimeException("Ce rôle existe déjà");
        }

        Set<Permission> permissions = dto.getPermissionIds()
                .stream()
                .map(permissionId -> permissionRepository.findById(permissionId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Permission introuvable : " + permissionId
                                )))
                .collect(Collectors.toSet());

        Role role = Role.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .status(true)
                .permissions(permissions)
                .build();

        Role savedRole = roleRepository.save(role);

        return mapToResponse(savedRole);
    }

    @Override
    public List<RoleResponseDTO> getAllRoles() {

        return roleRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public RoleResponseDTO getRoleById(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Rôle introuvable"));

        return mapToResponse(role);
    }

    @Override
    public RoleResponseDTO updateRole(Long id, RoleRequestDTO dto) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Rôle introuvable"));

        Set<Permission> permissions = dto.getPermissionIds()
                .stream()
                .map(permissionId -> permissionRepository.findById(permissionId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Permission introuvable : " + permissionId
                                )))
                .collect(Collectors.toSet());

        role.setName(dto.getName());
        role.setDescription(dto.getDescription());
        role.setPermissions(permissions);

        Role updatedRole = roleRepository.save(role);

        return mapToResponse(updatedRole);
    }

    @Override
    public void activateRole(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Rôle introuvable"));

        role.setStatus(true);

        roleRepository.save(role);
    }

    @Override
    public void deactivateRole(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Rôle introuvable"));

        role.setStatus(false);

        roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {

        Role role = roleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Rôle introuvable"));

        roleRepository.delete(role);
    }

    private RoleResponseDTO mapToResponse(Role role) {

        return RoleResponseDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .status(role.getStatus())
                .createdAt(role.getCreatedAt())
                .permissionCount(role.getPermissions().size())
                .permissions(
                        role.getPermissions()
                                .stream()
                                .map(Permission::getCode)
                                .collect(Collectors.toSet())
                )
                .build();
    }
}