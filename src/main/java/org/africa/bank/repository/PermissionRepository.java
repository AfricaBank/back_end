package org.africa.bank.repository;

import org.africa.bank.constants.PermissionName;
import org.africa.bank.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
    Optional<Permission> findByName(PermissionName name);

}
