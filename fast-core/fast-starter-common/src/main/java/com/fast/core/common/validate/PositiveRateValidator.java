package com.fast.core.common.validate;

import com.fast.core.common.validate.annotation.PositiveRate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class PositiveRateValidator implements ConstraintValidator<PositiveRate, Object> {

    private boolean canBeZero;
    private boolean canOverOne;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return ValidateUtil.checkDecimal(canBeZero, false, (BigDecimal) value, 4, !canOverOne);
    }

    @Override
    public void initialize(PositiveRate constraintAnnotation) {
        canBeZero = constraintAnnotation.canBeZero();
        canOverOne = constraintAnnotation.canOverOne();
    }
}
