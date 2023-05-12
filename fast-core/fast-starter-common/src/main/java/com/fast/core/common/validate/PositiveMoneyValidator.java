package com.fast.core.common.validate;

import com.fast.core.common.validate.annotation.PositiveMoney;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;


public class PositiveMoneyValidator implements ConstraintValidator<PositiveMoney, Object> {

    private boolean canBeZero;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return ValidateUtil.checkDecimal(canBeZero, false, (BigDecimal) value, 2, false);
    }

    @Override
    public void initialize(PositiveMoney constraintAnnotation) {
        canBeZero = constraintAnnotation.canBeZero();
    }

}
