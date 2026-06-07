package org.africa.bank.config;

import lombok.RequiredArgsConstructor;
import org.africa.bank.entity.Permission;
import org.africa.bank.repository.PermissionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionSeeder implements CommandLineRunner {

    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {

        createPermission(
                "READ",
                "Lecture des données"
        );

        createPermission(
                "WRITE",
                "Modification des données"
        );

        createPermission(
                "VALIDATE",
                "Validation des opérations"
        );

        createPermission(
                "AUTONOMY",
                "Autonomie complète"
        );
    }

    private void createPermission(
            String code,
            String description
    ) {

        permissionRepository.findByCode(code)
                .orElseGet(() ->
                        permissionRepository.save(
                                Permission.builder()
                                        .code(code)
                                        .description(description)
                                        .build()
                        )
                );
    }
}