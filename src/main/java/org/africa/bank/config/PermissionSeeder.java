package org.africa.bank.config;

import lombok.RequiredArgsConstructor;
import org.africa.bank.constants.PermissionName;
import org.africa.bank.repository.PermissionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.africa.bank.entity.Permission;

@Component
@RequiredArgsConstructor
public class PermissionSeeder implements CommandLineRunner {

    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {

        for (PermissionName permissionName : PermissionName.values()) {

            permissionRepository.findByName(permissionName)
                    .orElseGet(() -> permissionRepository.save(
                            Permission.builder()
                                    .name(permissionName)
                                    .build()
                    ));
        }
    }
}