package com.petclinic.auth.User;


import javassist.NotFoundException;  
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
@Validated
public class UserController {


    private final UserService userService;


    @PostMapping
    public User createUser(@RequestBody @Valid UserIDLessDTO dto) {

        log.info("Received user dto, trying to convert model");
        log.info("DTO info: { username={}, password={}, email={} }", dto.getUsername(), dto.getPassword(), dto.getEmail());
        log.info("Trying to persist user");
        final User saved = userService.createUser(dto);
        log.info("Successfully persisted user");

        return saved;
    }

    @PutMapping("/password_verification/{id}")
    public void passwordReset(@PathVariable long id,  @RequestBody String pwd) throws NotFoundException {

        userService.passwordReset(id,pwd);
        log.info("Password for User with id {}, reset", id);
    }

    @PutMapping("/email_verification/{id}")
    public void emailReset(@PathVariable long id,  @RequestBody String email) throws NotFoundException {

        log.info("email {} for User with id {}, reset", email, id);
        userService.emailReset(id, email);
    }
}
