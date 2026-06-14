package org.africa.bank.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class RoleResponseDTO {

    private Long id;

    private String name;

    private String description;
    private Boolean status;

    private LocalDateTime createdAt;

    private Integer permissionCount;

    private Set<String> permissions;
}