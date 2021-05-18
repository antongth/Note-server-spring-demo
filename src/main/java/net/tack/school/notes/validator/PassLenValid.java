package net.tack.school.notes.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PassLenValidValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PassLenValid {
    String message() default "Invalid len of pass";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}