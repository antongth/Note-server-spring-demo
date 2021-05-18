package net.tack.school.notes.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MaxLenValidValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxLenValid {
    String message() default "Invalid max len";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
