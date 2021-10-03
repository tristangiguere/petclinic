package com.petclinic.auth.User;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class AuthServiceUserRepoTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepo repo;

    final long ID = 1;
    final String
            USER = "user",
            PASS = "pas$word123",
            EMAIL = "email@gmail.com";

    private Validator validator;

    @BeforeEach
    void setup()
    {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    @DisplayName("Verify if the email is valid and succeed")
    void verify_valid_email_success() {
        User user = new User();
        user.setUsername(USER);
        user.setPassword(PASS);
        user.setId(ID);
        user.setEmail("testemail@gmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Verify if the email is valid and fail because missing @")
    void detect_invalid_email_missing_at() {
        User user = new User();
        user.setUsername(USER);
        user.setPassword(PASS);
        user.setId(ID);
        user.setEmail("testemailgmail.com");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(violations.size(), 1);

        ConstraintViolation<User> violation = violations.iterator().next();
        assertEquals("Email must be valid", violation.getMessage());
        assertEquals("email", violation.getPropertyPath().toString());
        assertEquals("testemailgmail.com", violation.getInvalidValue());
    }

    @Test
    @DisplayName("Create a user in the db and test if it can be found")
    public void find_created_user() {
        User user = new User();
        user.setUsername(USER);
        user.setPassword(PASS);
        user.setEmail(EMAIL);

        User savedUser = repo.save(user);
        User existUser = entityManager.find(User.class, savedUser.getId());

        assertEquals(user.getEmail(), existUser.getEmail());

    }
}
