/**
 * Created by IntelliJ IDEA.
 *
 * User: @MaxGrabs
 * Date: 26/09/21
 * Ticket: feat(AUTH-CPC-13)
 *
 * User: @Trilikin21
 * Date: 24/09/21
 * Ticket: feat(AUTH-CPC-64)
 *
 * User: @JordanAlbayrak
 * Date: 24/09/21
 * Ticket: feat(AUTH-CPC-102)
 *
 * User: @Zellyk
 * Date: 26/09/21
 * Ticket: feat(AUTH-CPC-104)
 *
 */
package com.petclinic.auth.User;

import com.petclinic.auth.Role.Role;
import com.petclinic.auth.Role.RoleController;
import com.petclinic.auth.Role.RoleIDLessDTO;
import com.petclinic.auth.Role.RoleRepo;
import com.petclinic.auth.User.*;
import javassist.NotFoundException;
import org.apache.tomcat.util.http.fileupload.MultipartStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.validation.ConstraintViolationException;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthServiceUserControllerTests {

    private final static Random rng;

    static {
        rng = new Random();
    }

    final String
            USER = "user",
            PASS = "Pas$word123",
            EMAIL = "email@gmail.com",
            BADEMAIL = "blabla";


    @BeforeEach
    void setup() {
        userRepo.deleteAllInBatch();
    }

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserMapper userMap;

    @Autowired
    private UserController userController;

    private final UserIDLessDTO ID_LESS_USER = new UserIDLessDTO(USER, PASS, EMAIL);

    @Test
    @DisplayName("Create a user from controller")
    void create_user_from_controller() {
        final User user = userController.createUser(ID_LESS_USER);
        assertNotNull(user);
        assertThat(user.getId(), instanceOf(Long.TYPE));
        assertTrue(userRepo.findById(user.getId()).isPresent());
    }
    @Test
    @DisplayName("Check the required fields with empty data")
    void check_empty_require_fields() {


        UserIDLessDTO userIDLessDTO = new UserIDLessDTO();

        assertThrows(ConstraintViolationException.class, () -> userController.createUser(userIDLessDTO));
    }

    @Test
    @DisplayName("Check the username field in order to refused if it is empty")
    void check_empty_username() {


        UserIDLessDTO userIDLessDTO = new UserIDLessDTO(null, PASS,EMAIL);

        assertThrows(ConstraintViolationException.class, () -> userController.createUser(userIDLessDTO));
    }
    @Test
    @DisplayName("Check the password field in order to refused if it is empty")
    void check_empty_password() {


        UserIDLessDTO userIDLessDTO = new UserIDLessDTO( USER, null,EMAIL);

        assertThrows(ConstraintViolationException.class, () -> userController.createUser(userIDLessDTO));
    }
    @Test
    @DisplayName("Check the email field in order to refused if it is empty")
    void check_empty_email(){

        UserIDLessDTO userIDLessDTO = new UserIDLessDTO(USER, PASS,null);

        assertThrows(ConstraintViolationException.class, () -> userController.createUser(userIDLessDTO));
    }
    @Test
    @DisplayName("Check if the input ID is correct")
    void check_empty_id() throws Exception{


        mockMvc.perform(put("/users/1000"))
                .andExpect(status().isForbidden());




    }
    @Test
    @DisplayName("Check that user exists")
    void verifyUser_true () throws NotFoundException {
        UserIDLessUsernameLessDTO loginUser = new UserIDLessUsernameLessDTO(EMAIL, PASS);
        userRepo.save(userMap.idLessUsernameLessToModel(loginUser).toBuilder().username("username").build());
        assertTrue(userController.verifyUser(loginUser));
    }
    @Test
    @DisplayName("Check user that does not exist")
    void verifyUser_false (){
        UserIDLessUsernameLessDTO loginUser = new UserIDLessUsernameLessDTO(BADEMAIL, PASS);
        assertThrows(NotFoundException.class, () -> userController.verifyUser(loginUser));
}
}