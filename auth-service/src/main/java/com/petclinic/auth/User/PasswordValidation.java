package com.petclinic.auth.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidation implements ConstraintValidator<PasswordStrengthCheck, String> {

    @Override
    public void initialize(PasswordStrengthCheck constraintAnnotation) {
       // ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        String pattern = "z";
       //String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";


        return value != null && value.matches(pattern);
    }


}
