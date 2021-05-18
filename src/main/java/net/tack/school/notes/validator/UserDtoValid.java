package net.tack.school.notes.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UserDtoValidValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface UserDtoValid {
    String message() default "fields are not ok";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
