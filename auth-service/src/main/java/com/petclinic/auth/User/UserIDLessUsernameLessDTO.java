package com.petclinic.auth.User;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserIDLessUsernameLessDTO {

    @PasswordStrengthCheck
    private String password;

    @NotEmpty
    @Email(message = "Email must be valid")
    @Column(unique = true)
    private String email;
}
