package com.icf.backend.bootstrap;

import com.icf.backend.enumeration.AvailableRole;
import com.icf.backend.model.Role;
import com.icf.backend.model.User;
import com.icf.backend.repository.RoleRepository;
import com.icf.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private List<Role> availableRoles;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent contextRefreshedEvent) {
        this.initRolesIfNecessary();
        this.initUsersIfNecessary();
    }

    public void initUsersIfNecessary() {
        if (CollectionUtils.isEmpty(this.userRepository.findAll()) && !CollectionUtils.isEmpty(this.availableRoles)) {

            log.info("User initiation has started...");

            User admin = new User();
            admin.setUsername("Admin");
            admin.setPassword(this.passwordEncoder.encode("password"));
            admin.setEnabled(true);
            admin.setRoles(
                    Set.of(
                            this.availableRoles.stream().filter(r -> r.getName().equalsIgnoreCase(AvailableRole.ROLE_ADMIN.name())).findFirst().orElse(null))
            );

            User user1 = new User();
            user1.setUsername("User1");
            user1.setPassword(this.passwordEncoder.encode("password"));
            user1.setEnabled(true);
            user1.setRoles(
                    Set.of(
                            this.availableRoles.stream().filter(r -> r.getName().equalsIgnoreCase(AvailableRole.ROLE_CONTENT_EDITOR.name())).findFirst().orElse(null),
                            this.availableRoles.stream().filter(r -> r.getName().equalsIgnoreCase(AvailableRole.ROLE_LOGGED_IN_USER.name())).findFirst().orElse(null))
            );

            User user2 = new User();
            user2.setUsername("User2");
            user2.setPassword(this.passwordEncoder.encode("password"));
            user2.setEnabled(true);
            user2.setRoles(
                    Set.of(
                            this.availableRoles.stream().filter(r -> r.getName().equalsIgnoreCase(AvailableRole.ROLE_CONTENT_EDITOR.name())).findFirst().orElse(null))
            );

            User user3 = new User();
            user3.setUsername("User3");
            user3.setPassword(this.passwordEncoder.encode("password"));
            user3.setEnabled(true);
            user3.setRoles(
                    Set.of(
                            this.availableRoles.stream().filter(r -> r.getName().equalsIgnoreCase(AvailableRole.ROLE_LOGGED_IN_USER.name())).findFirst().orElse(null))
            );

            this.userRepository.saveAll(List.of(admin, user1, user2, user3));

            log.info("User initiation has finished.");
        }
    }

    public void initRolesIfNecessary() {
        if (CollectionUtils.isEmpty(this.roleRepository.findAll())) {
            log.info("Role initiation has started...");

            Role adminRole = Role.builder().name(AvailableRole.ROLE_ADMIN.name()).build();
            Role contentEditorRole = Role.builder().name(AvailableRole.ROLE_CONTENT_EDITOR.name()).build();
            Role loggedInUserRole = Role.builder().name(AvailableRole.ROLE_LOGGED_IN_USER.name()).build();

            this.availableRoles = this.roleRepository.saveAll(List.of(adminRole, contentEditorRole, loggedInUserRole));

            log.info("Role initiation has finished.");
        }
    }
}
