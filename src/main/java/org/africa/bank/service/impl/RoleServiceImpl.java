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

        // Vérifier si le rôle existe déjà
        if (roleRepository.existsByLabel(dto.getLabel())) {
            throw new RuntimeException("Ce rôle existe déjà");
        }

        // Récupérer les permissions depuis la base
        Set<Permission> permissions = dto.getPermissionIds()
                .stream()
                .map(permissionId -> permissionRepository.findById(permissionId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Permission introuvable : " + permissionId
                                )))
                .collect(Collectors.toSet());

        // Construire le rôle
        Role role = Role.builder()
                .label(dto.getLabel())
                .description(dto.getDescription())
                .permissions(permissions)
                .build();

        // Sauvegarder
        Role savedRole = roleRepository.save(role);

        // Retourner la réponse
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

        role.setLabel(dto.getLabel());
        role.setDescription(dto.getDescription());
        role.setPermissions(permissions);

        Role updatedRole = roleRepository.save(role);

        return mapToResponse(updatedRole);
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
                .label(role.getLabel())
                .description(role.getDescription())
                .permissions(
                        role.getPermissions()
                                .stream()
                                .map(permission -> permission.getName().name())
                                .collect(Collectors.toSet())
                )
                .build();
    }
}