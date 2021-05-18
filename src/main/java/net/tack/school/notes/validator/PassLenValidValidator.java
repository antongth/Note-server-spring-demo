package net.tack.school.notes.validator;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class PassLenValidValidator implements ConstraintValidator<PassLenValid, String> {

    @Value("${max_name_length}")
    public int maxNameLength;

    @Value("${min_password_length}")
    public int minPassLength;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return Optional.ofNullable(s).map(v->(v.length() >= minPassLength) && (v.length() <= maxNameLength))
                .orElse(true);
    }
}
