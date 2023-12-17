package com.challenge.clientEnroller.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class CNPValidator implements ConstraintValidator<CNPConstraint, String> {
    @Override
    public void initialize(CNPConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String cnp, ConstraintValidatorContext constraintValidatorContext) {
        return StringUtils.isNotBlank(cnp) && cnp.matches("[0-9]{13}");
    }
}
