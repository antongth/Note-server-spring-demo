package net.tack.school.notes.validator;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserDtoValidValidator implements ConstraintValidator<UserDtoValid, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String s1 = (String) new BeanWrapperImpl(o).getPropertyValue("login");
        String s2 = (String) new BeanWrapperImpl(o).getPropertyValue("firstName");
        String s3 = (String) new BeanWrapperImpl(o).getPropertyValue("oldPassword");
        String s4 = (String) new BeanWrapperImpl(o).getPropertyValue("newPassword");
        String s5 = (String) new BeanWrapperImpl(o).getPropertyValue("lastName");
        if(s1!=null) {
            return true;
        }
        return false;
    }
}
