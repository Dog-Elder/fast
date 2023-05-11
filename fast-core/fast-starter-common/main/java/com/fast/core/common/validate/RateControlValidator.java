package com.fast.core.common.validate;

import com.fast.core.common.util.Com;
import com.fast.core.common.util.RUtil;
import com.fast.core.common.validate.annotation.RateControl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Digits;
import java.lang.reflect.Field;
import java.math.BigDecimal;


public class RateControlValidator implements ConstraintValidator<RateControl, Object> {

    private String[] targetArr;

    private String[] byArr;

    private boolean[] rateCanBeZeroArr;

    private boolean[] decimalCanBeZeroArr;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean ret = false;

        for (int i = 0; i < targetArr.length; i++) {
            String target = targetArr[i];
            String by = byArr[i];

            int scale = 4;
            boolean byRate = (boolean) RUtil.getValue(value, by);
            BigDecimal targetValue = (BigDecimal) RUtil.getValue(value, target);
            if (byRate) {
                Field field = RUtil.getField(value.getClass(), target);
                if (field.isAnnotationPresent(Digits.class)) {
                    Digits digits = field.getAnnotation(Digits.class);
                    scale = digits.fraction();
                }
            }

            boolean canBeZero = canBeZero(byRate, i);
            return ValidateUtil.checkDecimal(canBeZero, false, targetValue, byRate ? scale : Com.DecimalScale, byRate);
        }

        return ret;
    }

    private boolean canBeZero(boolean byRate, int index) {
        if (byRate) {
            return rateCanBeZeroArr.length == 1 ? rateCanBeZeroArr[0] : rateCanBeZeroArr[index];
        } else {
            return decimalCanBeZeroArr.length == 1 ? decimalCanBeZeroArr[0] : decimalCanBeZeroArr[index];
        }
    }

    @Override
    public void initialize(RateControl constraintAnnotation) {
        rateCanBeZeroArr = constraintAnnotation.rateCanBeZero();
        decimalCanBeZeroArr = constraintAnnotation.decimalCanBeZero();
        targetArr = constraintAnnotation.target();
        byArr = constraintAnnotation.by();
    }
}
