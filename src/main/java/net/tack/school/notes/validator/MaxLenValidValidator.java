package net.tack.school.notes.validator;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class MaxLenValidValidator implements ConstraintValidator<MaxLenValid, String> {
    @Value("${max_name_length}")
    public int maxName;

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return Optional.ofNullable(string).map(s -> s.length() <= maxName)
                .orElse(true);
    }
}
