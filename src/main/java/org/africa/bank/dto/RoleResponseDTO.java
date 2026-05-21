package org.africa.bank.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class RoleResponseDTO {

    private Long id;

    private String label;

    private String description;

    private Set<String> permissions;
}