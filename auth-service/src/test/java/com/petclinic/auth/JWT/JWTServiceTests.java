package com.petclinic.auth.JWT;

import com.petclinic.auth.Role.Role;
import com.petclinic.auth.User.User;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * User: @Fube
 * Date: 2021-10-10
 * Ticket: feat(AUTH-CPC-357)
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class JWTServiceTests {

    @Autowired
    private JWTService jwtService;

    private final User USER = new User(
            1,
            "uname",
            "pwd",
            "a@b.c",
            false,
            null);

    private final Set<Role> ROLES = new HashSet<Role>(){{
        add(new Role(1, "TEST"));
    }};

    @Test
    void setup(){}

    @Test
    @DisplayName("Given user, get JWT")
    void get_jwt_from_user() {
        final String token = jwtService.encrypt(USER);
        assertNotNull(token);
    }

    @Test
    @DisplayName("Given token, get user")
    void get_user_from_jwt() {
        final String token = jwtService.encrypt(USER);
        final User decrypt = jwtService.decrypt(token);
        assertEquals(USER.getEmail(), decrypt.getEmail());
    }
}
