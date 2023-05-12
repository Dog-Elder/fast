package com.fast.core.common.validate;

import com.fast.core.common.validate.annotation.PositiveDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;


public class PositiveDecimalValidator implements ConstraintValidator<PositiveDecimal, Object> {

    private boolean canBeZero;
    private int scale;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return ValidateUtil.checkDecimal(canBeZero, false, (BigDecimal) value, scale, false);
    }

    @Override
    public void initialize(PositiveDecimal constraintAnnotation) {
        canBeZero = constraintAnnotation.canBeZero();
        scale = constraintAnnotation.scale();
    }
}
