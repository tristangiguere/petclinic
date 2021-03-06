package com.petclinic.auth.User;

import com.petclinic.auth.Role.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthServiceUserEntityTests {

    final String
            USER = "user",
            PASS = "Pas$word123",
            EMAIL = "email",
            ROLE_NAME = "role";

    final Role role = new Role(0, ROLE_NAME);

    final Set<Role> ROLES = new HashSet<Role>() {{
        add(role);
    }};
    final long ID = 1L;

    @Autowired
    private UserRepo userRepo;

    @BeforeEach
    void setup() {
        userRepo.deleteAllInBatch();
    }

    @Test
    @DisplayName("User setters")
    void user_setters() {

        final User user = new User();
        user.setUsername(USER);
        user.setRoles(ROLES);
        user.setEmail(EMAIL);
        user.setPassword(PASS);
        user.setId(ID);

        assertEquals(USER, user.getUsername());
        assertEquals(PASS, user.getPassword());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(ID, user.getId());
        assertTrue(user.getRoles().stream().anyMatch(n -> n.getName().equals(ROLE_NAME)));
    }

    @Test
    @DisplayName("User builder")
    void user_builder() {
        final User user = User.builder()
                .roles(ROLES)
                .id(ID)
                .email(EMAIL)
                .password(PASS)
                .username(USER)
                .build();


        assertEquals(USER, user.getUsername());
        assertEquals(PASS, user.getPassword());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(ID, user.getId());
        assertTrue(user.getRoles().stream().anyMatch(n -> n.getName().equals(ROLE_NAME)));
    }
}