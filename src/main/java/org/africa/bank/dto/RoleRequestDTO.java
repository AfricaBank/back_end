package org.africa.bank.dto;
import lombok.Data;
import java.util.Set;

@Data
public class RoleRequestDTO {

    private String name;

    private String description;

    private Set<Long> permissionIds;
}